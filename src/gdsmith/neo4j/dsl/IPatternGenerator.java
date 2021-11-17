package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.IMatch;
import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;
import gdsmith.neo4j.ast.Match;

public interface IPatternGenerator {
    void fillMatchPattern(IMatchAnalyzer match);
}
