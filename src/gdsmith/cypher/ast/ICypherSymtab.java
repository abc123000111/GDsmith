package gdsmith.cypher.ast;

import java.util.List;

public interface ICypherSymtab {
    List<IPatternTuple> getPatterns();
    void deletePattern(IPatternTuple pattern);
    void addPattern(IPatternTuple pattern);

    List<INodePattern> getLocalNodePatterns();
    List<IRelationPattern> getLocalRelationPatterns();
    List<INodePattern> getAvailableNodePatterns();
    List<IRelationPattern> getAvailableRelationPatterns();

    ICypherSymtab getParent();
    void setParent(ICypherSymtab parent);
}
