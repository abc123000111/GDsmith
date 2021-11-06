package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.IIdentifier;
import gdsmith.cypher.ast.IRet;

public class Ret implements IRet {
    private boolean isAll;
    private IExpression expression;
    private IIdentifier identifier;

    public Ret(IIdentifier identifier){
        this.identifier = identifier;
        isAll = false;
    }

    public Ret(IExpression expression, String name){
        this.expression = expression;
        this.identifier = new Alias(name);
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
    public IExpression getExpression() {
        return expression;
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
