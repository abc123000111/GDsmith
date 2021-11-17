package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;
import gdsmith.neo4j.dsl.IIdentifierBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RelationIdentifier implements IRelationIdentifier {
    protected String name;
    protected IType relationType;
    protected List<IProperty> properties;
    protected Direction direction;

    public static RelationIdentifier createRelationRef(IRelationIdentifier relationIdentifier, Direction direction){
        return new RelationIdentifier(relationIdentifier.getName(), null,
                direction, new ArrayList<>());
    }

    public static RelationIdentifier createNewNamedRelation(IIdentifierBuilder identifierBuilder,
                                                            IType relationType, Direction direction, List<IProperty> properties){
        return new RelationIdentifier(identifierBuilder.getNewRelationName(), relationType,
                direction, properties);
    }

    public static RelationIdentifier createNewAnonymousRelation(IType relationType, Direction direction, List<IProperty> properties){
        return new RelationIdentifier("", relationType, direction, properties);
    }

    RelationIdentifier(String name, IType relationType, Direction direction, List<IProperty> properties){
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

    public static class RelationBuilder {
        private String curRelationName = "";
        private IType curRelationType = null;
        private Direction curDirection = Direction.BOTH;
        private List<IProperty> curRelationProperties;

        static RelationBuilder newRelationBuilder(String name){
            return new RelationBuilder(name);
        }

        public static RelationBuilder newRelationBuilder(IIdentifierBuilder identifierBuilder){
            return new RelationBuilder(identifierBuilder.getNewRelationName());
        }

        private RelationBuilder(String name){
            curRelationName = name;
            curRelationProperties = new ArrayList<>();
        }

        public RelationBuilder withType(IType relationType){
            curRelationType = relationType;
            return this;
        }

        public RelationBuilder withDirection(Direction direction){
            curDirection =direction;
            return this;
        }

        public RelationBuilder withProperties(IProperty ...properties){
            curRelationProperties.addAll(Arrays.asList(properties));
            curRelationProperties = curRelationProperties.stream().distinct().collect(Collectors.toList());
            return this;
        }

        public IRelationIdentifier build(){
            return new RelationIdentifier(curRelationName, curRelationType, curDirection, curRelationProperties);
        }
    }
}
