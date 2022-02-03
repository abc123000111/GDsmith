package gdsmith.cypher.ast;

import gdsmith.cypher.ast.analyzer.IUnwindAnalyzer;
import gdsmith.cypher.ast.analyzer.IWithAnalyzer;

public interface IUnwind extends ICypherClause{
    IRet getListAsAliasRet();
    void setListAsAliasRet(IRet listAsAlias);

    @Override
    IUnwindAnalyzer toAnalyzer();
}
