package gdsmith.mysql;

import gdsmith.IgnoreMeException;
import gdsmith.mysql.ast.MySQLBetweenOperation;
import gdsmith.mysql.ast.MySQLBinaryComparisonOperation;
import gdsmith.mysql.ast.MySQLBinaryLogicalOperation;
import gdsmith.mysql.ast.MySQLBinaryOperation;
import gdsmith.mysql.ast.MySQLCastOperation;
import gdsmith.mysql.ast.MySQLCollate;
import gdsmith.mysql.ast.MySQLColumnReference;
import gdsmith.mysql.ast.MySQLComputableFunction;
import gdsmith.mysql.ast.MySQLConstant;
import gdsmith.mysql.ast.MySQLExists;
import gdsmith.mysql.ast.MySQLExpression;
import gdsmith.mysql.ast.MySQLInOperation;
import gdsmith.mysql.ast.MySQLOrderByTerm;
import gdsmith.mysql.ast.MySQLSelect;
import gdsmith.mysql.ast.MySQLStringExpression;
import gdsmith.mysql.ast.MySQLTableReference;
import gdsmith.mysql.ast.MySQLUnaryPostfixOperation;

public class MySQLExpectedValueVisitor implements MySQLVisitor {

    private final StringBuilder sb = new StringBuilder();
    private int nrTabs;

    private void print(MySQLExpression expr) {
        MySQLToStringVisitor v = new MySQLToStringVisitor();
        v.visit(expr);
        for (int i = 0; i < nrTabs; i++) {
            sb.append("\t");
        }
        sb.append(v.get());
        sb.append(" -- ");
        sb.append(expr.getExpectedValue());
        sb.append("\n");
    }

    @Override
    public void visit(MySQLExpression expr) {
        nrTabs++;
        try {
            MySQLVisitor.super.visit(expr);
        } catch (IgnoreMeException e) {

        }
        nrTabs--;
    }

    @Override
    public void visit(MySQLConstant constant) {
        print(constant);
    }

    @Override
    public void visit(MySQLColumnReference column) {
        print(column);
    }

    @Override
    public void visit(MySQLUnaryPostfixOperation op) {
        print(op);
        visit(op.getExpression());
    }

    @Override
    public void visit(MySQLComputableFunction f) {
        print(f);
        for (MySQLExpression expr : f.getArguments()) {
            visit(expr);
        }
    }

    @Override
    public void visit(MySQLBinaryLogicalOperation op) {
        print(op);
        visit(op.getLeft());
        visit(op.getRight());
    }

    public String get() {
        return sb.toString();
    }

    @Override
    public void visit(MySQLSelect select) {
        for (MySQLExpression j : select.getJoinList()) {
            visit(j);
        }
        if (select.getWhereClause() != null) {
            visit(select.getWhereClause());
        }
    }

    @Override
    public void visit(MySQLBinaryComparisonOperation op) {
        print(op);
        visit(op.getLeft());
        visit(op.getRight());
    }

    @Override
    public void visit(MySQLCastOperation op) {
        print(op);
        visit(op.getExpr());
    }

    @Override
    public void visit(MySQLInOperation op) {
        print(op);
        for (MySQLExpression right : op.getListElements()) {
            visit(right);
        }
    }

    @Override
    public void visit(MySQLBinaryOperation op) {
        print(op);
        visit(op.getLeft());
        visit(op.getRight());
    }

    @Override
    public void visit(MySQLOrderByTerm op) {
    }

    @Override
    public void visit(MySQLExists op) {
        print(op);
        visit(op.getExpr());
    }

    @Override
    public void visit(MySQLStringExpression op) {
        print(op);
    }

    @Override
    public void visit(MySQLBetweenOperation op) {
        print(op);
        visit(op.getExpr());
        visit(op.getLeft());
        visit(op.getRight());
    }

    @Override
    public void visit(MySQLTableReference ref) {
    }

    @Override
    public void visit(MySQLCollate collate) {
        print(collate);
        visit(collate.getExpectedValue());
    }

}
