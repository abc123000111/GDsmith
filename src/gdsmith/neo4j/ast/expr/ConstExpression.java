package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.ICypherType;
import gdsmith.cypher.ast.IExpression;
import gdsmith.neo4j.ast.Neo4jType;

public class ConstExpression extends Neo4jExpression{
    Object value;
    ICypherType type;

    public ConstExpression(Object value){
        this.value = value;
        if(value instanceof Integer){
            type = Neo4jType.INT;
        }
        else if(value instanceof String){
            type = Neo4jType.STRING;
        }
        else {
            type = Neo4jType.UNKNOWN;
        }
    }

    public ICypherType getType(){
        return type;
    }

    public Object getValue(){
        return value;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        switch ((Neo4jType)type){
            case INT: sb.append(""+(Integer) value);break;
            case STRING: sb.append("\""+(String) value + "\"");break;
            case UNKNOWN: sb.append("null");break; //todo not supported
        }
    }
}
