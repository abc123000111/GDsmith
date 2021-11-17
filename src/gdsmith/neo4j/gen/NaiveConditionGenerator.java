package gdsmith.neo4j.gen;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.IMatch;
import gdsmith.cypher.ast.IWith;
import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;
import gdsmith.cypher.ast.analyzer.IWithAnalyzer;
import gdsmith.neo4j.schema.Neo4jSchema;
import gdsmith.neo4j.dsl.BasicConditionGenerator;

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
