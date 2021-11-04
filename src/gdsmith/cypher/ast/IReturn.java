package gdsmith.cypher.ast;

import java.util.List;

public interface IReturn extends ICypherClause{
    List<IRet> getReturnList();
    void setReturnList(List<IRet> returnList);
}
