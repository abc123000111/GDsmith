package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;
import gdsmith.neo4j.dsl.*;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.Arrays;

public interface IClauseSequenceBuilder {
    IIdentifierBuilder getIdentifierBuilder();

    IOngoingMatch MatchClause();
    IOngoingMatch MatchClause(IExpression condition, IPattern...patternTuple);

    interface IOngoingMatch extends IClauseSequenceBuilder{
    }

    IOngoingWith WithClause();
    IOngoingWith WithClause(IExpression condition, IRet...aliasTuple);

    interface IOngoingWith extends IClauseSequenceBuilder{
        IOngoingWith orderBy(boolean isDesc, IExpression ...expression);
        IOngoingWith limit(IExpression expression);
        IOngoingWith skip(IExpression expression);
        IOngoingWith distinct();
    }

    IOngoingReturn ReturnClause(IRet ...returnList);

    interface IOngoingReturn extends IClauseSequenceBuilder{
        IOngoingReturn orderBy(boolean isDesc, IExpression ...expression);
        IOngoingReturn limit(IExpression expression);
        IOngoingReturn skip(IExpression expression);
        IOngoingReturn distinct();
    }

    IClauseSequenceBuilder CreateClause();
    IClauseSequenceBuilder CreateClause(IPattern pattern);

    IClauseSequenceBuilder MergeClause();
    IClauseSequenceBuilder MergeClause(IPattern pattern);

    public IClauseSequence build(IConditionGenerator conditionGenerator, IAliasGenerator aliasGenerator,
                                IPatternGenerator patternGenerator, Neo4jSchema schema);

    public IClauseSequence build();
}
