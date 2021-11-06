package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.IAlias;
import gdsmith.cypher.ast.ICypherType;

public class Alias implements IAlias {
    private String name;

    public Alias(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ICypherType getType() {
        return Neo4jType.UNKNOWN;
    }
}
