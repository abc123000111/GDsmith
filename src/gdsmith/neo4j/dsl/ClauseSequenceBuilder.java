package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.*;
import gdsmith.neo4j.Neo4jSchema;
import gdsmith.neo4j.ast.ClauseSequence;
import gdsmith.neo4j.ast.Match;
import gdsmith.neo4j.ast.Return;
import gdsmith.neo4j.ast.With;

import java.util.Arrays;

public class ClauseSequenceBuilder {

    protected final IdentifierBuilder identifierBuilder;
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
                                    IPatternGenerator patternGenerator, Neo4jSchema schema){
            new QueryFiller(builder.clauseSequence, patternGenerator, conditionGenerator, aliasGenerator,
                    schema, builder.identifierBuilder).startVisit();
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
        createMatch(null);
        return this;
    }

    public ClauseSequenceBuilder createMatch(IExpression condition, IPattern ...patternTuple){
        IMatch match = new Match();
        match.setPatternTuple(Arrays.asList(patternTuple));
        match.setCondition(condition);
        clauseSequence.addClause(match);
        return this;
    }

    public ClauseSequenceBuilder createWith(){
        createWith(null);
        return this;
    }

    public ClauseSequenceBuilder createWith(IExpression condition, IRet ...aliasTuple){
        IWith with = new With();
        with.setCondition(condition);
        with.setReturnList(Arrays.asList(aliasTuple));
        clauseSequence.addClause(with);
        return this;
    }

    public FinishedBuilder createReturn(){
        clauseSequence.addClause(new Return());
        return new FinishedBuilder(this);
    }

}
