package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;

public class Ret implements IRet {
    private boolean isAll;
    private IExpression expression = null;
    private IIdentifier identifier = null;

    public Ret(IIdentifier identifier){
        this.identifier = identifier;
        isAll = false;
    }

    public Ret(IExpression expression, String name){
        this.identifier = new Alias(name, expression);
        isAll = false;
    }

    public Ret(IExpression expression){
        this.expression = expression;
        isAll = false;
    }

    public Ret(){
        isAll = true;
    }


    @Override
    public boolean isAll() {
        return isAll;
    }

    @Override
    public void setAll(boolean isAll) {
        this.isAll = isAll;
    }

    @Override
    public boolean isNodeIdentifier() {
        return identifier instanceof INodeIdentifier;
    }

    @Override
    public boolean isRelationIdentifier() {
        return identifier instanceof IRelationIdentifier;
    }

    @Override
    public boolean isAnonymousExpression() {
        return expression != null;
    }

    @Override
    public boolean isAlias() {
        return identifier instanceof IAlias;
    }

    @Override
    public IExpression getExpression() {
        if(identifier == null)
            return expression;
        if(identifier instanceof IAlias)
            return ((IAlias) identifier).getExpression();
        return null;
    }

    @Override
    public IIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        if(isAll()){
            sb.append("*");
            return;
        }
        if(expression != null){
            expression.toTextRepresentation(sb);
            if(identifier == null) {
                return;
            }
            sb.append(" AS ");
        }
        sb.append(identifier.getName());
    }
}
