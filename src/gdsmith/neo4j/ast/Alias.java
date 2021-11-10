package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.IAlias;
import gdsmith.cypher.ast.ICypherType;
import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.IRelationIdentifier;
import gdsmith.neo4j.dsl.IIdentifierBuilder;

public class Alias implements IAlias {
    private String name;
    private IExpression expression;
    private IAlias formerDef;

    public static Alias createAliasRef(IAlias alias){
        return new Alias(alias.getName(), null);
    }

    public static Alias createExpressionAlias(IExpression expression, IIdentifierBuilder identifierBuilder){
        return new Alias(identifierBuilder.getNewAliasName(), expression);
    }

    Alias(String name, IExpression expression){
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ICypherType getType() {
        return Neo4jType.UNKNOWN;
    }

    @Override
    public IExpression getExpression() {
        return expression;
    }

    @Override
    public IAlias getFormerDef() {
        return this.formerDef;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        if(expression != null){
            expression.toTextRepresentation(sb);
            sb.append(" AS ");
        }
        sb.append(name);
    }

    void setFormerDef(IAlias formerDef){
        this.formerDef = formerDef;
    }
}
