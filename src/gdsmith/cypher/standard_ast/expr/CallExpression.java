package gdsmith.cypher.standard_ast.expr;

import gdsmith.cypher.ast.IExpression;
import gdsmith.neo4j.schema.IFunctionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CallExpression extends CypherExpression {
    private String functionName;
    private String functionSignature;
    List<IExpression> params;

    public CallExpression(IFunctionInfo functionInfo, List<IExpression> params){
        this.functionName = functionInfo.getName();
        this.functionSignature = functionInfo.getSignature();
        this.params = params;
        params.forEach(e->e.setParentExpression(this));
    }

    public CallExpression(String functionName, String functionSignature, List<IExpression> params){
        this.functionName = functionName;
        this.functionSignature = functionSignature;
        this.params = params;
        params.forEach(e->e.setParentExpression(this));
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append(functionName).append("(");
        params.forEach(e->{e.toTextRepresentation(sb); sb.append(", ");});
        sb.delete(sb.length()-2, sb.length()); //多余的", "
        sb.append(")");
    }

    @Override
    public IExpression getCopy() {
        if(params == null){
            return new CallExpression(this.functionName, this.functionSignature, new ArrayList<>());
        }
        return new CallExpression(this.functionName, this.functionSignature,
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