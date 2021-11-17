package gdsmith.neo4j.ast;


import gdsmith.cypher.ast.ICypherType;
import gdsmith.cypher.ast.ILabel;
import gdsmith.cypher.ast.INodeIdentifier;
import gdsmith.cypher.ast.IProperty;
import gdsmith.cypher.ast.analyzer.INodeAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NodeAnalyzer extends NodeIdentifier implements INodeAnalyzer {
    INodeAnalyzer formerDef = null;

    NodeAnalyzer(INodeIdentifier nodeIdentifier) {
        super(nodeIdentifier.getName(), nodeIdentifier.getLabels(), nodeIdentifier.getProperties());
    }

    @Override
    public INodeAnalyzer getFormerDef() {
        return formerDef;
    }

    @Override
    public void setFormerDef(INodeAnalyzer formerDef) {
        this.formerDef = formerDef;
    }

    @Override
    public List<ILabel> getAllLabelsInDefChain() {
        List<ILabel> labels = new ArrayList<>(this.labels);
        if(formerDef != null){
            labels.addAll(formerDef.getLabels());
            labels = labels.stream().distinct().collect(Collectors.toList());
        }
        return labels;
    }

    @Override
    public List<IProperty> getAllPropertiesInDefChain() {
        List<IProperty> properties = new ArrayList<>(this.properties);
        if(formerDef != null){
            properties.addAll(formerDef.getProperties());
            properties = properties.stream().distinct().collect(Collectors.toList());
        }
        return properties;
    }

}
