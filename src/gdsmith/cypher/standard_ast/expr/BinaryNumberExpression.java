package gdsmith.cypher.standard_ast.expr;

import gdsmith.Randomly;
import gdsmith.cypher.ICypherSchema;
import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.analyzer.ICypherTypeDescriptor;
import gdsmith.cypher.ast.analyzer.IIdentifierAnalyzer;
import gdsmith.cypher.standard_ast.CypherType;
import gdsmith.cypher.standard_ast.CypherTypeDescriptor;

import java.util.List;

public class BinaryNumberExpression extends CypherExpression{
    private IExpression left, right;
    private BinaryNumberOperation op;
    
    public BinaryNumberExpression(IExpression left, IExpression right, BinaryNumberOperation op){
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public static IExpression randomBinaryNumber(IExpression left, IExpression right){
        Randomly randomly = new Randomly();
        int operationNum = randomly.getInteger(0, 100);
        if(operationNum < 25){
            return new BinaryNumberExpression(left, right, BinaryNumberOperation.DIVISION);
        }
        if(operationNum < 50){
            return new BinaryNumberExpression(left, right, BinaryNumberOperation.MULTIPLY);
        }
        if(operationNum < 75){
            return new BinaryNumberExpression(left, right, BinaryNumberOperation.MINUS);
        }
        return new BinaryNumberExpression(left, right, BinaryNumberOperation.ADD);
    }

    @Override
    public ICypherTypeDescriptor analyzeType(ICypherSchema schema, List<IIdentifierAnalyzer> identifiers) {
        return new CypherTypeDescriptor(CypherType.NUMBER);
    }

    @Override
    public IExpression getCopy() {
        return new BinaryNumberExpression(left.getCopy(), right.getCopy(), op);
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("(");
        left.toTextRepresentation(sb);
        sb.append(op.getTextRepresentation());
        right.toTextRepresentation(sb);
        sb.append(")");
    }

    public enum BinaryNumberOperation{
        ADD("+"), MINUS("-"), MULTIPLY("*"), DIVISION("/");
        
        BinaryNumberOperation(String textRepresentation){
            this.TextRepresentation = textRepresentation;
        }
        
        private final String TextRepresentation;

        public String getTextRepresentation(){
            return this.TextRepresentation;
        }
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof BinaryNumberExpression)){
            return false;
        }
        return left.equals(((BinaryNumberExpression)o).left) && right.equals(((BinaryNumberExpression)o).right)
                && op == ((BinaryNumberExpression)o).op;
    }
}
