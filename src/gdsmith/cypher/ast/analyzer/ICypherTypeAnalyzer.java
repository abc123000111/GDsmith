package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ast.ICypherType;

import java.util.List;

public interface ICypherTypeAnalyzer {
    ICypherType getType();
    boolean isBasicType();
    boolean isNodeOrRelation();
}
