package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.IAlias;
import gdsmith.cypher.ast.ICypherType;
import gdsmith.cypher.ast.IExpression;

public class Alias implements IAlias {
    private String name;
    private IExpression expression;

    public Alias(String name, IExpression expression){
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ICypherType getType() {
        return Neo4jType.UNKNOWN;
    }

    @Override
    public IExpression getExpression() {
        return expression;
    }
}
