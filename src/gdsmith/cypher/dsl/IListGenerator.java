package gdsmith.cypher.dsl;

import gdsmith.cypher.ast.analyzer.IUnwindAnalyzer;

public interface IListGenerator {
    void fillUnwindList(IUnwindAnalyzer unwindAnalyzer);
}
