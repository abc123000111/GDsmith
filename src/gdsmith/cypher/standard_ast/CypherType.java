package gdsmith.cypher.standard_ast;

import gdsmith.Randomly;
import gdsmith.cypher.ast.ICypherType;

public enum CypherType implements ICypherType {
    NUMBER, BOOLEAN, STRING, NODE, RELATION, UNKNOWN, LIST, MAP;

    public static CypherType getRandomBasicType(){
        Randomly randomly = new Randomly();
        if(randomly.getInteger(0, 100) < 50){
            return NUMBER;
        }
        return STRING;
    }
}
