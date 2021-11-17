package gdsmith.neo4j.ast;


import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.IClauseAnalyzer;
import gdsmith.cypher.ast.analyzer.INodeAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NodeAnalyzer extends NodeIdentifier implements INodeAnalyzer {
    INodeAnalyzer formerDef = null;
    IClauseAnalyzer clauseAnalyzer;
    INodeIdentifier source;
    IExpression sourceExpression;

    NodeAnalyzer(INodeIdentifier nodeIdentifier, IClauseAnalyzer clauseAnalyzer){
        this(nodeIdentifier, clauseAnalyzer, null);
    }

    NodeAnalyzer(INodeIdentifier nodeIdentifier, IClauseAnalyzer clauseAnalyzer, IExpression sourceExpression) {
        super(nodeIdentifier.getName(), nodeIdentifier.getLabels(), nodeIdentifier.getProperties());
        source = nodeIdentifier;
        this.clauseAnalyzer = clauseAnalyzer;
        this.sourceExpression = sourceExpression;
    }

    @Override
    public INodeIdentifier getSource() {
        return source;
    }

    @Override
    public IExpression getSourceRefExpression() {
        return sourceExpression;
    }

    @Override
    public INodeAnalyzer getFormerDef() {
        return formerDef;
    }

    @Override
    public IClauseAnalyzer getClauseAnalyzer() {
        return clauseAnalyzer;
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
        }
        return properties;
    }


}
