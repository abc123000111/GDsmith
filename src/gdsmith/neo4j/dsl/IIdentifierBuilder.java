package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.ICopyable;

public interface IIdentifierBuilder extends ICopyable {
    String getNewNodeName();

    String getNewRelationName();

    String getNewAliasName();

    @Override
    IIdentifierBuilder getCopy();
}
