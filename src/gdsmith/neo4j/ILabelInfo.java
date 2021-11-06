package gdsmith.neo4j;

import java.util.List;

public interface ILabelInfo {
    String getName();
    List<IPropertyInfo> getProperties();
}
