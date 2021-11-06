package gdsmith.cypher.ast;

public interface IRet extends ITextRepresentation {
    boolean isAll();
    void setAll(boolean isAll);


    IExpression getExpression();
    IIdentifier getIdentifier();
}
