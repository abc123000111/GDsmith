package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.IIdentifier;
import gdsmith.cypher.ast.IRet;

public class Ret implements IRet {
    private boolean isAll;
    private IExpression expression;
    private IIdentifier identifier;

    @Override
    public boolean isAll() {
        return isAll;
    }

    @Override
    public void setAll(boolean isAll) {
        this.isAll = isAll;
    }

    @Override
    public IExpression getExpression() {
        return expression;
    }

    @Override
    public IIdentifier getIdentifier() {
        return identifier;
    }
}
