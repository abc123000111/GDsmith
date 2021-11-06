package gdsmith.neo4j.gen;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.IMatch;
import gdsmith.cypher.ast.IWith;
import gdsmith.neo4j.Neo4jSchema;
import gdsmith.neo4j.dsl.BasicConditionGenerator;
import gdsmith.neo4j.dsl.IConditionGenerator;

public class NaiveConditionGenerator extends BasicConditionGenerator {

    public NaiveConditionGenerator(Neo4jSchema schema) {
        super(schema);
    }

    @Override
    public IExpression generateMatchCondition(IMatch matchClause, Neo4jSchema schema) {
        return null;
    }

    @Override
    public IExpression generateWithCondition(IWith withClause, Neo4jSchema schema) {
        return null;
    }
}
