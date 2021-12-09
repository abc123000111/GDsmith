package gdsmith.cypher.standard_ast.expr;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.IIdentifier;

public class IdentifierExpression extends CypherExpression {

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

    @Override
    public IExpression getCopy() {
        IIdentifier identifier = null;
        if(this.identifier != null){
            identifier = this.identifier.getCopy();
        }
        return new IdentifierExpression(identifier);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof IdentifierExpression)){
            return false;
        }
        return identifier.equals(((IdentifierExpression) o).identifier);
    }
}
