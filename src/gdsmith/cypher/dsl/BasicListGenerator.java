package gdsmith.cypher.dsl;

import gdsmith.cypher.ast.IRet;
import gdsmith.cypher.ast.analyzer.IReturnAnalyzer;
import gdsmith.cypher.ast.analyzer.IUnwindAnalyzer;
import gdsmith.cypher.schema.CypherSchema;

import java.util.List;

public abstract class BasicListGenerator<S extends CypherSchema<?,?>> implements IListGenerator{

    protected final S schema;
    private final IIdentifierBuilder identifierBuilder;

    public BasicListGenerator(S schema, IIdentifierBuilder identifierBuilder){
        this.schema = schema;
        this.identifierBuilder = identifierBuilder;
    }

    @Override
    public void fillUnwindList(IUnwindAnalyzer unwindAnalyzer) {
        unwindAnalyzer.setListAsAliasRet(generateList(unwindAnalyzer, identifierBuilder, schema));
    }

    public abstract IRet generateList(IUnwindAnalyzer unwindAnalyzer, IIdentifierBuilder identifierBuilder, S schema);
}
