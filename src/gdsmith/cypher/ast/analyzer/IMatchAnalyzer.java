package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ast.IMatch;

public interface IMatchAnalyzer extends IMatch, IClauseAnalyzer {
    @Override
    IMatch getSource();
}
