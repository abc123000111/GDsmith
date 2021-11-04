package gdsmith.neo4j.gen;

import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.cypher.ast.*;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.ast.ClauseSequence;
import gdsmith.neo4j.dsl.*;

import java.util.List;

public class Neo4jNaiveQueryGenerator {

    public CypherQueryAdapter generateQuery(Neo4jGlobalState globalState){
        ClauseSequenceBuilder builder = new ClauseSequenceBuilder();

        ClauseSequence clauseSequence = builder.createMatch().createMatch().createReturn().build(new BasicConditionGenerator(globalState.getSchema()) {
            @Override
            public IExpression generateMatchCondition(IMatch matchClause) {
                return null;
            }

            @Override
            public IExpression generateWithCondition(IWith withClause) {
                return null;
            }
        },
                new BasicAliasGenerator(globalState.getSchema(), builder.getIdentifierBuilder()) {
                    @Override
                    public List<IRet> generateReturnAlias(IReturn returnClause, IIdentifierBuilder identifierBuilder) {
                        return null;
                    }

                    @Override
                    public List<IRet> generateWithAlias(IWith withClause, IIdentifierBuilder identifierBuilder) {
                        return null;
                    }
                },
                new BasicPatternGenerator(globalState.getSchema(), builder.getIdentifierBuilder()) {
                    @Override
                    public IPatternTuple generatePattern(IMatch matchClause, IIdentifierBuilder identifierBuilder) {
                        return null;
                    }
                });

        return new CypherQueryAdapter("");
    }
}
