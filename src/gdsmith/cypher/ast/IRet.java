package gdsmith.cypher.ast;

public interface IRet {
    boolean isAll();
    void setAll(boolean isAll);


    IExpression getExpression();
    IIdentifier getIdentifier();
}
