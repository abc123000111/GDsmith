package gdsmith.mysql.oracle;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import gdsmith.common.gen.ExpressionGenerator;
import gdsmith.common.oracle.TernaryLogicPartitioningOracleBase;
import gdsmith.common.oracle.TestOracle;
import gdsmith.mysql.MySQLErrors;
import gdsmith.mysql.MySQLGlobalState;
import gdsmith.mysql.MySQLSchema;
import gdsmith.mysql.MySQLSchema.MySQLTable;
import gdsmith.mysql.MySQLSchema.MySQLTables;
import gdsmith.mysql.ast.MySQLColumnReference;
import gdsmith.mysql.ast.MySQLExpression;
import gdsmith.mysql.ast.MySQLSelect;
import gdsmith.mysql.ast.MySQLTableReference;
import gdsmith.mysql.gen.MySQLExpressionGenerator;

public abstract class MySQLQueryPartitioningBase
        extends TernaryLogicPartitioningOracleBase<MySQLExpression, MySQLGlobalState> implements TestOracle {

    MySQLSchema s;
    MySQLTables targetTables;
    MySQLExpressionGenerator gen;
    MySQLSelect select;

    public MySQLQueryPartitioningBase(MySQLGlobalState state) {
        super(state);
        MySQLErrors.addExpressionErrors(errors);
    }

    @Override
    public void check() throws SQLException {
        s = state.getSchema();
        targetTables = s.getRandomTableNonEmptyTables();
        gen = new MySQLExpressionGenerator(state).setColumns(targetTables.getColumns());
        initializeTernaryPredicateVariants();
        select = new MySQLSelect();
        select.setFetchColumns(generateFetchColumns());
        List<MySQLTable> tables = targetTables.getTables();
        List<MySQLExpression> tableList = tables.stream().map(t -> new MySQLTableReference(t))
                .collect(Collectors.toList());
        // List<MySQLExpression> joins = MySQLJoin.getJoins(tableList, state);
        select.setFromList(tableList);
        select.setWhereClause(null);
        // select.setJoins(joins);
    }

    List<MySQLExpression> generateFetchColumns() {
        return Arrays.asList(MySQLColumnReference.create(targetTables.getColumns().get(0), null));
    }

    @Override
    protected ExpressionGenerator<MySQLExpression> getGen() {
        return gen;
    }

}
