package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.ICypherType;
import gdsmith.cypher.ast.analyzer.ICypherTypeAnalyzer;

public class Neo4jBasicTypeAnalyzer implements ICypherTypeAnalyzer {

    private Neo4jType type;

    public Neo4jBasicTypeAnalyzer(Neo4jType type){
        this.type = type;
    }

    @Override
    public Neo4jType getType() {
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
