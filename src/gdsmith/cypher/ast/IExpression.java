package gdsmith.cypher.ast;

public interface IExpression extends ITextRepresentation{
    IExpression getParentExpression();
    void setParentExpression(IExpression parentExpression);
    ICypherClause getExpressionRootClause();
    void setParentClause(ICypherClause parentClause);

}
