package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ast.ILabel;
import gdsmith.cypher.ast.INodeIdentifier;
import gdsmith.cypher.ast.IProperty;

import java.util.List;

public interface INodeAnalyzer extends INodeIdentifier {
    INodeAnalyzer getFormerDef();
    void setFormerDef(INodeAnalyzer formerDef);
    List<ILabel> getAllLabelsInDefChain();
    List<IProperty> getAllPropertiesInDefChain();
}
