package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ast.IMerge;

public interface IMergeAnalyzer extends IMerge, IClauseAnalyzer {
    @Override
    IMerge getSource();
}
