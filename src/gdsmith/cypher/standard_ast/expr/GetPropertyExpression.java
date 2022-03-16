package gdsmith.cypher.standard_ast.expr;

import gdsmith.GlobalState;
import gdsmith.MainOptions;
import gdsmith.cypher.ICypherSchema;
import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.analyzer.*;
import gdsmith.cypher.schema.IPropertyInfo;
import gdsmith.cypher.standard_ast.CypherType;
import gdsmith.cypher.standard_ast.CypherTypeDescriptor;

import java.util.List;
import java.util.Optional;

public class GetPropertyExpression extends CypherExpression {
    private final IExpression fromExpression;
    private final String propertyName;

    public GetPropertyExpression(IExpression fromExpression, String propertyName){
        this.fromExpression = fromExpression;
        this.propertyName = propertyName;
        fromExpression.setParentExpression(this);
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("(");
        fromExpression.toTextRepresentation(sb);
        sb.append(".").append("k"+ GlobalState.curMainOptions.getPropertyRandomNums()).append(")");
    }

    @Override
    public ICypherTypeDescriptor analyzeType(ICypherSchema schema, List<IIdentifierAnalyzer> identifiers) {
        //a.b的结构，首先获取a的类型
        ICypherTypeDescriptor fromExpressionType = fromExpression.analyzeType(schema, identifiers);
        if(fromExpressionType.isNode()){
            //a是NODE，看是否可以根据上下文信息找到b属性，如果可以，返回对应的属性类型，否则返回UNKNOWN
            INodeAnalyzer nodeAnalyzer = fromExpressionType.getNodeAnalyzer();
            IPropertyInfo propertyInfo = nodeAnalyzer.getAllPropertiesAvailable(schema).stream()
                    .filter(p->p.getKey().equals(propertyName)).findAny().orElse(null);
            if(propertyInfo != null){
                return new CypherTypeDescriptor(propertyInfo.getType());
            }
            return new CypherTypeDescriptor(CypherType.UNKNOWN);
        }
        if(fromExpressionType.isRelation()){
            //a是RELATION，看是否可以根据上下文信息找到b属性，如果可以，返回对应的属性类型，否则返回UNKNOWN
            IRelationAnalyzer relationAnalyzer = fromExpressionType.getRelationAnalyzer();
            IPropertyInfo propertyInfo = relationAnalyzer.getAllPropertiesAvailable(schema).stream()
                    .filter(p->p.getKey().equals(propertyName)).findAny().orElse(null);
            if(propertyInfo != null){
                return new CypherTypeDescriptor(propertyInfo.getType());
            }
            return new CypherTypeDescriptor(CypherType.UNKNOWN);
        }
        if(fromExpressionType.isMap()){
            //a是MAP，看根据上下文信息是否可以看出有b属性，如果有，返回对应属性类型，如果无法确定，发挥UNKNOWN
            IMapDescriptor mapDescriptor = fromExpressionType.getMapDescriptor();
            if(!mapDescriptor.isMapSizeUnknown()){
                //定长的MAP，可以遍历每一个属性
                if(mapDescriptor.getMapMemberTypes().containsKey(propertyName)){
                    return mapDescriptor.getMapMemberTypes().get(propertyName);
                }
            }
            //其他情况都无从判断
            return new CypherTypeDescriptor(CypherType.UNKNOWN);
        }
        return new CypherTypeDescriptor(CypherType.UNKNOWN);
    }

    @Override
    public IExpression getCopy() {
        return new GetPropertyExpression(fromExpression.getCopy(), propertyName);
    }
    @Override
    public boolean equals(Object o){
        if(!(o instanceof GetPropertyExpression)){
            return false;
        }
        return fromExpression.equals(((GetPropertyExpression) o).fromExpression) && propertyName.equals(((GetPropertyExpression) o).propertyName);
    }

}
