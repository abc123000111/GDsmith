package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.IExpression;

public class CallExpression extends Neo4jExpression{
    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("call not supported yet");
    }

    @Override
    public IExpression getCopy() {
        return new CallExpression();
    }
}
