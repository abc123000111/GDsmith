package gdsmith.cypher.ast;

import gdsmith.cypher.ast.analyzer.IAliasAnalyzer;

public interface IAlias extends IIdentifier{
    IExpression getExpression();
}
