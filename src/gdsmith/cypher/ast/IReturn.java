package gdsmith.cypher.ast;

import gdsmith.cypher.ast.analyzer.IClauseAnalyzer;
import gdsmith.cypher.ast.analyzer.IRelationAnalyzer;
import gdsmith.cypher.ast.analyzer.IReturnAnalyzer;

import java.util.List;

public interface IReturn extends ICypherClause{
    List<IRet> getReturnList();
    void setReturnList(List<IRet> returnList);

    @Override
    IReturnAnalyzer toAnalyzer();
}
