package gdsmith.cypher.standard_ast;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.dsl.*;
import gdsmith.neo4j.schema.Neo4jSchema;

public interface IClauseSequenceBuilder {
    IIdentifierBuilder getIdentifierBuilder();

    IOngoingMatch MatchClause();
    IOngoingMatch MatchClause(IExpression condition, IPattern...patternTuple);

    IOngoingMatch OptionalMatchClause();
    IOngoingMatch OptionalMatchClause(IExpression condition, IPattern...patternTuple);

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

    //public IClauseSequence build(IConditionGenerator conditionGenerator, IAliasGenerator aliasGenerator,
    //                            IPatternGenerator patternGenerator, Neo4jSchema schema);

    public IClauseSequence build();
}
