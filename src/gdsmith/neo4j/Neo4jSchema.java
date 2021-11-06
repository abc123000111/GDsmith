package gdsmith.neo4j;

import gdsmith.common.schema.*;

import java.util.ArrayList;
import java.util.List;

import gdsmith.cypher.ast.ICypherType;
import gdsmith.cypher.ast.ILabel;
import gdsmith.neo4j.Neo4jSchema.Neo4jTable;
import gdsmith.neo4j.ast.Neo4jType;

public class Neo4jSchema extends AbstractSchema<Neo4jGlobalState, Neo4jTable> {

    private List<Neo4jLabelInfo> labels;
    private List<Neo4jRelationTypeInfo> relationTypes;


    public static Neo4jSchema createEmptyNewSchema(){
        return new Neo4jSchema(new ArrayList<Neo4jTable>());
    }

    //todo complete
    public Neo4jSchema(List<Neo4jTable> databaseTables) {
        super(databaseTables);
        labels = new ArrayList<>();
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
    }

    public List<Neo4jLabelInfo> getLabels(){
        return labels;
    }

    public List<Neo4jRelationTypeInfo> getRelationTypes(){
        return relationTypes;
    }

    public static class Neo4jRelationTypeInfo implements IRelationTypeInfo{
        private String name;
        private List<IPropertyInfo> properties = new ArrayList<>();

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<IPropertyInfo> getProperties() {
            return properties;
        }
    }

    public static class Neo4jLabelInfo implements ILabelInfo{
        private String name;
        private List<IPropertyInfo> properties = new ArrayList<>();

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
        private ICypherType type;

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public ICypherType getType() {
            return type;
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
