package gdsmith.neo4j.gen.random;

import gdsmith.Randomly;
import gdsmith.cypher.ast.IClauseSequence;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.ast.ClauseSequence;
import gdsmith.neo4j.ast.IClauseSequenceBuilder;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;

public class RandomQueryGenerator {

    public static class Seed{
        IClauseSequence sequence;
        boolean bugDetected;
        int resultLength;

        public Seed(IClauseSequence sequence, boolean bugDetected, int resultLength){
            this.sequence = sequence;
            this.bugDetected = bugDetected;
            this.resultLength = resultLength;
        }
    }

    private List<Seed> seeds = new ArrayList<>();

    private IClauseSequenceBuilder generateClauses(IClauseSequenceBuilder seq, int len) {
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

    public IClauseSequence generateQuery(Neo4jGlobalState globalState){

        Neo4jSchema schema = globalState.getSchema();
        Randomly r = new Randomly();
        IClauseSequence sequence;

        boolean isNotFromSeeds = Randomly.getBooleanWithRatherLowProbability();
        if (isNotFromSeeds || seeds.size() == 0) {
            IClauseSequenceBuilder builder = ClauseSequence.createClauseSequenceBuilder();
            int numOfClauses = r.getInteger(1, 8);
            sequence = generateClauses(builder.MatchClause(), numOfClauses).ReturnClause().build(new RandomConditionGenerator(schema),
                    new RandomAliasGenerator(schema, builder.getIdentifierBuilder()),
                    new RandomPatternGenerator(schema, builder.getIdentifierBuilder()), schema);
        } else {
            IClauseSequence seedSeq = seeds.get(r.getInteger(0, seeds.size() - 1)).sequence;
            IClauseSequenceBuilder builder = ClauseSequence.createClauseSequenceBuilder(seedSeq);
            int numOfClauses = Randomly.smallNumber();
            sequence = generateClauses(builder, numOfClauses).ReturnClause().build(new RandomConditionGenerator(schema),
                    new RandomAliasGenerator(schema, builder.getIdentifierBuilder()),
                    new RandomPatternGenerator(schema, builder.getIdentifierBuilder()), schema);
        }

        return sequence;
    }
}
