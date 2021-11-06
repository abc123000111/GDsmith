package gdsmith.cypher.ast;

import java.util.List;

public interface ICypherSymtab {
    List<IPattern> getPatterns();
    void setPatterns(List<IPattern> patterns);
    List<IRet> getAliasDefinitions();
    void setAliasDefinition(List<IRet> aliasDefinitions);

    List<INodePattern> getLocalNodePatterns();
    List<IRelationPattern> getLocalRelationPatterns();
    List<INodePattern> getAvailableNodePatterns();
    List<IRelationPattern> getAvailableRelationPatterns();

}
