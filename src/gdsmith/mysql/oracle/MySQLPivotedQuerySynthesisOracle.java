package gdsmith.mysql.oracle;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import gdsmith.Randomly;
import gdsmith.SQLConnection;
import gdsmith.common.oracle.PivotedQuerySynthesisBase;
import gdsmith.common.query.Query;
import gdsmith.common.query.SQLQueryAdapter;
import gdsmith.mysql.MySQLErrors;
import gdsmith.mysql.MySQLGlobalState;
import gdsmith.mysql.MySQLSchema.MySQLColumn;
import gdsmith.mysql.MySQLSchema.MySQLRowValue;
import gdsmith.mysql.MySQLSchema.MySQLTable;
import gdsmith.mysql.MySQLSchema.MySQLTables;
import gdsmith.mysql.MySQLVisitor;
import gdsmith.mysql.ast.MySQLColumnReference;
import gdsmith.mysql.ast.MySQLConstant;
import gdsmith.mysql.ast.MySQLExpression;
import gdsmith.mysql.ast.MySQLSelect;
import gdsmith.mysql.ast.MySQLTableReference;
import gdsmith.mysql.ast.MySQLUnaryPostfixOperation;
import gdsmith.mysql.ast.MySQLUnaryPostfixOperation.UnaryPostfixOperator;
import gdsmith.mysql.ast.MySQLUnaryPrefixOperation;
import gdsmith.mysql.ast.MySQLUnaryPrefixOperation.MySQLUnaryPrefixOperator;
import gdsmith.mysql.gen.MySQLExpressionGenerator;

public class MySQLPivotedQuerySynthesisOracle  //todo ?
        extends PivotedQuerySynthesisBase<MySQLGlobalState, MySQLRowValue, MySQLExpression, SQLConnection> {

    private List<MySQLExpression> fetchColumns;
    private List<MySQLColumn> columns;

    public MySQLPivotedQuerySynthesisOracle(MySQLGlobalState globalState) throws SQLException {
        super(globalState);
        MySQLErrors.addExpressionErrors(errors);
        errors.add("in 'order clause'"); // e.g., Unknown column '2067708013' in 'order clause'
    }

    @Override
    public Query<SQLConnection> getRectifiedQuery() throws SQLException {
        MySQLTables randomFromTables = globalState.getSchema().getRandomTableNonEmptyTables();
        List<MySQLTable> tables = randomFromTables.getTables(); //随机选择一些非空tables

        MySQLSelect selectStatement = new MySQLSelect();//select语句
        selectStatement.setSelectType(Randomly.fromOptions(MySQLSelect.SelectType.values()));//select 修饰符
        columns = randomFromTables.getColumns();//随机选择一些列
        pivotRow = randomFromTables.getRandomRowValue(globalState.getConnection());//随机选择一个行，这是通过和数据库的连接实现的，也就是SQLancer里没有存储具体有哪些行

        selectStatement.setFromList(tables.stream().map(t -> new MySQLTableReference(t)).collect(Collectors.toList()));//from

        fetchColumns = columns.stream().map(c -> new MySQLColumnReference(c, null)).collect(Collectors.toList());
        selectStatement.setFetchColumns(fetchColumns);//select ?
        MySQLExpression whereClause = generateRectifiedExpression(columns, pivotRow);
        selectStatement.setWhereClause(whereClause);//where todo ?
        List<MySQLExpression> groupByClause = generateGroupByClause(columns, pivotRow);
        selectStatement.setGroupByExpressions(groupByClause);//group by todo ?
        MySQLExpression limitClause = generateLimit();
        selectStatement.setLimitClause(limitClause);//limit todo ?
        if (limitClause != null) {
            MySQLExpression offsetClause = generateOffset();
            selectStatement.setOffsetClause(offsetClause);
        }
        List<String> modifiers = Randomly.subset("STRAIGHT_JOIN", "SQL_SMALL_RESULT", "SQL_BIG_RESULT", "SQL_NO_CACHE");
        selectStatement.setModifiers(modifiers);//todo ?
        List<MySQLExpression> orderBy = new MySQLExpressionGenerator(globalState).setColumns(columns)
                .generateOrderBys();//todo ?
        selectStatement.setOrderByExpressions(orderBy);

        return new SQLQueryAdapter(MySQLVisitor.asString(selectStatement), errors);
    }

    private List<MySQLExpression> generateGroupByClause(List<MySQLColumn> columns, MySQLRowValue rw) {
        if (Randomly.getBoolean()) {
            return columns.stream().map(c -> MySQLColumnReference.create(c, rw.getValues().get(c)))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private MySQLConstant generateLimit() {
        if (Randomly.getBoolean()) {
            return MySQLConstant.createIntConstant(Integer.MAX_VALUE);
        } else {
            return null;
        }
    }

    private MySQLExpression generateOffset() {
        if (Randomly.getBoolean()) {
            return MySQLConstant.createIntConstantNotAsBoolean(0);
        } else {
            return null;
        }
    }

    private MySQLExpression generateRectifiedExpression(List<MySQLColumn> columns, MySQLRowValue rw) {
        MySQLExpression expression = new MySQLExpressionGenerator(globalState).setRowVal(rw).setColumns(columns)
                .generateExpression();
        MySQLConstant expectedValue = expression.getExpectedValue(); //模拟计算预期的值，todo： 猜测：如果出现了列值引用，就用的是选定的行的对应列值
        MySQLExpression result;
        if (expectedValue.isNull()) {
            result = new MySQLUnaryPostfixOperation(expression, UnaryPostfixOperator.IS_NULL, false);
        } else if (expectedValue.asBooleanNotNull()) {
            result = expression;
        } else {
            result = new MySQLUnaryPrefixOperation(expression, MySQLUnaryPrefixOperator.NOT);
        }
        rectifiedPredicates.add(result);
        return result;
    }

    @Override
    protected Query<SQLConnection> getContainmentCheckQuery(Query<?> query) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ("); // ANOTHER SELECT TO USE ORDER BY without restrictions
        sb.append(query.getUnterminatedQueryString());
        sb.append(") as result WHERE ");
        int i = 0;
        for (MySQLColumn c : columns) {
            if (i++ != 0) {
                sb.append(" AND ");
            }
            sb.append("result.");
            sb.append("ref");
            sb.append(i - 1);
            if (pivotRow.getValues().get(c).isNull()) {
                sb.append(" IS NULL");
            } else {
                sb.append(" = ");
                sb.append(pivotRow.getValues().get(c).getTextRepresentation());
            }
        }

        String resultingQueryString = sb.toString();
        return new SQLQueryAdapter(resultingQueryString, query.getExpectedErrors());
    }

    @Override
    protected String getExpectedValues(MySQLExpression expr) {
        return MySQLVisitor.asExpectedValues(expr);
    }
}
