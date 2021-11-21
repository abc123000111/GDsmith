package gdsmith.neo4j.gen.random;

import gdsmith.Randomly;
import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.ast.ClauseSequence;
import gdsmith.neo4j.dsl.ClauseSequenceBuilder;
import gdsmith.neo4j.gen.examples.NaiveAliasGenerator;
import gdsmith.neo4j.gen.examples.NaiveConditionGenerator;
import gdsmith.neo4j.gen.examples.NaivePatternGenerator;
import gdsmith.neo4j.schema.Neo4jSchema;

public class RandomQueryGenerator {

    private ClauseSequenceBuilder generateClauses(ClauseSequenceBuilder seq, int len) {
        if (len == 0) {
            return seq;
        }
        boolean generateWith = Randomly.getBooleanWithRatherLowProbability();
        //boolean generateWith = Randomly.getBoolean();
        if (generateWith) {
            return generateClauses(seq.WithClause(), len - 1);
        } else {
            return generateClauses(seq.MatchClause(), len - 1);
        }
    }

    public ClauseSequence generateQuery(Neo4jGlobalState globalState){
        ClauseSequenceBuilder builder = new ClauseSequenceBuilder();
        Neo4jSchema schema = globalState.getSchema();
        Randomly r = new Randomly();

        int numOfClauses = r.getInteger(1, 10);
        /* int numOfClauses = Randomly.smallNumber();
        if (numOfClauses <= 0) {
            numOfClauses = 1;
        } */
        ClauseSequence sequence = generateClauses(builder, numOfClauses).ReturnClause().build(new RandomConditionGenerator(schema),
                new RandomAliasGenerator(schema, builder.getIdentifierBuilder()),
                new RandomPatternGenerator(schema, builder.getIdentifierBuilder()), schema);
        
        return sequence;
    }
}
