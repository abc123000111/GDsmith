package gdsmith.neo4j.gen.random;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;
import gdsmith.cypher.ast.analyzer.IWithAnalyzer;
import gdsmith.neo4j.dsl.BasicConditionGenerator;
import gdsmith.neo4j.schema.Neo4jSchema;

public class RandomConditionGenerator extends BasicConditionGenerator {
    public RandomConditionGenerator(Neo4jSchema schema) {
        super(schema);
    }

    @Override
    public IExpression generateMatchCondition(IMatchAnalyzer matchClause, Neo4jSchema schema) {
        return null;
    }

    @Override
    public IExpression generateWithCondition(IWithAnalyzer withClause, Neo4jSchema schema) {
        return null;
    }
}
