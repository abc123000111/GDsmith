package gdsmith.cypher.dsl;

import gdsmith.cypher.ast.analyzer.IReturnAnalyzer;
import gdsmith.cypher.ast.analyzer.IWithAnalyzer;

public interface IAliasGenerator {
    void fillReturnAlias(IReturnAnalyzer returnClause);
    void fillWithAlias(IWithAnalyzer withClause);
}
