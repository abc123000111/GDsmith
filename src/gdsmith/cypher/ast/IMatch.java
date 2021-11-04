package gdsmith.cypher.ast;

public interface IMatch extends ICypherClause{
    IPatternTuple getPatternTuple();
    void setPatternTuple(IPatternTuple patternTuple);
    boolean isOptional();
    void setOptional(boolean optional);
    IExpression getCondition();
    void setCondition(IExpression condition);
}
