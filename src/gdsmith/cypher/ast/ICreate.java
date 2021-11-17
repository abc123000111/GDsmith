package gdsmith.cypher.ast;

import gdsmith.cypher.ast.analyzer.ICreateAnalyzer;
import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;

public interface ICreate extends ICypherClause{
    IPattern getPattern();
    void setPattern(IPattern pattern);

    @Override
    ICreateAnalyzer toAnalyzer();
}
