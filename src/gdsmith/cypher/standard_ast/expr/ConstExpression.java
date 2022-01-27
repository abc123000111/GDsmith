package gdsmith.cypher.standard_ast.expr;

import gdsmith.cypher.ICypherSchema;
import gdsmith.cypher.ast.ICopyable;
import gdsmith.cypher.ast.ICypherType;
import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.analyzer.ICypherTypeDescriptor;
import gdsmith.cypher.ast.analyzer.IIdentifierAnalyzer;
import gdsmith.cypher.standard_ast.CypherType;
import gdsmith.cypher.standard_ast.CypherTypeDescriptor;

import java.util.List;

public class ConstExpression extends CypherExpression {
    Object value;
    ICypherType type;

    public ConstExpression(Object value){
        this.value = value;
        if(value instanceof Integer || value instanceof Float || value instanceof Long || value instanceof Double){
            type = CypherType.NUMBER;
        }
        else if(value instanceof String){
            type = CypherType.STRING;
        }
        else if(value instanceof Boolean){
            type = CypherType.BOOLEAN;
        }
        else {
            type = CypherType.UNKNOWN;
        }
    }

    @Override
    public ICypherTypeDescriptor analyzeType(ICypherSchema schema, List<IIdentifierAnalyzer> identifiers) {
        return new CypherTypeDescriptor(type);
    }

    public ICypherType getType(){
        return type;
    }

    public Object getValue(){
        return value;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        switch ((CypherType)type){
            case NUMBER: sb.append("" + value); break;
            case STRING: sb.append("\'" + value + "\'"); break;
            case BOOLEAN: sb.append("" + value); break;
            case UNKNOWN: sb.append("null"); break; //todo not supported
        }
    }

    @Override
    public IExpression getCopy() { //todo 这是个隐患
        if(value instanceof ICopyable){
            return new ConstExpression(((ICopyable) value).getCopy());
        }
        return new ConstExpression(value);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof ConstExpression)){
            return false;
        }
        if(type != ((ConstExpression) o).type){
            return false;
        }
        if(type == CypherType.UNKNOWN){
            return false;
        }
        return value.equals(((ConstExpression) o).value);
    }
}
