package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;

import java.util.ArrayList;
import java.util.List;

public class RelationPattern implements IRelationPattern {
    private String name;
    private IType relationType;
    private List<IProperty> properties;
    private Direction direction;

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
}
