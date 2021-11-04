package gdsmith.neo4j.dsl;

import gdsmith.neo4j.ast.ClauseSequence;
import gdsmith.neo4j.ast.Match;
import gdsmith.neo4j.ast.Return;
import gdsmith.neo4j.ast.With;

public class ClauseSequenceBuilder {

    private final IdentifierBuilder identifierBuilder;
    private final ClauseSequence clauseSequence;

    public static class IdentifierBuilder implements IIdentifierBuilder{
        public int nodeNum = 0, relationNum = 0, aliasNum = 0;

        private IdentifierBuilder(){

        }

        public String getNewNodeName(){
            nodeNum++;
            return "n"+(nodeNum - 1);
        }

        public String getNewRelationName(){
            relationNum++;
            return "r"+(relationNum - 1);
        }

        public String getNewAliasName(){
            aliasNum++;
            return "a"+(aliasNum - 1);
        }
    }

    public static class FinishedBuilder{
        private ClauseSequenceBuilder builder;

        private FinishedBuilder(ClauseSequenceBuilder builder){
            this.builder = builder;
        }
        public ClauseSequence build(IConditionGenerator conditionGenerator, IAliasGenerator aliasGenerator,
                                    IPatternGenerator patternGenerator){
            return builder.clauseSequence;
        }
    }

    public ClauseSequenceBuilder(){
        identifierBuilder = new IdentifierBuilder();
        clauseSequence = new ClauseSequence();
    }

    public IIdentifierBuilder getIdentifierBuilder(){
        return identifierBuilder;
    }



    public ClauseSequenceBuilder createMatch(){
        clauseSequence.addClause(new Match());
        return this;
    }

    public ClauseSequenceBuilder createWith(){
        clauseSequence.addClause(new With());
        return this;
    }

    public FinishedBuilder createReturn(){
        clauseSequence.addClause(new Return());
        return new FinishedBuilder(this);
    }

}
