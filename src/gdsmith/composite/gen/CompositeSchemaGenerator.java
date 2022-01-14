package gdsmith.composite.gen;

import gdsmith.Randomly;
import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.cypher.gen.CypherSchemaGenerator;
import gdsmith.cypher.schema.CypherSchema;
import gdsmith.composite.CompositeGlobalState;
import gdsmith.cypher.standard_ast.CypherType;
import gdsmith.cypher.schema.IPatternElementInfo;
import gdsmith.cypher.schema.IPropertyInfo;
import gdsmith.composite.CompositeSchema;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;

public class CompositeSchemaGenerator extends CypherSchemaGenerator<CompositeSchema, CompositeGlobalState> {


    public CompositeSchemaGenerator(CompositeGlobalState globalState){
        super(globalState);
    }

    @Override
    public CompositeSchema generateSchemaObject(CompositeGlobalState globalState, List<CypherSchema.CypherLabelInfo> labels, List<CypherSchema.CypherRelationTypeInfo> relationTypes, List<CypherSchema.CypherPatternInfo> patternInfos) {
        /*for (CypherSchema.CypherLabelInfo label: labels) {
            String createVertex = "CREATE VLABEL ";
            createVertex += label.getName();
            //System.out.println(createVertex);
            try {
                globalState.executeStatement(new CypherQueryAdapter(createVertex));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (CypherSchema.CypherRelationTypeInfo type: relationTypes) {
            String createEdge = "CREATE ELABEL ";
            createEdge += type.getName();
            try {
                globalState.executeStatement(new CypherQueryAdapter(createEdge));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Randomly r = new Randomly();
        int numOfIndexes = r.getInteger(5, 8);

        for (int i = 0; i < numOfIndexes; i++) {
            String createIndex = "CREATE PROPERTY INDEX ON ";
            if (Randomly.getBoolean()) {
                CypherSchema.CypherLabelInfo n = labels.get(r.getInteger(0, labels.size() - 1));
                createIndex = createIndex + n.getName() + " (";
                IPropertyInfo p = n.getProperties().get(r.getInteger(0, n.getProperties().size() - 1));
                createIndex = createIndex + p.getKey() + ")";
            } else {
                CypherSchema.CypherRelationTypeInfo re = relationTypes.get(r.getInteger(0, relationTypes.size() - 1));
                createIndex = createIndex + re.getName() + " (";
                IPropertyInfo p = re.getProperties().get(r.getInteger(0, re.getProperties().size() - 1));
                createIndex = createIndex + p.getKey() + ")";
            }
            //System.out.println(createIndex);
            try {
                globalState.executeStatement(new CypherQueryAdapter(createIndex));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

        return new CompositeSchema(new ArrayList<>(), labels, relationTypes, patternInfos);
    }

}
