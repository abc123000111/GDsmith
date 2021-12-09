package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.IExpression;

public class GetPropertyExpression extends Neo4jExpression{
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
        sb.append(".").append(propertyName).append(")");
    }

    @Override
    public IExpression getCopy() {
        return null;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof GetPropertyExpression)){
            return false;
        }
        return fromExpression.equals(((GetPropertyExpression) o).fromExpression) && propertyName.equals(((GetPropertyExpression) o).propertyName);
    }

}
