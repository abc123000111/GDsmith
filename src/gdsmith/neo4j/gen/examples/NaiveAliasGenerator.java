package gdsmith.neo4j.gen.examples;

import gdsmith.cypher.ast.IRet;
import gdsmith.cypher.ast.analyzer.IReturnAnalyzer;
import gdsmith.cypher.ast.analyzer.IWithAnalyzer;
import gdsmith.neo4j.schema.Neo4jSchema;
import gdsmith.cypher.standard_ast.Ret;
import gdsmith.cypher.dsl.BasicAliasGenerator;
import gdsmith.cypher.dsl.IIdentifierBuilder;

import java.util.ArrayList;
import java.util.List;

public class NaiveAliasGenerator extends BasicAliasGenerator {

    public NaiveAliasGenerator(Neo4jSchema schema, IIdentifierBuilder identifierBuilder) {
        super(schema, identifierBuilder);
    }

    @Override
    public List<IRet> generateReturnAlias(IReturnAnalyzer returnClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema) {
        //return *
        List<IRet> results = new ArrayList<>();
        Ret result = Ret.createStar();
        results.add(result);
        return results;
    }

    @Override
    public List<IRet> generateWithAlias(IWithAnalyzer withClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema) {
        //return *
        List<IRet> results = new ArrayList<>();
        Ret result = Ret.createStar();
        results.add(result);
        return results;
    }
}
