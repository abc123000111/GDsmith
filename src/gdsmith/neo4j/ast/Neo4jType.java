package gdsmith.neo4j.ast;

import gdsmith.Randomly;
import gdsmith.cypher.ast.ICypherType;

public enum Neo4jType implements ICypherType {
    NUMBER, BOOLEAN, STRING, NODE, RELATION, UNKNOWN;

    public static Neo4jType getRandomBasicType(){
        Randomly randomly = new Randomly();
        return NUMBER;
    }
}
