package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ast.ILabel;
import gdsmith.cypher.ast.IProperty;
import gdsmith.cypher.ast.IRelationIdentifier;
import gdsmith.cypher.ast.IType;

import java.util.List;

public interface IRelationAnalyzer extends IRelationIdentifier {
    IRelationAnalyzer getFormerDef();
    void setFormerDef(IRelationAnalyzer formerDef);
    List<IType> getAllRelationTypesInDefChain();
    List<IProperty> getAllPropertiesInDefChain();
}
