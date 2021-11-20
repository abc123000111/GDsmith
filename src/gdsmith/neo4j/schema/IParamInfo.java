package gdsmith.neo4j.schema;

import gdsmith.neo4j.ast.Neo4jType;

public interface IParamInfo {
    boolean isOptionalLength();
    Neo4jType getParamType();
}
