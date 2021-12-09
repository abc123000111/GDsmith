package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ICypherSchema;
import gdsmith.cypher.ast.*;
import gdsmith.cypher.schema.IPropertyInfo;

import java.util.List;

public interface INodeAnalyzer extends INodeIdentifier, IIdentifierAnalyzer {
    @Override
    INodeIdentifier getSource();
    @Override
    INodeAnalyzer getFormerDef();
    void setFormerDef(INodeAnalyzer formerDef);

    /**
     * 从该处定义回溯，所有对该节点的定义中出现的Label
     * @return
     */
    List<ILabel> getAllLabelsInDefChain();

    /**
     * 从该处回溯，所有对该节点的定义中出现的property
     * @return
     */
    List<IProperty> getAllPropertiesInDefChain();
    List<IPropertyInfo> getAllPropertiesAvailable(ICypherSchema schema);
    List<IPropertyInfo> getAllPropertiesWithType(ICypherSchema schema, ICypherType type);
}
