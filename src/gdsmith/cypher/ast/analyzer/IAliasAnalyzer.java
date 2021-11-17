package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ast.IAlias;
import gdsmith.cypher.ast.IExpression;

import java.util.List;

public interface IAliasAnalyzer extends IAlias {
    IAliasAnalyzer getFormerDef();
    void setFormerDef(IAliasAnalyzer formerDef);
    IExpression getAliasDefExpression();
}
