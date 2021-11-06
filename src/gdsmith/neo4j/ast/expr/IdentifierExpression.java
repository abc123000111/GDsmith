package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.IIdentifier;

public class IdentifierExpression implements IExpression {

    private final IIdentifier identifier;

    public IdentifierExpression(IIdentifier identifier){
        this.identifier = identifier;
    }

    public IIdentifier getIdentifier(){
        return identifier;
    }


    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append(identifier.getName());
    }
}
