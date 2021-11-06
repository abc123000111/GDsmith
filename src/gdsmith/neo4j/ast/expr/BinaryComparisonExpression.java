package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.IExpression;

public class BinaryComparisonExpression implements IExpression{



    public enum BinaryComparisonOperation{
        SMALLER("<"),
        EQUAL("="),
        SMALLER_OR_EQUAL("<="),
        HIGHER(">"),
        HIGER_OR_EQUAL(">="),
        NOT_EQUAL("<>");

        BinaryComparisonOperation(String textRepresentation){
            this.TextRepresentation = textRepresentation;
        }

        private final String TextRepresentation;

        public String getTextRepresentation(){
            return this.TextRepresentation;
        }
    }

    private final IExpression left, right;
    private final BinaryComparisonOperation op;

    public BinaryComparisonExpression(IExpression left, IExpression right, BinaryComparisonOperation op){
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public IExpression getLeftExpression(){
        return left;
    }

    public IExpression getRightExpression(){
        return right;
    }

    public BinaryComparisonOperation getOperation(){
        return op;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("(");
        left.toTextRepresentation(sb);
        sb.append(" ").append(op.getTextRepresentation()).append(" ");
        right.toTextRepresentation(sb);
        sb.append(")");
    }
}
