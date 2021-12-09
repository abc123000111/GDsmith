package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.List;

public abstract class BasicPatternGenerator implements IPatternGenerator{

    protected final Neo4jSchema schema;
    private final IIdentifierBuilder identifierBuilder;

    public BasicPatternGenerator(Neo4jSchema schema, IIdentifierBuilder identifierBuilder){
        this.schema = schema;
        this.identifierBuilder = identifierBuilder;
    }


    @Override
    public void fillMatchPattern(IMatchAnalyzer matchClause) {
        matchClause.setPatternTuple(generatePattern(matchClause, identifierBuilder, schema));
    }

    public abstract List<IPattern> generatePattern(IMatchAnalyzer matchClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema);
}
