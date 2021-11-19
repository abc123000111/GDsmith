package gdsmith.neo4j.gen.random;

import gdsmith.cypher.ast.IRet;
import gdsmith.cypher.ast.analyzer.IReturnAnalyzer;
import gdsmith.cypher.ast.analyzer.IWithAnalyzer;
import gdsmith.neo4j.dsl.BasicAliasGenerator;
import gdsmith.neo4j.dsl.IIdentifierBuilder;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;

public class RandomAliasGenerator extends BasicAliasGenerator {
    public RandomAliasGenerator(Neo4jSchema schema, IIdentifierBuilder identifierBuilder) {
        super(schema, identifierBuilder);
    }

    @Override
    public List<IRet> generateReturnAlias(IReturnAnalyzer returnClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema) {
        List<IRet> results = new ArrayList<>();

        return null;
    }

    @Override
    public List<IRet> generateWithAlias(IWithAnalyzer withClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema) {
        List<IRet> results = new ArrayList<>();

        return null;
    }
}
