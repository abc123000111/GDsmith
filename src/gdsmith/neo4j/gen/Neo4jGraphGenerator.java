package gdsmith.neo4j.gen;

import gdsmith.Randomly;
import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.cypher.ast.Direction;
import gdsmith.cypher.ast.INodeIdentifier;
import gdsmith.cypher.ast.IPattern;
import gdsmith.cypher.schema.CypherSchema;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.cypher.standard_ast.*;
import gdsmith.cypher.standard_ast.expr.ConstExpression;
import gdsmith.cypher.schema.IPropertyInfo;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;

public class Neo4jGraphGenerator {
    private static int minNumOfNodes = 10;
    private static int maxNumOfNodes = 50;
    private static double percentOfEdges = 0.0001;
    private static List<IPattern> INodesPattern;

    private final Neo4jGlobalState globalState;

    // todo(rly): handle Exception
    private ConstExpression generatePropertyValue(Randomly r, CypherType type) throws Exception {
        switch (type){
            case NUMBER: return new ConstExpression(r.getInteger());
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
        IClauseSequenceBuilder builder = ClauseSequence.createClauseSequenceBuilder();

        Randomly r = new Randomly();

        // create nodes
        INodesPattern = new ArrayList<>();
        int numOfNodes = r.getInteger(minNumOfNodes, maxNumOfNodes);
        List<CypherSchema.CypherLabelInfo> labels = schema.getLabels();
        for (int i = 0; i < numOfNodes; ++i) {
            Pattern.PatternBuilder.OngoingNode n = new Pattern.PatternBuilder(builder.getIdentifierBuilder()).newNamedNode();
            for (CypherSchema.CypherLabelInfo l : labels) {
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
            INodesPattern.add(pattern);
            ClauseSequence sequence = (ClauseSequence) ClauseSequence.createClauseSequenceBuilder().CreateClause(pattern).ReturnClause(Ret.createStar()).build();
            StringBuilder sb = new StringBuilder();
            sequence.toTextRepresentation(sb);
            queries.add(new CypherQueryAdapter(sb.toString()));
        }



        // create relations
        List<CypherSchema.CypherRelationTypeInfo> relationTypes = schema.getRelationTypes();
        for (int i = 0; i < numOfNodes; ++i) {
            for (int j = 0; j < numOfNodes; ++j) {
                for (CypherSchema.CypherRelationTypeInfo relationType : relationTypes) {
                    if (r.getInteger(0, 1000000) < percentOfEdges * 1000000) { // choose this type
                        IPattern patternI = INodesPattern.get(i);
                        IPattern patternJ = INodesPattern.get(j);
                        INodeIdentifier nodeI = (INodeIdentifier) patternI.getPatternElements().get(0);
                        INodeIdentifier nodeJ = (INodeIdentifier) patternJ.getPatternElements().get(0);

                        Pattern.PatternBuilder.OngoingRelation rel = new Pattern.PatternBuilder(builder.getIdentifierBuilder())
                                .newRefDefinedNode(nodeI)
                                .newNamedRelation().withType(new RelationType(relationType.getName()));

                        for (IPropertyInfo p : relationType.getProperties()) {
                            if (r.getBooleanWithRatherLowProbability()) { // choose this property
                                rel = rel.withProperties(new Property(p.getKey(), p.getType(), generatePropertyValue(r, p.getType())));
                            }
                        }

                        int dirChoice = r.getInteger(0, 2); // generate direction
                        Direction dir = (dirChoice == 0) ? Direction.LEFT : Direction.RIGHT; // For generate in Neo4j, ALL relationships should be directed.
                        rel = rel.withDirection(dir);

                        IPattern merge = rel.newNodeRef(nodeJ).build();

                        ClauseSequence sequence = (ClauseSequence) ClauseSequence.createClauseSequenceBuilder()
                                .MatchClause(null, patternI, patternJ).MergeClause(merge).ReturnClause(Ret.createStar()).build();
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
