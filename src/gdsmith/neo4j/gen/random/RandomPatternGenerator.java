package gdsmith.neo4j.gen.random;

import gdsmith.cypher.ast.IPattern;
import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;
import gdsmith.neo4j.dsl.BasicPatternGenerator;
import gdsmith.neo4j.dsl.IIdentifierBuilder;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.List;

public class RandomPatternGenerator extends BasicPatternGenerator {

    public RandomPatternGenerator(Neo4jSchema schema, IIdentifierBuilder identifierBuilder) {
        super(schema, identifierBuilder);
    }

    @Override
    public List<IPattern> generatePattern(IMatchAnalyzer matchClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema) {
        return null;
    }
}
