package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ast.ICypherType;

import java.util.List;

public interface ICypherTypeDescriptor {
    ICypherType getType();
    boolean isBasicType();
    boolean isNodeOrRelation();
    boolean isNode();
    boolean isRelation();
    boolean isList();
    boolean isMap();
    IListDescriptor getListDescriptor();
    IMapDescriptor getMapDescriptor();
    INodeAnalyzer getNodeAnalyzer();
    IRelationAnalyzer getRelationAnalyzer();
}
