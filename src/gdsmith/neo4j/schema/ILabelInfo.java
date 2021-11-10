package gdsmith.neo4j.schema;

import java.util.List;

public interface ILabelInfo extends IPatternElementInfo{
    String getName();
    List<IPropertyInfo> getProperties();
}
