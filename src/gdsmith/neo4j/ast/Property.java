package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.ICypherType;
import gdsmith.cypher.ast.IProperty;

public class Property implements IProperty {
    private String key;
    private Object value;
    private Neo4jType type;

    public Property(String key, Neo4jType type, Object value){
        this.key = key;
        this.value = value;
        this.type = type;
    }

    @Override
    public ICypherType getType() {
        return type;
    }

    @Override
    public Object getVal() {
        return value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }
}
