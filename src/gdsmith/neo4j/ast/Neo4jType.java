package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.ICypherType;

public enum Neo4jType implements ICypherType {
    INT, STRING, NODE, RELATION, UNKNOWN
}
