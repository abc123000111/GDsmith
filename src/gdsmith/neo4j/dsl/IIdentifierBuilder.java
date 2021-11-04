package gdsmith.neo4j.dsl;

public interface IIdentifierBuilder {
    String getNewNodeName();

    String getNewRelationName();

    String getNewAliasName();
}
