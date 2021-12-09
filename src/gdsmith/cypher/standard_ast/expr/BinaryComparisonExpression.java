package gdsmith.neo4j.ast.expr;

import gdsmith.Randomly;
import gdsmith.cypher.ast.ICypherType;
import gdsmith.cypher.ast.IExpression;

public class BinaryComparisonExpression extends Neo4jExpression{

    @Override
    public IExpression getCopy() {
        IExpression left = null, right = null;
        if(this.left != null){
            left = this.left.getCopy();
        }
        if(this.right != null){
            right = this.right.getCopy();
        }
        return new BinaryComparisonExpression(left, right, this.op);
    }

    public static BinaryComparisonExpression randomComparison(IExpression left, IExpression right){
        Randomly randomly = new Randomly();
        int operationNum = randomly.getInteger(0, 100);
        if(operationNum < 5){
            return new BinaryComparisonExpression(left, right, BinaryComparisonOperation.EQUAL);
        }
        if(operationNum < 20){
            return new BinaryComparisonExpression(left, right, BinaryComparisonOperation.NOT_EQUAL);
        }
        if(operationNum < 40){
            return new BinaryComparisonExpression(left, right, BinaryComparisonOperation.HIGHER);
        }
        if(operationNum < 60){
            return new BinaryComparisonExpression(left, right, BinaryComparisonOperation.HIGER_OR_EQUAL);
        }
        if(operationNum < 80){
            return new BinaryComparisonExpression(left, right, BinaryComparisonOperation.SMALLER);
        }
        return new BinaryComparisonExpression(left, right, BinaryComparisonOperation.SMALLER_OR_EQUAL);
    }

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
        left.setParentExpression(this);
        right.setParentExpression(this);
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

    @Override
    public boolean equals(Object o){
        if(!(o instanceof BinaryComparisonExpression)){
            return false;
        }
        return left.equals(((BinaryComparisonExpression)o).left) && right.equals(((BinaryComparisonExpression)o).right)
                && op == ((BinaryComparisonExpression)o).op;
    }
}
