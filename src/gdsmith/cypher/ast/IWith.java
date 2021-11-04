package gdsmith.cypher.ast;

import java.util.List;

public interface IWith extends ICypherClause{
    boolean isDistinct();
    void setDistinct(boolean isDistinct);
    List<IRet> getReturnList();
    void setReturnList(List<IRet> returnList);
    IExpression getCondtion();
    void setCondition(IExpression condtion);
}
