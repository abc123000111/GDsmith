package gdsmith.cypher.ast.analyzer;

import java.util.List;

public interface IListAnalyzer extends ICypherTypeAnalyzer{
    boolean isListLengthUnknown();
    List<ICypherTypeAnalyzer> getListMemberTypes();
    boolean isMembersWithSameType();
    ICypherTypeAnalyzer getSameMemberType();
}
