package org.example.gdsmith.tugraph.gen;

import org.example.gdsmith.Randomly;
import org.example.gdsmith.cypher.CypherQueryAdapter;
import org.example.gdsmith.cypher.ast.Direction;
import org.example.gdsmith.cypher.ast.INodeIdentifier;
import org.example.gdsmith.cypher.ast.IPattern;
import org.example.gdsmith.cypher.schema.CypherSchema;
import org.example.gdsmith.cypher.standard_ast.*;
import org.example.gdsmith.tugraph.TuGraphGlobalState;
import org.example.gdsmith.cypher.standard_ast.*;
import org.example.gdsmith.cypher.standard_ast.expr.ConstExpression;
import org.example.gdsmith.cypher.schema.IPropertyInfo;
import org.example.gdsmith.tugraph.schema.TuGraphSchema;

import java.util.ArrayList;
import java.util.List;

public class TuGraphGraphGenerator {
    private static int minNumOfNodes = 5;
    private static int maxNumOfNodes = 10;
    private static double percentOfEdges = 0.01;
    private static List<IPattern> INodesPattern;

    private final TuGraphGlobalState globalState;

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

    public TuGraphGraphGenerator(TuGraphGlobalState globalState){
        this.globalState = globalState;
    }

    public static List<CypherQueryAdapter> createGraph(TuGraphGlobalState globalState) throws Exception {
        return new TuGraphGraphGenerator(globalState).generateGraph(globalState.getSchema());
    }

    public List<CypherQueryAdapter> generateGraph(TuGraphSchema schema) throws Exception {
        List<CypherQueryAdapter> queries = new ArrayList<>();
        IClauseSequenceBuilder builder = ClauseSequence.createClauseSequenceBuilder();

        Randomly r = new Randomly();

        // create nodes
        INodesPattern = new ArrayList<>();
        int numOfNodes = r.getInteger(minNumOfNodes, maxNumOfNodes);
        List<CypherSchema.CypherLabelInfo> labels = schema.getLabels();
        for (int i = 0; i < numOfNodes; ++i) {
            Pattern.PatternBuilder.OngoingNode n = new Pattern.PatternBuilder(builder.getIdentifierBuilder()).newNamedNode();
            /*for (CypherSchema.CypherLabelInfo l : labels) {
                if (r.getBoolean()) { // choose label
                    n = n.withLabels(new Label(l.getName()));
                    for (IPropertyInfo p : l.getProperties()) {
                        if (r.getBoolean()) { // choose property
                            n = n.withProperties(new Property(p.getKey(), p.getType(), generatePropertyValue(r, p.getType())));
                        }
                    }
                }
            }*/
            CypherSchema.CypherLabelInfo l = labels.get(r.getInteger(0, labels.size() - 1)); // choose label
            n = n.withLabels(new Label(l.getName()));
            for (IPropertyInfo p : l.getProperties()) {
                if (r.getBooleanWithRatherLowProbability()) { // choose property
                    n = n.withProperties(new Property(p.getKey(), p.getType(), generatePropertyValue(r, p.getType())));
                }
            }
            n = n.withProperties(new Property("id", CypherType.NUMBER, new ConstExpression(i)));
            IPattern pattern = n.build();
            INodesPattern.add(pattern);
            ClauseSequence sequence = (ClauseSequence) ClauseSequence.createClauseSequenceBuilder().CreateClause(pattern).build();
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
                            if (r.getBoolean()) { // choose this property
                                rel = rel.withProperties(new Property(p.getKey(), p.getType(), generatePropertyValue(r, p.getType())));
                            }
                        }
                        int dirChoice = r.getInteger(0, 2); // generate direction
                        Direction dir = (dirChoice == 0) ? Direction.LEFT : Direction.RIGHT; // For generate in TuGraph, ALL relationships should be directed.
                        rel = rel.withDirection(dir);

                        IPattern merge = rel.newNodeRef(nodeJ).build();
                        ConstExpression n0 = (ConstExpression) nodeI.getProperties().get(nodeI.getProperties().size() - 1).getVal();
                        ConstExpression n1 = (ConstExpression) nodeJ.getProperties().get(nodeJ.getProperties().size() - 1).getVal();
                        StringBuilder str = new StringBuilder();
                        str.append("MATCH ("+nodeI.getName()+"), ("+nodeJ.getName()+") WHERE "+nodeI.getName()+".id = ");
                        StringBuilder n0v = new StringBuilder();
                        StringBuilder n1v = new StringBuilder();
                        n0.toTextRepresentation(n0v);
                        n1.toTextRepresentation(n1v);
                        str.append(n0v);
                        str.append(" AND "+nodeJ.getName()+".id = ");
                        str.append(n1v);

                        ClauseSequence sequence = (ClauseSequence) ClauseSequence.createClauseSequenceBuilder()
                                .CreateClause(merge).build();
                        StringBuilder sb = new StringBuilder();
                        sequence.toTextRepresentation(sb);
                        str.append(" " + sb);
                        queries.add(new CypherQueryAdapter(str.toString()));
                    }
                }
            }
        }
        return queries;
    }
}
