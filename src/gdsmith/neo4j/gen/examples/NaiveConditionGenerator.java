package gdsmith.neo4j.gen.examples;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;
import gdsmith.cypher.ast.analyzer.IWithAnalyzer;
import gdsmith.neo4j.schema.Neo4jSchema;
import gdsmith.cypher.dsl.BasicConditionGenerator;

public class NaiveConditionGenerator extends BasicConditionGenerator {

    public NaiveConditionGenerator(Neo4jSchema schema) {
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
