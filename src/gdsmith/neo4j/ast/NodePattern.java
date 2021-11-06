package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;

import java.util.List;

public class NodePattern implements INodePattern {
    private String name;
    private List<ILabel> labels;
    private List<IProperty> properties;
    private INodePattern formerDef;


    public NodePattern(String name, List<ILabel> labels, List<IProperty> properties){
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

    void setFormerDef(INodePattern formerDef){
        this.formerDef = formerDef;
    }

    @Override
    public INodePattern getFormerDef() {
        return formerDef;
    }

    @Override
    public INodePattern createRef() {
        return new NodePattern(this.name, null, null);
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
        if(!(o instanceof NodePattern)){
            return false;
        }
        if(getName().equals(((NodePattern)o).getName())){
            return true;
        }
        return false;
    }
}
