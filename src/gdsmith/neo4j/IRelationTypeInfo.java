package gdsmith.neo4j;

import java.util.List;

public interface IRelationTypeInfo {
    String getName();
    List<IPropertyInfo> getProperties();
}
