package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;

import java.util.ArrayList;
import java.util.List;

public class RelationIdentifier implements IRelationIdentifier {
    private String name;
    private IType relationType;
    private List<IProperty> properties;
    private Direction direction;
    private IRelationIdentifier formerDef;

    public RelationIdentifier(String name, IType relationType, Direction direction, List<IProperty> properties){
        this.name = name;
        this.relationType = relationType;
        this.direction = direction;
        this.properties = properties;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ICypherType getType() {
        return Neo4jType.RELATION;
    }

    @Override
    public List<IProperty> getProperties() {
        return properties;
    }

    @Override
    public List<IType> getTypes() {
        List<IType> result = new ArrayList<IType>();
        result.add(relationType);
        return result;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    void setFormerDef(IRelationIdentifier formerDef){
        this.formerDef = formerDef;
    }

    @Override
    public IRelationIdentifier getFormerDef() {
        return formerDef;
    }

    @Override
    public IRelationIdentifier createRef() {
        return new RelationIdentifier(name, null, direction, null);
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        switch (direction){
            case RIGHT:
            case BOTH:
                sb.append("-[");
                break;
            case LEFT:
                sb.append("<-[");
                break;
        }
        if(name != null){
            sb.append(name);
        }
       if(relationType != null){
           sb.append(" :").append(relationType.getName());
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
        switch (direction){
            case LEFT:
            case BOTH:
                sb.append("]-");
                break;
            case RIGHT:
                sb.append("]->");
                break;
        }

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
        if(!(o instanceof RelationIdentifier)){
            return false;
        }
        if(getName().equals(((RelationIdentifier)o).getName())){
            return true;
        }
        return false;
    }
}
