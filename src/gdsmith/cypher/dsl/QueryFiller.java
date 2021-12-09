package gdsmith.cypher.dsl;

import gdsmith.cypher.ast.ICreate;
import gdsmith.cypher.ast.IMatch;
import gdsmith.cypher.ast.IReturn;
import gdsmith.cypher.ast.IWith;
import gdsmith.neo4j.schema.Neo4jSchema;
import gdsmith.cypher.standard_ast.ClauseSequence;
import gdsmith.cypher.dsl.QueryFiller.QueryFillerContext;

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
        patternGenerator.fillMatchPattern(matchClause.toAnalyzer());
        conditionGenerator.fillMatchCondtion(matchClause.toAnalyzer());
    }

    @Override
    public void visitWith(IWith withClause, QueryFillerContext context) {
        aliasGenerator.fillWithAlias(withClause.toAnalyzer());
        conditionGenerator.fillWithCondition(withClause.toAnalyzer());
    }

    @Override
    public void visitReturn(IReturn returnClause, QueryFillerContext context) {
        aliasGenerator.fillReturnAlias(returnClause.toAnalyzer());
    }

    @Override
    public void visitCreate(ICreate createClause, QueryFillerContext context) {

    }
}
