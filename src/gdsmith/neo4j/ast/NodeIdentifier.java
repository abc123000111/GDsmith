package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;

import java.util.List;

public class NodeIdentifier implements INodeIdentifier {
    private String name;
    private List<ILabel> labels;
    private List<IProperty> properties;
    private INodeIdentifier formerDef;


    public NodeIdentifier(String name, List<ILabel> labels, List<IProperty> properties){
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

    void setFormerDef(INodeIdentifier formerDef){
        this.formerDef = formerDef;
    }

    @Override
    public INodeIdentifier getFormerDef() {
        return formerDef;
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
}
