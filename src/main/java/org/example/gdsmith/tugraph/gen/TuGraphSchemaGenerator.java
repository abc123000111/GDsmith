package org.example.gdsmith.tugraph.gen;

import org.example.gdsmith.Randomly;
import org.example.gdsmith.cypher.CypherQueryAdapter;
import org.example.gdsmith.cypher.gen.CypherSchemaGenerator;
import org.example.gdsmith.cypher.schema.CypherSchema;
import org.example.gdsmith.tugraph.TuGraphGlobalState;
import org.example.gdsmith.tugraph.schema.TuGraphSchema;

import java.util.ArrayList;
import java.util.List;

public class TuGraphSchemaGenerator extends CypherSchemaGenerator<TuGraphSchema, TuGraphGlobalState> {


    public TuGraphSchemaGenerator(TuGraphGlobalState globalState){
        super(globalState);
    }

    @Override
    public TuGraphSchema generateSchemaObject(TuGraphGlobalState globalState, List<CypherSchema.CypherLabelInfo> labels, List<CypherSchema.CypherRelationTypeInfo> relationTypes, List<CypherSchema.CypherPatternInfo> patternInfos) {
        Randomly r = new Randomly();
        int numOfIndexes = r.getInteger(5, 8);

        for(CypherSchema.CypherLabelInfo label : labels){
            StringBuilder sb = new StringBuilder();
            sb.append("CALL db.createVertexLabel(");
            sb.append("'"+label.getName()+"',");
            sb.append("'id',");
            sb.append("'id', int32, false");
            label.getProperties().forEach(p->{
                sb.append(",'"+p.getKey()+"'");
                switch (p.getType()){
                    case NUMBER:
                        sb.append(", int64, true");
                        break;
                    case STRING:
                        sb.append(", string, true");
                        break;
                    case BOOLEAN:
                        sb.append(", bool, true");
                        break;
                }
            });
            sb.append(")");
            try {
                globalState.executeStatement(new CypherQueryAdapter(sb.toString()));
            } catch (Exception e) {
                System.out.println(e.toString());
                throw new RuntimeException(e);
            }
        }

        for(CypherSchema.CypherRelationTypeInfo relationType : relationTypes){
            StringBuilder sb = new StringBuilder();
            sb.append("CALL db.createEdgeLabel(");
            sb.append("'"+relationType.getName()+"',");
            sb.append("'[]'");
            relationType.getProperties().forEach(p->{
                sb.append(",'"+p.getKey()+"'");
                switch (p.getType()){
                    case NUMBER:
                        sb.append(", int64, false");
                        break;
                    case STRING:
                        sb.append(", string, false");
                        break;
                    case BOOLEAN:
                        sb.append(", bool, false");
                        break;
                }
            });
            sb.append(")");
            try {
                globalState.executeStatement(new CypherQueryAdapter(sb.toString()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /*for (int i = 0; i < numOfIndexes; i++) {
            String createIndex = "CREATE INDEX i" + i;
            createIndex += " IF NOT EXISTS FOR (n:";
            if (Randomly.getBoolean()) {
                CypherSchema.CypherLabelInfo n = labels.get(r.getInteger(0, labels.size()));
                createIndex = createIndex + n.getName() + ") ON (n.";
                IPropertyInfo p = n.getProperties().get(r.getInteger(0, n.getProperties().size()));
                createIndex = createIndex + p.getKey() + ")";
            } else {
                CypherSchema.CypherRelationTypeInfo re = relationTypes.get(r.getInteger(0, relationTypes.size()));
                createIndex = createIndex + re.getName() + ") ON (n.";
                IPropertyInfo p = re.getProperties().get(r.getInteger(0, re.getProperties().size()));
                createIndex = createIndex + p.getKey() + ")";
            }
            try {
                globalState.executeStatement(new CypherQueryAdapter(createIndex));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        return new TuGraphSchema(new ArrayList<>(), labels, relationTypes, patternInfos);
    }
}
