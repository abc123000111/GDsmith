package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.IAlias;
import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.analyzer.IAliasAnalyzer;

public class AliasAnalyzer extends Alias implements IAliasAnalyzer {
    IAliasAnalyzer formerDef = null;

    AliasAnalyzer(IAlias alias){
        this(alias.getName(), alias.getExpression());
    }

    AliasAnalyzer(String name, IExpression expression) {
        super(name, expression);
    }

    @Override
    public IAliasAnalyzer getFormerDef() {
        return formerDef;
    }

    @Override
    public void setFormerDef(IAliasAnalyzer formerDef) {
        this.formerDef = formerDef;
    }

    @Override
    public IExpression getAliasDefExpression() {
        if(formerDef == null){
            return this.expression;
        }
        return formerDef.getAliasDefExpression();
    }
}
