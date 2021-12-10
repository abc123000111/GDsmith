package gdsmith.neo4j.gen;

import gdsmith.Randomly;
import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.cypher.schema.CypherSchema;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.cypher.standard_ast.CypherType;
import gdsmith.cypher.schema.IPatternElementInfo;
import gdsmith.cypher.schema.IPropertyInfo;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;

public class Neo4jSchemaGenerator {

    private Neo4jGlobalState globalState;

    public Neo4jSchemaGenerator(Neo4jGlobalState globalState){
        this.globalState = globalState;
    }

    public Neo4jSchema generateSchema(){
        Randomly r = new Randomly();
        List<CypherSchema.CypherLabelInfo> labels = new ArrayList<>();
        List<CypherSchema.CypherRelationTypeInfo> relationTypes = new ArrayList<>();
        List<CypherSchema.CypherPatternInfo> patternInfos = new ArrayList<>();

        int numOfLabels = r.getInteger(5,8);
        int numOfRelationTypes = r.getInteger(5, 8);
        int numOfPatternInfos = r.getInteger(5, 8);
        int numOfIndexes = r.getInteger(5, 8);
        int indexOfProperty = 0;

        for (int i = 0; i < numOfLabels; i++) {
            int numOfProperties = r.getInteger(5, 8);
            List<IPropertyInfo> properties = new ArrayList<>();
            for (int j = 0; j < numOfProperties; j++) {
                String key = "k" + indexOfProperty;
                CypherType type = Randomly.fromOptions(CypherType.NUMBER, CypherType.STRING, CypherType.BOOLEAN);
                boolean isOptional = Randomly.getBoolean();
                CypherSchema.CypherPropertyInfo p = new  CypherSchema.CypherPropertyInfo(key, type, isOptional);
                properties.add(p);
                indexOfProperty++;
            }
            String name = "L" + i;
            CypherSchema.CypherLabelInfo t = new CypherSchema.CypherLabelInfo(name, properties);
            labels.add(t);
        }

        for (int i = 0; i < numOfRelationTypes; i++) {
            int numOfProperties = r.getInteger(5, 8);
            List<IPropertyInfo> properties = new ArrayList<>();
            for (int j = 0; j < numOfProperties; j++) {
                String key = "k" + indexOfProperty;
                CypherType type = Randomly.fromOptions(CypherType.NUMBER, CypherType.STRING, CypherType.BOOLEAN);
                boolean isOptional = Randomly.getBoolean();
                CypherSchema.CypherPropertyInfo p = new CypherSchema.CypherPropertyInfo(key, type, isOptional);
                properties.add(p);
                indexOfProperty++;
            }
            String name = "T" + i;
            CypherSchema.CypherRelationTypeInfo re = new CypherSchema.CypherRelationTypeInfo(name, properties);
            relationTypes.add(re);
        }

        for (int i = 0; i < numOfPatternInfos; i++) {
            List<IPatternElementInfo> patternElementInfos = new ArrayList<>();

            int index = r.getInteger(0, numOfLabels - 1);
            CypherSchema.CypherLabelInfo tLeft = labels.get(index);
            index = r.getInteger(0, numOfRelationTypes - 1);
            CypherSchema.CypherRelationTypeInfo re = relationTypes.get(index);
            index = r.getInteger(0, numOfLabels - 1);
            CypherSchema.CypherLabelInfo tRight = labels.get(index);

            patternElementInfos.add(tLeft);
            patternElementInfos.add(re);
            patternElementInfos.add(tRight);

            CypherSchema.CypherPatternInfo pi = new CypherSchema.CypherPatternInfo(patternElementInfos);
            patternInfos.add(pi);
        }

        for (int i = 0; i < numOfIndexes; i++) {
            String createIndex = "CREATE INDEX i" + i;
            createIndex += " IF NOT EXISTS FOR (n:";
            if (Randomly.getBoolean()) {
                CypherSchema.CypherLabelInfo n = labels.get(r.getInteger(0, numOfLabels - 1));
                createIndex = createIndex + n.getName() + ") ON (n.";
                IPropertyInfo p = n.getProperties().get(r.getInteger(0, n.getProperties().size() - 1));
                createIndex = createIndex + p.getKey() + ")";
            } else {
                CypherSchema.CypherRelationTypeInfo re = relationTypes.get(r.getInteger(0, numOfRelationTypes - 1));
                createIndex = createIndex + re.getName() + ") ON (n.";
                IPropertyInfo p = re.getProperties().get(r.getInteger(0, re.getProperties().size() - 1));
                createIndex = createIndex + p.getKey() + ")";
            }
            System.out.println(createIndex);
            try {
                globalState.executeStatement(new CypherQueryAdapter(createIndex));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new Neo4jSchema(new ArrayList<>(), labels, relationTypes, patternInfos);
    }
}
