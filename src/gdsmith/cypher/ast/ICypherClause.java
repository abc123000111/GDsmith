package gdsmith.cypher.ast;

import gdsmith.cypher.ast.analyzer.IClauseAnalyzer;

import java.util.List;

public interface ICypherClause extends ITextRepresentation{
    void setNextClause(ICypherClause next);
    ICypherClause getNextClause();
    void setPrevClause(ICypherClause prev);
    ICypherClause getPrevClause();
    IClauseAnalyzer toAnalyzer();


}
