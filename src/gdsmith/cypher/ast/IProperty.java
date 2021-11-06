package gdsmith.cypher.ast;

public interface IProperty extends ITextRepresentation{
    ICypherType getType();
    IExpression getVal();
    String getKey();
    void setKey(String key);
}
