package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.ICopyable;
import gdsmith.cypher.ast.ICypherType;
import gdsmith.cypher.ast.IExpression;
import gdsmith.neo4j.ast.Neo4jType;

public class ConstExpression extends Neo4jExpression{
    Object value;
    ICypherType type;

    public ConstExpression(Object value){
        this.value = value;
        if(value instanceof Integer || value instanceof Float || value instanceof Long || value instanceof Double){
            type = Neo4jType.NUMBER;
        }
        else if(value instanceof String){
            type = Neo4jType.STRING;
        }
        else if(value instanceof Boolean){
            type = Neo4jType.BOOLEAN;
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
            case NUMBER: sb.append(""+(Integer) value);break;
            case STRING: sb.append("\""+(String) value + "\"");break;
            case BOOLEAN: sb.append(""+(Boolean)value);break;
            case UNKNOWN: sb.append("null");break; //todo not supported
        }
    }

    @Override
    public IExpression getCopy() { //todo 这是个隐患
        if(value instanceof ICopyable){
            return new ConstExpression(((ICopyable) value).getCopy());
        }
        return new ConstExpression(value);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof ConstExpression)){
            return false;
        }
        if(type != ((ConstExpression) o).type){
            return false;
        }
        if(type == Neo4jType.UNKNOWN){
            return false;
        }
        return value.equals(((ConstExpression) o).value);
    }
}
