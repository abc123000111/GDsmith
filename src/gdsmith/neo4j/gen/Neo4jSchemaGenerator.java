package gdsmith.neo4j.gen;

import gdsmith.Randomly;
import gdsmith.neo4j.ast.Neo4jType;
import gdsmith.neo4j.schema.IPatternElementInfo;
import gdsmith.neo4j.schema.IPropertyInfo;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;

public class Neo4jSchemaGenerator {

    public Neo4jSchema generateSchema(){
        Randomly r = new Randomly();
        List<Neo4jSchema.Neo4jLabelInfo> labels = new ArrayList<>();
        List<Neo4jSchema.Neo4jRelationTypeInfo> relationTypes = new ArrayList<>();
        List<Neo4jSchema.Neo4jPatternInfo> patternInfos = new ArrayList<>();

        int numOfLabels = r.getInteger(2,5);
        int numOfRelationTypes = r.getInteger(2, 5);
        int numOfPatternInfos = r.getInteger(2, 5);

        for (int i = 0; i < numOfLabels; i++) {
            int numOfProperties = r.getInteger(2, 5);
            List<IPropertyInfo> properties = new ArrayList<>();
            for (int j = 0; j < numOfProperties; j++) {
                String key = "k" + j;
                Neo4jType type = Randomly.fromOptions(Neo4jType.INT, Neo4jType.STRING);
                boolean isOptional = Randomly.getBoolean();
                Neo4jSchema.Neo4jPropertyInfo p = new Neo4jSchema.Neo4jPropertyInfo(key, type, isOptional);
                properties.add(p);
            }
            String name = "l" + i;
            Neo4jSchema.Neo4jLabelInfo t = new Neo4jSchema.Neo4jLabelInfo(name, properties);
            labels.add(t);
        }

        for (int i = 0; i < numOfRelationTypes; i++) {
            int numOfProperties = r.getInteger(2, 5);
            List<IPropertyInfo> properties = new ArrayList<>();
            for (int j = 0; j < numOfProperties; j++) {
                String key = "k" + j;
                Neo4jType type = Randomly.fromOptions(Neo4jType.INT, Neo4jType.STRING);
                boolean isOptional = Randomly.getBoolean();
                Neo4jSchema.Neo4jPropertyInfo p = new Neo4jSchema.Neo4jPropertyInfo(key, type, isOptional);
                properties.add(p);
            }
            String name = "t" + i;
            Neo4jSchema.Neo4jRelationTypeInfo re = new Neo4jSchema.Neo4jRelationTypeInfo(name, properties);
            relationTypes.add(re);
        }

        for (int i = 0; i < numOfPatternInfos; i++) {
            List<IPatternElementInfo> patternElementInfos = new ArrayList<>();

            int index = r.getInteger(0, numOfLabels - 1);
            Neo4jSchema.Neo4jLabelInfo tLeft = labels.get(index);
            index = r.getInteger(0, numOfRelationTypes - 1);
            Neo4jSchema.Neo4jRelationTypeInfo re = relationTypes.get(index);
            index = r.getInteger(0, numOfLabels - 1);
            Neo4jSchema.Neo4jLabelInfo tRight = labels.get(index);

            patternElementInfos.add(tLeft);
            patternElementInfos.add(re);
            patternElementInfos.add(tRight);

            Neo4jSchema.Neo4jPatternInfo pi = new Neo4jSchema.Neo4jPatternInfo(patternElementInfos);
            patternInfos.add(pi);
        }

        return new Neo4jSchema(new ArrayList<>(), labels, relationTypes, patternInfos);
    }
}
