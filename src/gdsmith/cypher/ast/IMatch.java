package gdsmith.cypher.ast;

import java.util.List;

public interface IMatch extends ICypherClause{
    List<IPattern> getPatternTuple();
    void setPatternTuple(List<IPattern> patternTuple);
    boolean isOptional();
    void setOptional(boolean optional);
    IExpression getCondition();
    void setCondition(IExpression condition);
}
