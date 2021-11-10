package gdsmith.cypher.ast;

public interface IAlias extends IIdentifier{
    IExpression getExpression();
    IAlias getFormerDef();
}
