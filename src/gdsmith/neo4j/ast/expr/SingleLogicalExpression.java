package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.IExpression;

public class SingleLogicalExpression extends Neo4jExpression{

    @Override
    public IExpression getCopy() {
        IExpression child = null;
        if(this.child != null){
            child = this.child.getCopy();
        }
        return new SingleLogicalExpression(child, this.op);
    }

    public enum SingleLogicalOperation{
        SMALLER("IS NULL"),
        EQUAL("IS NOT NULL"),
        SMALLER_OR_EQUAL("NOT");

        SingleLogicalOperation(String textRepresentation){
            this.TextRepresentation = textRepresentation;
        }

        private final String TextRepresentation;

        public String getTextRepresentation(){
            return this.TextRepresentation;
        }
    }

    private final IExpression child;
    private final SingleLogicalOperation op;

    public SingleLogicalExpression(IExpression child, SingleLogicalOperation op){
        this.child = child;
        this.op = op;
    }

    public IExpression getChildExpression(){
        return child;
    }

    public SingleLogicalOperation getOperation(){
        return op;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("(");
        child.toTextRepresentation(sb);
        sb.append(" ").append(op.getTextRepresentation());
        sb.append(")");
    }

}
