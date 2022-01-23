package gdsmith.cypher.gen.random;

import gdsmith.Randomly;
import gdsmith.cypher.CypherGlobalState;
import gdsmith.cypher.ast.IClauseSequence;
import gdsmith.cypher.dsl.QueryFiller;
import gdsmith.cypher.schema.CypherSchema;
import gdsmith.cypher.standard_ast.ClauseSequence;
import gdsmith.cypher.standard_ast.IClauseSequenceBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomQueryGenerator<S extends CypherSchema<G,?>,G extends CypherGlobalState<?,S>> {

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

    private IClauseSequenceBuilder generateClauses(IClauseSequenceBuilder seq, int len, List<String> generateClause) {
        if (len == 0) {
            return seq;
        }
        Randomly r = new Randomly();
        String generate = generateClause.get(r.getInteger(0, generateClause.size() - 1));
        if (generate == "MATCH") {
            return generateClauses(seq.MatchClause(), len - 1, Arrays.asList("MATCH", "OPTIONAL MATCH", "WITH"));
        } else if (generate == "OPTIONAL MATCH") {
            return generateClauses(seq.OptionalMatchClause(), len - 1, Arrays.asList("MATCH", "OPTIONAL MATCH", "WITH"));
            // return generateClauses(seq.OptionalMatchClause(), len - 1, Arrays.asList("OPTIONAL MATCH", "WITH")); //todo
        } else {
            return generateClauses(seq.WithClause(), len - 1, Arrays.asList("MATCH", "OPTIONAL MATCH", "WITH"));
        }
    }

    public void addSeed(Seed seed){
        //todo 判断是否需要加到seed中，如果需要，则加入seeds
        if (seed.sequence.getClauseList().size() <= 6) {
            seeds.add(seed);
        }
    }

    public IClauseSequence generateQuery(G globalState){

        S schema = globalState.getSchema();
        Randomly r = new Randomly();
        IClauseSequence sequence;

        //boolean isNotFromSeeds = Randomly.getBooleanWithRatherLowProbability();
        boolean isNotFromSeeds = Randomly.getBoolean();
        if (isNotFromSeeds || seeds.size() == 0) {
            IClauseSequenceBuilder builder = ClauseSequence.createClauseSequenceBuilder();
            int numOfClauses = r.getInteger(1, 3);
            sequence = generateClauses(builder.MatchClause(), numOfClauses, Arrays.asList("MATCH", "OPTIONAL MATCH", "WITH")).ReturnClause().build();
            new QueryFiller<S>(sequence,
                    new RandomPatternGenerator<>(schema, builder.getIdentifierBuilder()),
                    new RandomConditionGenerator<>(schema),
                    new RandomAliasGenerator<>(schema, builder.getIdentifierBuilder()),
                    schema, builder.getIdentifierBuilder()).startVisit();
        } else {
            IClauseSequence seedSeq = seeds.get(r.getInteger(0, seeds.size() - 1)).sequence;
            IClauseSequenceBuilder builder = ClauseSequence.createClauseSequenceBuilder(seedSeq);
            int numOfClauses = Randomly.smallNumber();
            sequence = generateClauses(builder.WithClause(), numOfClauses, Arrays.asList("MATCH", "OPTIONAL MATCH", "WITH")).ReturnClause().build();
            new QueryFiller<S>(sequence,
                    new RandomPatternGenerator<>(schema, builder.getIdentifierBuilder()),
                    new RandomConditionGenerator<>(schema),
                    new RandomAliasGenerator<>(schema, builder.getIdentifierBuilder()),
                    schema, builder.getIdentifierBuilder()).startVisit();
        }

        return sequence;
    }
}
