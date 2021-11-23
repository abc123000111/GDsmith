package gdsmith.cypher;

import gdsmith.cypher.ast.ILabel;
import gdsmith.cypher.ast.IType;
import gdsmith.neo4j.schema.ILabelInfo;
import gdsmith.neo4j.schema.IRelationTypeInfo;

public interface ICypherSchema {
    boolean containsLabel(ILabel label);
    ILabelInfo getLabelInfo(ILabel label);
    boolean containsRelationType(IType relation);
    IRelationTypeInfo getRelationInfo(IType relation);
}
