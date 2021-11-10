package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.ICreate;
import gdsmith.cypher.ast.IMatch;
import gdsmith.cypher.ast.IReturn;
import gdsmith.cypher.ast.IWith;
import gdsmith.neo4j.schema.Neo4jSchema;
import gdsmith.neo4j.ast.ClauseSequence;
import gdsmith.neo4j.dsl.QueryFiller.QueryFillerContext;

public class QueryFiller extends ClauseVisitor<QueryFillerContext>{


    public static class QueryFillerContext implements IContext{
        private Neo4jSchema schema;
        private IIdentifierBuilder identifierBuilder;
        private QueryFillerContext(Neo4jSchema schema, IIdentifierBuilder identifierBuilder){
            this.schema = schema;
            this.identifierBuilder = identifierBuilder;
        }
    }

    private IPatternGenerator patternGenerator;
    private IConditionGenerator conditionGenerator;
    private IAliasGenerator aliasGenerator;


    public QueryFiller(ClauseSequence clauseSequence, IPatternGenerator patternGenerator,
                       IConditionGenerator conditionGenerator, IAliasGenerator aliasGenerator,
                       Neo4jSchema schema, IIdentifierBuilder identifierBuilder){
        super(clauseSequence, new QueryFillerContext(schema, identifierBuilder));
        this.patternGenerator = patternGenerator;
        this.conditionGenerator = conditionGenerator;
        this.aliasGenerator = aliasGenerator;
    }

    @Override
    public void visitMatch(IMatch matchClause, QueryFillerContext context) {
        patternGenerator.fillMatchPattern(matchClause);
        conditionGenerator.fillMatchCondtion(matchClause);
    }

    @Override
    public void visitWith(IWith withClause, QueryFillerContext context) {
        aliasGenerator.fillWithAlias(withClause);
        conditionGenerator.fillWithCondition(withClause);
    }

    @Override
    public void visitReturn(IReturn returnClause, QueryFillerContext context) {
        aliasGenerator.fillReturnAlias(returnClause);
    }

    @Override
    public void visitCreate(ICreate createClause, QueryFillerContext context) {

    }
}
