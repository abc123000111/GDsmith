package gdsmith.cypher.dsl;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;
import gdsmith.cypher.ast.analyzer.IWithAnalyzer;
import gdsmith.neo4j.schema.Neo4jSchema;

public abstract class BasicConditionGenerator implements IConditionGenerator{

    private final Neo4jSchema schema;

    public BasicConditionGenerator(Neo4jSchema schema){
        this.schema = schema;
    }

    @Override
    public void fillMatchCondtion(IMatchAnalyzer matchClause) {
        matchClause.setCondition(generateMatchCondition(matchClause, schema));
    }

    @Override
    public void fillWithCondition(IWithAnalyzer withClause) {
        withClause.setCondition(generateWithCondition(withClause, schema));
    }

    public abstract IExpression generateMatchCondition(IMatchAnalyzer matchClause, Neo4jSchema schema);
    public abstract IExpression generateWithCondition(IWithAnalyzer withClause, Neo4jSchema schema);
}
