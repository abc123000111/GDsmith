package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.IExpression;

public class StringMatchingExpression extends Neo4jExpression{
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

    @Override
    public IExpression getCopy() {
        return null;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("(");
        source.toTextRepresentation(sb);
        sb.append(" ").append(op).append(" ");
        pattern.toTextRepresentation(sb);
        sb.append(")");
    }
}
