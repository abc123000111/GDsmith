package gdsmith.cypher.ast;

public interface IProperty {
    ICypherType getType();
    Object getVal();
    String getKey();
    void setKey(String key);
}
