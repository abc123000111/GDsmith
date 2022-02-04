package gdsmith.cypher;

import gdsmith.cypher.ast.ILabel;
import gdsmith.cypher.ast.IType;
import gdsmith.cypher.schema.IFunctionInfo;
import gdsmith.cypher.schema.ILabelInfo;
import gdsmith.cypher.schema.IRelationTypeInfo;

import java.util.List;

public interface ICypherSchema {
    boolean containsLabel(ILabel label);
    ILabelInfo getLabelInfo(ILabel label);
    boolean containsRelationType(IType relation);
    IRelationTypeInfo getRelationInfo(IType relation);
    List<IFunctionInfo> getFunctions();
}
