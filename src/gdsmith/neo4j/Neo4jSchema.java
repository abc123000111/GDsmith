package gdsmith.neo4j;

import gdsmith.common.schema.*;

import java.util.ArrayList;
import java.util.List;

import gdsmith.neo4j.Neo4jSchema.Neo4jTable;

public class Neo4jSchema extends AbstractSchema<Neo4jGlobalState, Neo4jTable> {

    public static Neo4jSchema createEmptyNewSchema(){
        return new Neo4jSchema(new ArrayList<Neo4jTable>());
    }

    //todo complete
    public Neo4jSchema(List<Neo4jTable> databaseTables) {
        super(databaseTables);
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
