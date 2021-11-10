package gdsmith.cypher.ast;

import java.util.List;

public interface ICreate extends ICypherClause{
    List<IPattern> getPatternTuple();
    void setPatternTuple(List<IPattern> patternTuple);
    IExpression getCondition();
    void setCondition(IExpression condition);
}
