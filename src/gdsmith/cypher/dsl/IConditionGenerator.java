package gdsmith.cypher.dsl;

import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;
import gdsmith.cypher.ast.analyzer.IWithAnalyzer;

public interface IConditionGenerator {
    void fillMatchCondtion(IMatchAnalyzer matchClause);
    void fillWithCondition(IWithAnalyzer withClause);
}
