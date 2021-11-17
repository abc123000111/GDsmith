package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ast.IWith;

public interface IWithAnalyzer extends IWith, IClauseAnalyzer {
    @Override
    IWith getSource();
}
