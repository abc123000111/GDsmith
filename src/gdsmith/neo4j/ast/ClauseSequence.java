package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;
import gdsmith.neo4j.dsl.*;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClauseSequence implements IClauseSequence {

    List<ICypherClause> clauses = new ArrayList<>();
    IIdentifierBuilder identifierBuilder;

    public ClauseSequence(IIdentifierBuilder identifierBuilder){
        this.identifierBuilder = identifierBuilder;
    }

    @Override
    public List<ICypherClause> getClauseList() {
        return clauses;
    }

    @Override
    public IClauseSequence getCopy() {
        ClauseSequence clauseSequence = new ClauseSequence(identifierBuilder.getCopy());
        clauses.stream().forEach(c->{clauseSequence.addClause(c.getCopy());});
        return clauseSequence;
    }

    public void addClause(ICypherClause clause){
        if(clauses.size() != 0 ) {
            clauses.get(clauses.size() - 1).setNextClause(clause);
        }
        clauses.add(clause);
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        for(int i = 0; i < clauses.size(); i ++){
            clauses.get(i).toTextRepresentation(sb);
            if(i != clauses.size() - 1){
                sb.append(" ");
            }
        }
    }

    public static class ClauseSequenceBuilder {

        protected final IdentifierBuilder identifierBuilder;
        private final ClauseSequence clauseSequence;

        public static class IdentifierBuilder implements IIdentifierBuilder {
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

            @Override
            public IIdentifierBuilder getCopy() {
                IdentifierBuilder identifierBuilder = new IdentifierBuilder();
                identifierBuilder.nodeNum = this.nodeNum;
                identifierBuilder.aliasNum = this.aliasNum;
                identifierBuilder.relationNum = this.relationNum;
                return identifierBuilder;
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

            public ClauseSequenceBuilder WithClause(IExpression condition, IRet...aliasTuple){
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
            clauseSequence = new ClauseSequence(identifierBuilder);
        }

        /**
         * 从一个sequence开始继续生成，sequence必须是一个查询语句
         * @param sequence
         */
        public ClauseSequenceBuilder(ClauseSequence sequence){
            clauseSequence = (ClauseSequence) sequence.getCopy();
            identifierBuilder = (IdentifierBuilder) clauseSequence.identifierBuilder;
            if(clauseSequence.clauses.get(clauseSequence.clauses.size()-1) instanceof IReturn){
                clauseSequence.clauses.remove(clauseSequence.clauses.size()-1);
            }
        }

        public IIdentifierBuilder getIdentifierBuilder(){
            return identifierBuilder;
        }



        public ClauseSequenceBuilder MatchClause(){
            return MatchClause(null);
        }

        public ClauseSequenceBuilder MatchClause(IExpression condition, IPattern...patternTuple){
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

        public ClauseSequenceBuilder MergeClause(IPattern pattern){
            IMerge merge = new Merge();
            merge.setPattern(pattern);
            clauseSequence.addClause(merge);
            return this;
        }

        public ClauseSequenceBuilder MergeClause(){
            return MergeClause(null);
        }

    }

}
