package gdsmith.neo4j;

import gdsmith.cypher.ast.ICypherType;

public interface IPropertyInfo {
    String getKey();
    ICypherType getType();
}
