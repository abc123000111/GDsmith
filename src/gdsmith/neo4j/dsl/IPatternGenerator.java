package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.IMatch;
import gdsmith.neo4j.ast.Match;

public interface IPatternGenerator {
    void fillMatchPattern(IMatch match);
}
