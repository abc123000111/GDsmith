package gdsmith.cypher.dsl;

import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;

public interface IPatternGenerator {
    void fillMatchPattern(IMatchAnalyzer match);
}
