package gdsmith.cypher.ast;

import gdsmith.cypher.ast.analyzer.IClauseAnalyzer;
import gdsmith.cypher.ast.analyzer.IWithAnalyzer;

import java.util.List;

public interface IWith extends ICypherClause{
    boolean isDistinct();
    void setDistinct(boolean isDistinct);
    List<IRet> getReturnList();
    void setReturnList(List<IRet> returnList);
    IExpression getCondtion();
    void setCondition(IExpression condtion);

    @Override
    IWithAnalyzer toAnalyzer();
}
