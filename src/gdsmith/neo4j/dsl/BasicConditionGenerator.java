package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.IMatch;
import gdsmith.cypher.ast.IWith;
import gdsmith.neo4j.Neo4jSchema;

public abstract class BasicConditionGenerator implements IConditionGenerator{

    private final Neo4jSchema schema;

    public BasicConditionGenerator(Neo4jSchema schema){
        this.schema = schema;
    }

    @Override
    public void fillMatchCondtion(IMatch matchClause) {
        matchClause.setCondition(generateMatchCondition(matchClause));
    }

    @Override
    public void fillWithCondition(IWith withClause) {
        withClause.setCondition(generateWithCondition(withClause));
    }

    public abstract IExpression generateMatchCondition(IMatch matchClause);
    public abstract IExpression generateWithCondition(IWith withClause);
}
