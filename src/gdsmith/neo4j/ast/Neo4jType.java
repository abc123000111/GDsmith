package gdsmith.neo4j.ast;

import gdsmith.Randomly;
import gdsmith.cypher.ast.ICypherType;

public enum Neo4jType implements ICypherType {
    INT, BOOLEAN, STRING, NODE, RELATION, UNKNOWN;

    public static Neo4jType getRandomBasicType(){
        Randomly randomly = new Randomly();
        return INT;
    }
}
