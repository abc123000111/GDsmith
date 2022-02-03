package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ast.IUnwind;

public interface IUnwindAnalyzer extends IUnwind, IClauseAnalyzer {
    @Override
    IUnwind getSource();
}
