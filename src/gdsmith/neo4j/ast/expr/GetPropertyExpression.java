package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.IExpression;

public class GetPropertyExpression implements IExpression {
    private final IExpression fromExpression;
    private final String propertyName;

    public GetPropertyExpression(IExpression fromExpression, String propertyName){
        this.fromExpression = fromExpression;
        this.propertyName = propertyName;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("(");
        fromExpression.toTextRepresentation(sb);
        sb.append(".").append(propertyName).append(")");
    }
}