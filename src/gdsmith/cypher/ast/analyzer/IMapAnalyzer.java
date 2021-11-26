package gdsmith.cypher.ast.analyzer;

import java.util.List;
import java.util.Map;

public interface IMapAnalyzer extends ICypherTypeAnalyzer{
    boolean isMapSizeUnknown();
    Map<String, ICypherTypeAnalyzer> getMapMemberTypes();
    boolean isMembersWithSameType();
    ICypherTypeAnalyzer getSameMemberType();
}
