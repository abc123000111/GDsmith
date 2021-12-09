package gdsmith.cypher.schema;

import gdsmith.cypher.standard_ast.CypherType;

public interface IParamInfo {
    boolean isOptionalLength();
    CypherType getParamType();
}
