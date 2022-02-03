package gdsmith.cypher.standard_ast.expr;

import gdsmith.cypher.ICypherSchema;
import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.analyzer.ICypherTypeDescriptor;
import gdsmith.cypher.ast.analyzer.IIdentifierAnalyzer;
import gdsmith.cypher.standard_ast.CypherType;
import gdsmith.cypher.standard_ast.CypherTypeDescriptor;

import java.util.List;

public class StringCatExpression extends CypherExpression{
    private IExpression left, right;

    public StringCatExpression(IExpression left, IExpression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public ICypherTypeDescriptor analyzeType(ICypherSchema schema, List<IIdentifierAnalyzer> identifiers) {
        return new CypherTypeDescriptor(CypherType.STRING);
    }

    @Override
    public IExpression getCopy() {
        return new StringCatExpression(left, right);
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("(");
        left.toTextRepresentation(sb);
        sb.append("+");
        right.toTextRepresentation(sb);
        sb.append(")");
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof StringCatExpression)){
            return false;
        }
        return left.equals(((StringCatExpression) o).left) && right.equals(((StringCatExpression) o).right);
    }
}
