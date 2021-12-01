package gdsmith.cypher.ast;

import gdsmith.cypher.ast.analyzer.IClauseAnalyzer;
import gdsmith.cypher.ast.analyzer.IRelationAnalyzer;
import gdsmith.cypher.ast.analyzer.IReturnAnalyzer;

import java.util.List;

public interface IReturn extends ICypherClause{
    List<IRet> getReturnList();
    void setReturnList(List<IRet> returnList);

    void setDistinct(boolean distinct);
    boolean isDistinct();

    void setOrderBy(IExpression expression);
    IExpression getOrderBy();

    void setLimit(IExpression expression);
    IExpression getLimit();

    void setSkip(IExpression expression);
    IExpression getSkip();

    @Override
    IReturnAnalyzer toAnalyzer();
}
