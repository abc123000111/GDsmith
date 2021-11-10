package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.IRet;
import gdsmith.cypher.ast.IReturn;
import gdsmith.cypher.ast.IWith;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.List;

public abstract class BasicAliasGenerator implements IAliasGenerator{

    protected final Neo4jSchema schema;
    private final IIdentifierBuilder identifierBuilder;

    public BasicAliasGenerator(Neo4jSchema schema, IIdentifierBuilder identifierBuilder){
        this.schema = schema;
        this.identifierBuilder = identifierBuilder;
    }

    @Override
    public void fillReturnAlias(IReturn returnClause) {
        returnClause.setReturnList(generateReturnAlias(returnClause, identifierBuilder, schema));
    }

    @Override
    public void fillWithAlias(IWith withClause) {
        withClause.setReturnList(generateWithAlias(withClause, identifierBuilder, schema));
    }

    public abstract List<IRet> generateReturnAlias(IReturn returnClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema);
    public abstract List<IRet> generateWithAlias(IWith withClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema);
}
