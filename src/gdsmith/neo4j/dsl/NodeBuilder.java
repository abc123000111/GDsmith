package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.*;
import gdsmith.neo4j.ast.NodeIdentifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NodeBuilder {
    private String curNodeName = "";
    private List<ILabel> curNodeLabels;
    private List<IProperty> curNodeProperties;

    static NodeBuilder newNodeBuilder(String name){
        return new NodeBuilder(name);
    }

    public static NodeBuilder newNodeBuilder(IIdentifierBuilder identifierBuilder){
        return new NodeBuilder(identifierBuilder.getNewNodeName());
    }

    private NodeBuilder(String name){
        curNodeLabels = new ArrayList<>();
        curNodeProperties = new ArrayList<>();
        this.curNodeName = name;
    }


    public NodeBuilder withLabels(ILabel ...labels){
        curNodeLabels.addAll(Arrays.asList(labels));
        curNodeLabels = curNodeLabels.stream().distinct().collect(Collectors.toList());
        return this;
    }

    public NodeBuilder withProperties(IProperty ...properties){
        curNodeProperties.addAll(Arrays.asList(properties));
        curNodeProperties = curNodeProperties.stream().distinct().collect(Collectors.toList());
        return this;
    }


    public INodeIdentifier build(){
        return new NodeIdentifier(curNodeName, curNodeLabels, curNodeProperties);
    }
}
