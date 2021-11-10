package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.*;
import gdsmith.neo4j.ast.RelationIdentifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RelationBuilder {
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
