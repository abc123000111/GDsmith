package gdsmith.neo4j.schema;

import gdsmith.neo4j.schema.IPropertyInfo;

import java.util.List;

public interface IRelationTypeInfo extends IPatternElementInfo{
    String getName();
    List<IPropertyInfo> getProperties();
}