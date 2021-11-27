package gdsmith.neo4j.ast.expr;

import gdsmith.Randomly;
import gdsmith.cypher.ast.IExpression;

public class BinaryLogicalExpression extends Neo4jExpression{

    @Override
    public IExpression getCopy() {
        IExpression left = null, right = null;
        if(this.left != null){
            left = this.left.getCopy();
        }
        if(this.right != null){
            right = this.right.getCopy();
        }
        return new BinaryLogicalExpression(left, right, this.op);
    }

    public enum BinaryLogicalOperation{
        OR("OR"),
        AND("AND"),
        XOR("XOR");

        BinaryLogicalOperation(String textRepresentation){
            this.TextRepresentation = textRepresentation;
        }

        private final String TextRepresentation;

        public String getTextRepresentation(){
            return this.TextRepresentation;
        }
    }

    public static BinaryLogicalExpression randomLogical(IExpression left, IExpression right){
        Randomly randomly = new Randomly();
        int operationNum = randomly.getInteger(0, 90);
        if(operationNum < 30){
            return new BinaryLogicalExpression(left, right, BinaryLogicalOperation.AND);
        }
        if(operationNum < 60){
            return new BinaryLogicalExpression(left, right, BinaryLogicalOperation.OR);
        }
        return new BinaryLogicalExpression(left, right, BinaryLogicalOperation.XOR);
    }

    private final IExpression left, right;
    private final BinaryLogicalOperation op;

    public BinaryLogicalExpression(IExpression left, IExpression right, BinaryLogicalOperation op){
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

    public BinaryLogicalOperation getOperation(){
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
        if(!(o instanceof BinaryLogicalExpression)){
            return false;
        }
        return left.equals(((BinaryLogicalExpression)o).left) && right.equals(((BinaryLogicalExpression)o).right)
                && op == ((BinaryLogicalExpression)o).op;
    }
}
