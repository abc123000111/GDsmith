package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.IExpression;

public class GetPropertyExpression implements IExpression {
    private final IExpression fromExpression;
    private final String propertyName;

    public GetPropertyExpression(IExpression fromExpression, String propertyName){
        this.fromExpression = fromExpression;
        this.propertyName = propertyName;
    }
}
