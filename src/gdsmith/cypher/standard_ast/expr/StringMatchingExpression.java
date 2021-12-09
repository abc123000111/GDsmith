package gdsmith.cypher.standard_ast.expr;

import gdsmith.Randomly;
import gdsmith.cypher.ast.IExpression;

public class StringMatchingExpression extends CypherExpression {
    private IExpression source, pattern;
    private StringMatchingOperation op;

    public StringMatchingExpression(IExpression source, IExpression pattern, StringMatchingOperation op){
        this.source = source;
        this.pattern = pattern;
        this.op = op;
        source.setParentExpression(this);
        pattern.setParentExpression(this);
    }

    public enum StringMatchingOperation{
        CONTAINS("CONTAINS"),
        STARTS_WITH("STARTS WITH"),
        ENDS_WITH("ENDS WITH");

        StringMatchingOperation(String textRepresentation){
            this.TextRepresentation = textRepresentation;
        }

        private final String TextRepresentation;

        public String getTextRepresentation(){
            return this.TextRepresentation;
        }
    }

    public static StringMatchingExpression randomMatching(IExpression left, IExpression right){
        Randomly randomly = new Randomly();
        int operationNum = randomly.getInteger(0, 90);
        if(operationNum < 30){
            return new  StringMatchingExpression(left, right, StringMatchingOperation.CONTAINS);
        }
        if(operationNum < 60){
            return new  StringMatchingExpression(left, right, StringMatchingOperation.ENDS_WITH);
        }
        return new  StringMatchingExpression(left, right, StringMatchingOperation.STARTS_WITH);
    }

    @Override
    public IExpression getCopy() {
        return null;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("(");
        source.toTextRepresentation(sb);
        sb.append(" ").append(op.getTextRepresentation()).append(" ");
        pattern.toTextRepresentation(sb);
        sb.append(")");
    }
}
