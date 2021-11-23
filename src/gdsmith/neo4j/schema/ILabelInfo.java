package gdsmith.neo4j.schema;

import gdsmith.cypher.ast.ICypherType;
import gdsmith.neo4j.ast.Neo4jType;

import java.util.List;

public interface ILabelInfo extends IPatternElementInfo{
    String getName();
    List<IPropertyInfo> getProperties();
    boolean hasPropertyWithType(ICypherType type);
    List<IPropertyInfo> getPropertiesWithType(ICypherType type);
}
