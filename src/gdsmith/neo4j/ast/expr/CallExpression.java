package gdsmith.neo4j.ast.expr;

import gdsmith.cypher.ast.IExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CallExpression extends Neo4jExpression{
    private String functionName;
    List<IExpression> params;

    public CallExpression(String functionName, List<IExpression> params){
        this.functionName = functionName;
        this.params = params;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("call not supported yet");
    }

    @Override
    public IExpression getCopy() {
        if(params == null){
            return new CallExpression(this.functionName, new ArrayList<>());
        }
        return new CallExpression(this.functionName,
                this.params.stream().map(p->p.getCopy()).collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof CallExpression)){
            return false;
        }
        if(((CallExpression) o).params == null){
            ((CallExpression) o).params = new ArrayList<>();
        }
        if(params == null){
            params = new ArrayList<>();
        }
        if(params.size() != ((CallExpression) o).params.size()){
            return false;
        }
        return ((CallExpression) o).params.containsAll(params);
    }
}
