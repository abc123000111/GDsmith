package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.*;
import gdsmith.neo4j.ast.*;
import gdsmith.neo4j.schema.Neo4jSchema;

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

        public ClauseSequence build(){
            return builder.clauseSequence;
        }
    }

    public static class OngoingSequenceCreate{
        private ClauseSequenceBuilder builder;

        private OngoingSequenceCreate(ClauseSequenceBuilder builder){
            this.builder = builder;
        }

        public ClauseSequenceBuilder WithClause(){
            return builder.WithClause();
        }

        public ClauseSequenceBuilder WithClause(IExpression condition, IRet ...aliasTuple){
            return builder.WithClause(condition, aliasTuple);
        }

        public FinishedBuilder ReturnClause(IRet ...returnList){
            return builder.ReturnClause(returnList);
        }

        public ClauseSequence build(){
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



    public ClauseSequenceBuilder MatchClause(){
        return MatchClause(null);
    }

    public ClauseSequenceBuilder MatchClause(IExpression condition, IPattern ...patternTuple){
        IMatch match = new Match();
        match.setPatternTuple(Arrays.asList(patternTuple));
        match.setCondition(condition);
        clauseSequence.addClause(match);
        return this;
    }

    public ClauseSequenceBuilder WithClause(){
        return WithClause(null);
    }


    public ClauseSequenceBuilder WithClause(IExpression condition, IRet ...aliasTuple){
        IWith with = new With();
        with.setCondition(condition);
        with.setReturnList(Arrays.asList(aliasTuple));
        clauseSequence.addClause(with);
        return this;
    }


    public FinishedBuilder ReturnClause(IRet ...returnList){
        IReturn returnClause = new Return();
        returnClause.setReturnList(Arrays.asList(returnList));
        clauseSequence.addClause(returnClause);
        return new FinishedBuilder(this);
    }

    public ClauseSequenceBuilder CreateClause(IPattern pattern){
        ICreate create = new Create();
        create.setPattern(pattern);
        clauseSequence.addClause(create);
        return this;
    }

    public ClauseSequenceBuilder CreateClause(){
        return CreateClause(null);
    }

}
