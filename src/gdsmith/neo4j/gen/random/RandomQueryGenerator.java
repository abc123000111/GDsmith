package gdsmith.neo4j.gen.random;

import gdsmith.Randomly;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.ast.ClauseSequence;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RandomQueryGenerator {

    public static class Seed{
        ClauseSequence sequence;
        boolean bugDetected;
        int resultLength;

        public Seed(ClauseSequence sequence, boolean bugDetected, int resultLength){
            this.sequence = sequence;
            this.bugDetected = bugDetected;
            this.resultLength = resultLength;
        }
    }

    private List<Seed> seeds = new ArrayList<>();

    private ClauseSequence.ClauseSequenceBuilder generateClauses(ClauseSequence.ClauseSequenceBuilder seq, int len) {
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

    public void addSeed(Seed seed){
        //todo 判断是否需要加到seed中，如果需要，则加入seeds
        if (seed.sequence.getClauseList().size() <= 12) {
            seeds.add(seed);
        }
    }

    public ClauseSequence generateQuery(Neo4jGlobalState globalState){

        Neo4jSchema schema = globalState.getSchema();
        Randomly r = new Randomly();
        ClauseSequence sequence;

        boolean isNotFromSeeds = Randomly.getBooleanWithRatherLowProbability();
        if (isNotFromSeeds || seeds.size() == 0) {
            ClauseSequence.ClauseSequenceBuilder builder = new ClauseSequence.ClauseSequenceBuilder();
            int numOfClauses = r.getInteger(1, 8);
            sequence = generateClauses(builder.MatchClause(), numOfClauses).ReturnClause().build(new RandomConditionGenerator(schema),
                    new RandomAliasGenerator(schema, builder.getIdentifierBuilder()),
                    new RandomPatternGenerator(schema, builder.getIdentifierBuilder()), schema);
        } else {
            ClauseSequence seedSeq = seeds.get(r.getInteger(0, seeds.size() - 1)).sequence;
            ClauseSequence.ClauseSequenceBuilder builder = new ClauseSequence.ClauseSequenceBuilder(seedSeq);
            int numOfClauses = Randomly.smallNumber();
            sequence = generateClauses(builder, numOfClauses).ReturnClause().build(new NewConditionGenerator(schema),
                    new NewAliasGenerator(schema, builder.getIdentifierBuilder()),
                    new NewPatternGenerator(schema, builder.getIdentifierBuilder()), schema);
        }

        return sequence;
    }
}
