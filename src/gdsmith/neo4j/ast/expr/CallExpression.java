package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.IExpression;

public class CallExpression implements IExpression {
    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("call not supported yet");
    }
}