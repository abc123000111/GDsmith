package gdsmith.common.ast;

import gdsmith.common.ast.BinaryOperatorNode.Operator;

public abstract class BinaryOperatorNode<T, O extends Operator> extends BinaryNode<T> {

    private final O op;

    public interface Operator {
        String getTextRepresentation();
    }

    public BinaryOperatorNode(T left, T right, O op) {
        super(left, right);
        this.op = op;
    }

    @Override
    public String getOperatorRepresentation() {
        return op.getTextRepresentation();
    }

    public O getOp() {
        return op;
    }

}
