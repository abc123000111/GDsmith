package gdsmith.mysql.ast;

import gdsmith.mysql.MySQLSchema.MySQLTable;

public class MySQLTableReference implements MySQLExpression {

    private final MySQLTable table;

    public MySQLTableReference(MySQLTable table) {
        this.table = table;
    }

    public MySQLTable getTable() {
        return table;
    }

}
