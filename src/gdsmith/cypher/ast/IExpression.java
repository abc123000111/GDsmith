package gdsmith.cypher.ast;

public interface IExpression extends ITextRepresentation, ICopyable{
    IExpression getParentExpression();
    void setParentExpression(IExpression parentExpression);
    ICypherClause getExpressionRootClause();
    void setParentClause(ICypherClause parentClause);

    @Override
    IExpression getCopy();

}
