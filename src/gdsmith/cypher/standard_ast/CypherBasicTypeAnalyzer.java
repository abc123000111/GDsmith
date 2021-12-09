package gdsmith.cypher.standard_ast;

import gdsmith.cypher.ast.analyzer.ICypherTypeAnalyzer;

public class CypherBasicTypeAnalyzer implements ICypherTypeAnalyzer {

    private CypherType type;

    public CypherBasicTypeAnalyzer(CypherType type){
        this.type = type;
    }

    @Override
    public CypherType getType() {
        return type;
    }

    @Override
    public boolean isBasicType() {
        return true;
    }

    @Override
    public boolean isNodeOrRelation() {
        return false;
    }
}
