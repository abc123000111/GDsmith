package gdsmith.neo4j.schema;

import gdsmith.common.schema.*;

import java.util.ArrayList;
import java.util.List;

import gdsmith.cypher.ast.ICypherType;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.schema.Neo4jSchema.Neo4jTable;
import gdsmith.neo4j.ast.Neo4jType;

public class Neo4jSchema extends AbstractSchema<Neo4jGlobalState, Neo4jTable> {

    private List<Neo4jLabelInfo> labels; //所有的Label信息
    private List<Neo4jRelationTypeInfo> relationTypes; //所有的relationship type信息
    private List<Neo4jPatternInfo> patternInfos; //存在的pattern结构


    private static Neo4jSchema schema;

    public static Neo4jSchema createEmptyNewSchema(){
        return new Neo4jSchema(new ArrayList<Neo4jTable>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    //todo complete
    public Neo4jSchema(List<Neo4jTable> databaseTables, List<Neo4jLabelInfo> labels,
                       List<Neo4jRelationTypeInfo> relationTypes, List<Neo4jPatternInfo> patternInfos) {
        super(databaseTables);
        /**
         * 这里面是乱写的，应该让SchemaGenerator来生成
         */
        /*labels = new ArrayList<>();
        relationTypes = new ArrayList<>();

        Neo4jLabelInfo tl1 = new Neo4jLabelInfo(), tl2 = new Neo4jLabelInfo();
        Neo4jRelationTypeInfo r1 = new Neo4jRelationTypeInfo(), r2 = new Neo4jRelationTypeInfo();
        Neo4jPropertyInfo p1 = new Neo4jPropertyInfo(), p2 = new Neo4jPropertyInfo(), p3 = new Neo4jPropertyInfo();

        p1.key = "name";
        p2.key = "integer";
        p3.key = "string";
        p1.type = Neo4jType.STRING;
        p2.type = Neo4jType.INT;
        p3.type = Neo4jType.STRING;

        tl1.name = "L1";
        tl1.properties.add(p1);
        tl1.properties.add(p2);
        tl1.properties.add(p3);
        tl2.name = "L2";
        tl2.properties.add(p1);
        tl2.properties.add(p2);
        tl2.properties.add(p3);
        r1.name = "R1";
        r1.properties.add(p1);
        r1.properties.add(p2);
        r1.properties.add(p3);
        r2.name = "R2";
        r1.properties.add(p1);
        r1.properties.add(p2);
        r1.properties.add(p3);

        labels.add(tl1);
        labels.add(tl2);
        relationTypes.add(r1);
        relationTypes.add(r2);
         */
        this.labels = labels;
        this.relationTypes = relationTypes;
        this.patternInfos = patternInfos;
    }

    public List<Neo4jLabelInfo> getLabels(){
        return labels;
    }

    public List<Neo4jRelationTypeInfo> getRelationTypes(){
        return relationTypes;
    }

    public static class Neo4jPatternInfo implements IPatternInfo{

        private List<IPatternElementInfo> patternElementInfos = new ArrayList<>();

        public Neo4jPatternInfo(List<IPatternElementInfo> patternElementInfos) {
            this.patternElementInfos = patternElementInfos;
        }

        @Override
        public List<IPatternElementInfo> getPattern() {
            return patternElementInfos;
        }
    }

    public static class Neo4jRelationTypeInfo implements IRelationTypeInfo {
        private String name;
        private List<IPropertyInfo> properties = new ArrayList<>();

        public Neo4jRelationTypeInfo(String name, List<IPropertyInfo> properties) {
            this.name = name;
            this.properties = properties;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<IPropertyInfo> getProperties() {
            return properties;
        }
    }

    public static class Neo4jLabelInfo implements ILabelInfo {
        private String name;
        private List<IPropertyInfo> properties = new ArrayList<>();

        public Neo4jLabelInfo(String name, List<IPropertyInfo> properties) {
            this.name = name;
            this.properties = properties;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<IPropertyInfo> getProperties() {
            return properties;
        }
    }

    public static class Neo4jPropertyInfo implements IPropertyInfo{
        private String key;
        private Neo4jType type;
        private boolean isOptional;

        public Neo4jPropertyInfo(String key, Neo4jType type, boolean isOptional) {
            this.key = key;
            this.type = type;
            this.isOptional = isOptional;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Neo4jType getType() {
            return type;
        }

        @Override
        public boolean isOptional() {
            return isOptional;
        }
    }




    public enum Neo4jDataType{

    }
    public static class Neo4jTable extends AbstractTable<Neo4jTableColumn, TableIndex, Neo4jGlobalState> {

        //todo complete
        public Neo4jTable(String name, List<Neo4jTableColumn> columns, List<TableIndex> indexes, boolean isView) {
            super(name, columns, indexes, isView);
        }

        @Override
        public long getNrRows(Neo4jGlobalState globalState) {
            return 0;
        }
    }

    public static class Neo4jTableColumn extends AbstractTableColumn<Neo4jTable, Neo4jDataType>{
        //todo complete
        public Neo4jTableColumn(String name, Neo4jTable table, Neo4jDataType type) {
            super(name, table, type);
        }
    }
}
