package gdsmith.cypher.gen.random;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.IRet;
import gdsmith.cypher.ast.analyzer.IUnwindAnalyzer;
import gdsmith.cypher.dsl.BasicListGenerator;
import gdsmith.cypher.dsl.IIdentifierBuilder;
import gdsmith.cypher.schema.CypherSchema;
import gdsmith.cypher.standard_ast.CypherType;
import gdsmith.cypher.standard_ast.Ret;

public class RandomListGenerator<S extends CypherSchema<?,?>> extends BasicListGenerator<S> {
    public RandomListGenerator(S schema, IIdentifierBuilder identifierBuilder) {
        super(schema, identifierBuilder);
    }

    @Override
    public IRet generateList(IUnwindAnalyzer unwindAnalyzer, IIdentifierBuilder identifierBuilder, S schema) {
        //todo
        if(unwindAnalyzer.getListAsAliasRet()!=null){
            return unwindAnalyzer.getListAsAliasRet();
        }
        IExpression listExpression = new RandomExpressionGenerator<>(unwindAnalyzer, schema).generateListWithBasicType(2, CypherType.NUMBER);
        return Ret.createNewExpressionAlias(identifierBuilder, listExpression);
    }
}
