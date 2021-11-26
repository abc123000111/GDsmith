package gdsmith.neo4j.gen;

import gdsmith.Randomly;
import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.cypher.ast.*;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.ast.*;
import gdsmith.neo4j.ast.expr.ConstExpression;
import gdsmith.neo4j.dsl.ClauseSequenceBuilder;
import gdsmith.neo4j.schema.IPatternInfo;
import gdsmith.neo4j.schema.IPropertyInfo;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;

public class Neo4jGraphGenerator {
    private static int minNumOfNodes = 10;
    private static int maxNumOfNodes = 100;
    private static List<INodeIdentifier> INodeIdfs;

    private final Neo4jGlobalState globalState;

    // todo(rly): handle Exception
    private ConstExpression generatePropertyValue(Randomly r, Neo4jType type) throws Exception {
        switch (type){
            case INT: return new ConstExpression(r.getInteger());
            case STRING: return new ConstExpression(r.getString());
            case BOOLEAN: return new ConstExpression(r.getInteger(0, 2) == 0);
            default:
                throw new Exception("undefined type in generator!");
        }
    }

    public Neo4jGraphGenerator(Neo4jGlobalState globalState){
        this.globalState = globalState;
    }

    public static List<CypherQueryAdapter> createGraph(Neo4jGlobalState globalState) throws Exception {
        return new Neo4jGraphGenerator(globalState).generateGraph(globalState.getSchema());
    }

    public List<CypherQueryAdapter> generateGraph(Neo4jSchema schema) throws Exception {
        List<CypherQueryAdapter> queries = new ArrayList<>();
        ClauseSequenceBuilder builder = new ClauseSequenceBuilder();

        Randomly r = new Randomly();

        // create nodes
        INodeIdfs = new ArrayList<>();
        int numOfNodes = r.getInteger(maxNumOfNodes, maxNumOfNodes);
        List<Neo4jSchema.Neo4jLabelInfo> labels = schema.getLabels();
        for (int i = 0; i < numOfNodes; ++i) {
            Pattern.PatternBuilder.OngoingNode n = new Pattern.PatternBuilder(builder.getIdentifierBuilder()).newNamedNode();
            for (Neo4jSchema.Neo4jLabelInfo l : labels) {
                if (r.getBooleanWithRatherLowProbability()) { // choose label
                    n = n.withLabels(new Label(l.getName()));
                    for (IPropertyInfo p : l.getProperties()) {
                        if (r.getBooleanWithRatherLowProbability()) { // choose property
                            n = n.withProperties(new Property(p.getKey(), p.getType(), generatePropertyValue(r, p.getType())));
                        }
                    }
                }
            }
            IPattern pattern = n.build();
            INodeIdentifier INodeIdf = (INodeIdentifier) pattern.getPatternElements().get(0);
            INodeIdfs.add(INodeIdf);
            ClauseSequence sequence = new ClauseSequenceBuilder().CreateClause(pattern).ReturnClause(Ret.createStar()).build();
            StringBuilder sb = new StringBuilder();
            sequence.toTextRepresentation(sb);
            queries.add(new CypherQueryAdapter(sb.toString()));
        }


        // create relations
        List<Neo4jSchema.Neo4jRelationTypeInfo> relationTypes = schema.getRelationTypes();
        for (int i = 0; i < numOfNodes; ++i) {
            for (int j = 0; j < numOfNodes; ++j) {
                for (Neo4jSchema.Neo4jRelationTypeInfo relationType : relationTypes) {
                    if (r.getBooleanWithRatherLowProbability()) { // choose this type
                        Pattern.PatternBuilder.OngoingRelation rel = new Pattern.PatternBuilder(builder.getIdentifierBuilder())
                                .newRefDefinedNode(INodeIdfs.get(i))
                                .newNamedRelation().withType(new RelationType(relationType.getName()));

                        for (IPropertyInfo p : relationType.getProperties()) {
                            if (r.getBooleanWithRatherLowProbability()) { // choose this property
                                rel = rel.withProperties(new Property(p.getKey(), p.getType(), generatePropertyValue(r, p.getType())));
                            }
                        }

                        // generate direction
                        Direction dir;
                        int dirChoice = r.getInteger(0, 2); // For CREATE in Neo4j, ALL relationships should be directed.
                        if (dirChoice == 0) {
                            dir = Direction.LEFT;
                        } else {
                            dir = Direction.RIGHT;
                        }

                        rel = rel.withDirection(dir);

                        IPattern pattern = rel.newNodeRef(INodeIdfs.get(j)).build();
                        ClauseSequence sequence = new ClauseSequenceBuilder().CreateClause(pattern).ReturnClause(Ret.createStar()).build();
                        StringBuilder sb = new StringBuilder();
                        sequence.toTextRepresentation(sb);
                        queries.add(new CypherQueryAdapter(sb.toString()));
                    }
                }
            }
        }

        return queries;
    }
}
