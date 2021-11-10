package gdsmith.cypher.ast;

public interface IIdentifier extends ITextRepresentation{
    String getName();
    ICypherType getType();

}
