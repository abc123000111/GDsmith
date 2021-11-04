package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.ILabel;

public class Label implements ILabel {
    private String name;

    public Label(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
