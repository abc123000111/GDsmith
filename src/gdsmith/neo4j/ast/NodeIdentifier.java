package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.INodeAnalyzer;
import gdsmith.neo4j.dsl.IIdentifierBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NodeIdentifier implements INodeIdentifier {
    protected String name;
    protected List<ILabel> labels;
    protected List<IProperty> properties;


    public static NodeIdentifier createNodeRef(INodeIdentifier nodeIdentifier){
        return new NodeIdentifier(nodeIdentifier.getName(), new ArrayList<>(), new ArrayList<>());
    }

    public static NodeIdentifier createNewNamedNode(IIdentifierBuilder identifierBuilder, List<ILabel> labels, List<IProperty> properties){
        return new NodeIdentifier(identifierBuilder.getNewNodeName(), labels, properties);
    }

    public static NodeIdentifier createNewAnonymousNode(List<ILabel> labels, List<IProperty> properties){
        return new NodeIdentifier("", labels, properties);
    }

    NodeIdentifier(String name, List<ILabel> labels, List<IProperty> properties){
        this.name = name;
        this.labels = labels;
        this.properties = properties;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public ICypherType getType() {
        return Neo4jType.NODE;
    }

    @Override
    public List<IProperty> getProperties() {
        return properties;
    }

    @Override
    public List<ILabel> getLabels() {
        return labels;
    }


    @Override
    public INodeIdentifier createRef() {
        return new NodeIdentifier(this.name, null, null);
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("(");
        if(name != null){
            sb.append(name);
        }
        if(labels != null){
            for(ILabel label: labels){
                sb.append(" :").append(label.getName());
            }
        }
        if(properties != null && properties.size()!=0){
            sb.append("{");
            for(int i = 0; i < properties.size(); i++){
                properties.get(i).toTextRepresentation(sb);
                if(i != properties.size() - 1){
                    sb.append(", ");
                }
            }
            sb.append("}");
        }
        sb.append(")");
    }

    @Override
    public boolean isAnonymous() {
        return getName() == null || getName().length() == 0;
    }

    @Override
    public int hashCode(){
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof NodeIdentifier)){
            return false;
        }
        if(getName().equals(((NodeIdentifier)o).getName())){
            return true;
        }
        return false;
    }

    public static class NodeBuilder {
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
}
