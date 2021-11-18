package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.ICypherClause;
import gdsmith.cypher.ast.IExpression;

public abstract class Neo4jExpression implements IExpression {
    protected IExpression parentExpression;
    protected ICypherClause parentClause;

    @Override
    public IExpression getParentExpression() {
        return parentExpression;
    }

    @Override
    public void setParentExpression(IExpression parentExpression){
        this.parentExpression = parentExpression;
    }

    @Override
    public ICypherClause getExpressionRootClause() {
        if(parentExpression != null){
            return parentExpression.getExpressionRootClause();
        }
        return parentClause;
    }

    @Override
    public void setParentClause(ICypherClause parentClause){
        this.parentClause = parentClause;
    }

}
