package gdsmith.cypher.ast;

public interface IRet extends ITextRepresentation {
    boolean isAll();
    void setAll(boolean isAll);
    boolean isNodeIdentifier();
    boolean isRelationIdentifier();
    boolean isAnonymousExpression();
    boolean isAlias();


    IExpression getExpression();
    IIdentifier getIdentifier();
}
