package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.ICypherType;
import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.IProperty;

public class Property implements IProperty {
    private String key;
    private IExpression value;
    private Neo4jType type;

    public Property(String key, Neo4jType type, IExpression value){
        this.key = key;
        this.value = value;
        this.type = type;
    }

    @Override
    public ICypherType getType() {
        return type;
    }

    @Override
    public IExpression getVal() {
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

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append(key).append(":");
        value.toTextRepresentation(sb);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Property)){
            return false;
        }
        return ((Property) o).key.equals(key);
    }

    @Override
    public int hashCode(){
        return key.hashCode();
    }

}
