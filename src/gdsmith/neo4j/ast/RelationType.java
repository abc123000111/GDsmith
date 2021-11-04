package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.IType;

public class RelationType implements IType {

    private String name;

    public RelationType(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
