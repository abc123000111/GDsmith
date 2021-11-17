package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ast.IReturn;

public interface IReturnAnalyzer extends IReturn, IClauseAnalyzer {
    @Override
    IReturn getSource();
}
