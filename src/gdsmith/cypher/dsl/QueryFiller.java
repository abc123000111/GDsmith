package gdsmith.cypher.dsl;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.schema.CypherSchema;
import gdsmith.neo4j.schema.Neo4jSchema;
import gdsmith.cypher.standard_ast.ClauseSequence;
import gdsmith.cypher.dsl.QueryFiller.QueryFillerContext;

public class QueryFiller<S extends CypherSchema<?,?>> extends ClauseVisitor<QueryFillerContext<S>>{


    public static class QueryFillerContext<S extends CypherSchema<?,?>> implements IContext{
        private S schema;
        private IIdentifierBuilder identifierBuilder;
        private QueryFillerContext(S schema, IIdentifierBuilder identifierBuilder){
            this.schema = schema;
            this.identifierBuilder = identifierBuilder;
        }
    }

    private IPatternGenerator patternGenerator;
    private IConditionGenerator conditionGenerator;
    private IAliasGenerator aliasGenerator;


    public QueryFiller(IClauseSequence clauseSequence, IPatternGenerator patternGenerator,
                       IConditionGenerator conditionGenerator, IAliasGenerator aliasGenerator,
                       S schema, IIdentifierBuilder identifierBuilder){
        super(clauseSequence, new QueryFillerContext<>(schema, identifierBuilder));
        this.patternGenerator = patternGenerator;
        this.conditionGenerator = conditionGenerator;
        this.aliasGenerator = aliasGenerator;
    }

    @Override
    public void visitMatch(IMatch matchClause, QueryFillerContext<S> context) {
        patternGenerator.fillMatchPattern(matchClause.toAnalyzer());
        conditionGenerator.fillMatchCondtion(matchClause.toAnalyzer());
    }

    @Override
    public void visitWith(IWith withClause, QueryFillerContext<S> context) {
        aliasGenerator.fillWithAlias(withClause.toAnalyzer());
        conditionGenerator.fillWithCondition(withClause.toAnalyzer());
    }

    @Override
    public void visitReturn(IReturn returnClause, QueryFillerContext<S> context) {
        aliasGenerator.fillReturnAlias(returnClause.toAnalyzer());
    }

    @Override
    public void visitCreate(ICreate createClause, QueryFillerContext<S> context) {

    }
}
