package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.ICypherType;
import gdsmith.cypher.ast.ILabel;
import gdsmith.cypher.ast.INodePattern;
import gdsmith.cypher.ast.IProperty;

import java.util.List;

public class NodePattern implements INodePattern {
    private String name;
    private List<ILabel> labels;
    private List<IProperty> properties;

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
}
