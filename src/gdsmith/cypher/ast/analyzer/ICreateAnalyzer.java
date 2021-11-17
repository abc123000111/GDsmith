package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ast.ICreate;

public interface ICreateAnalyzer extends ICreate, IClauseAnalyzer {
    @Override
    ICreate getSource();
}
