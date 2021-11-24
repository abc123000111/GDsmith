package gdsmith.neo4j.gen;

import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.cypher.ast.IPattern;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.ast.*;
import gdsmith.neo4j.ast.expr.ConstExpression;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;

public class Neo4jGraphGenerator {
    private final Neo4jGlobalState globalState;
    public Neo4jGraphGenerator(Neo4jGlobalState globalState){
        this.globalState = globalState;
    }

    public static List<CypherQueryAdapter> createGraph(Neo4jGlobalState globalState){
        return new Neo4jGraphGenerator(globalState).generateGraph(globalState.getSchema());
    }

    public List<CypherQueryAdapter> generateGraph(Neo4jSchema schema){
        //todo complete
        List<CypherQueryAdapter> queries = new ArrayList<>();
        queries.add(new CypherQueryAdapter("CREATE (m)"));
        queries.add(new CypherQueryAdapter("CREATE (n)"));

        ClauseSequence.ClauseSequenceBuilder builder = new ClauseSequence.ClauseSequenceBuilder();
        IPattern pattern = new Pattern.PatternBuilder(builder.getIdentifierBuilder()).newNamedNode()
                .withLabels(new Label("Person"))
                .withProperties(new Property("name", Neo4jType.STRING, new ConstExpression("Frank"))).build();

        ClauseSequence sequence = new ClauseSequence.ClauseSequenceBuilder().CreateClause(pattern).ReturnClause(Ret.createStar()).build();

        StringBuilder sb = new StringBuilder();
        sequence.toTextRepresentation(sb);
        queries.add(new CypherQueryAdapter(sb.toString()));

        return queries;
    }
}
