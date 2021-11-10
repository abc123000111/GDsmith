package gdsmith.cypher.ast;

import java.util.List;

public interface ICypherSymtab {
    List<IPattern> getPatterns();
    void setPatterns(List<IPattern> patterns);
    List<IRet> getAliasDefinitions();
    void setAliasDefinition(List<IRet> aliasDefinitions);

    List<IAlias> getLocalAliasDefs();
    List<IAlias> getAvailableAliasDefs();
    List<INodeIdentifier> getLocalNodePatterns();
    List<IRelationIdentifier> getLocalRelationPatterns();
    List<INodeIdentifier> getAvailableNodePatterns();
    List<IRelationIdentifier> getAvailableRelationPatterns();

}
