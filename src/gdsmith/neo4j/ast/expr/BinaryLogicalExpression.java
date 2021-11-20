package gdsmith.neo4j.ast.expr;

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
        SMALLER("OR"),
        EQUAL("AND"),
        SMALLER_OR_EQUAL("XOR");

        BinaryLogicalOperation(String textRepresentation){
            this.TextRepresentation = textRepresentation;
        }

        private final String TextRepresentation;

        public String getTextRepresentation(){
            return this.TextRepresentation;
        }
    }

    private final IExpression left, right;
    private final BinaryLogicalOperation op;

    public BinaryLogicalExpression(IExpression left, IExpression right, BinaryLogicalOperation op){
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
}
