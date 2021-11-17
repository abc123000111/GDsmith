package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;
import gdsmith.neo4j.dsl.IIdentifierBuilder;

public class Ret implements IRet {
    private boolean isAll;
    private IExpression expression = null;
    private IIdentifier identifier = null;

    public static Ret createAliasRef(IAlias alias){
        return new Ret(new Alias(alias.getName(), null));
    }

    public static Ret createNodeRef(INodeIdentifier node){
        return new Ret(NodeIdentifier.createNodeRef(node));
    }

    public static Ret createRelationRef(IRelationIdentifier relation){
        return new Ret(RelationIdentifier.createRelationRef(relation, Direction.NONE, 1, 1));
    }

    public static Ret createNewExpressionAlias(IIdentifierBuilder identifierBuilder, IExpression expression){
        return new Ret(expression, identifierBuilder.getNewAliasName());
    }

    public static Ret createNewExpressionReturnVal(IExpression expression){
        return new Ret(expression);
    }

    public static Ret createStar(){
        return new Ret();
    }

    Ret(IIdentifier identifier){
        this.identifier = identifier;
        isAll = false;
    }

    Ret(IExpression expression, String name){
        this.identifier = new Alias(name, expression);
        isAll = false;
    }

    Ret(IExpression expression){
        this.expression = expression;
        isAll = false;
    }

    Ret(){
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
