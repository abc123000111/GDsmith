package gdsmith.neo4j.gen.random;

import gdsmith.Randomly;
import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;
import gdsmith.cypher.ast.analyzer.IWithAnalyzer;
import gdsmith.cypher.dsl.BasicConditionGenerator;
import gdsmith.neo4j.schema.Neo4jSchema;

public class RandomConditionGenerator extends BasicConditionGenerator {
    public RandomConditionGenerator(Neo4jSchema schema) {
        super(schema);
    }

    private static final int NO_CONDITION_RATE = 20, MAX_DEPTH = 3;

    @Override
    public IExpression generateMatchCondition(IMatchAnalyzer matchClause, Neo4jSchema schema) {
        IExpression matchCondition = matchClause.getCondition();
        if (matchCondition != null) {
            return matchCondition;
        }

        Randomly r = new Randomly();
        r.getString();
        if(r.getInteger(0, 100)< NO_CONDITION_RATE){
            return null;
        }
        return new RandomExpressionGenerator(matchClause, schema).generateCondition(MAX_DEPTH);
    }

    @Override
    public IExpression generateWithCondition(IWithAnalyzer withClause, Neo4jSchema schema) {
        IExpression withCondition = withClause.getCondtion();
        if (withCondition != null) {
            return withCondition;
        }

        Randomly r = new Randomly();
        if(r.getInteger(0, 100)< NO_CONDITION_RATE){
            return null;
        }
        return new RandomExpressionGenerator(withClause, schema).generateCondition(MAX_DEPTH);
    }
}
