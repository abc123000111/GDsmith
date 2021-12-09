package gdsmith.neo4j.schema;

import gdsmith.cypher.ast.ICypherType;

import java.util.List;

public interface ILabelInfo extends IPatternElementInfo{
    String getName();
    List<IPropertyInfo> getProperties();
    boolean hasPropertyWithType(ICypherType type);
    List<IPropertyInfo> getPropertiesWithType(ICypherType type);
}
