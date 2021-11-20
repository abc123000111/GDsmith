package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class With extends Neo4jClause implements IWithAnalyzer {

    private boolean distinct = false;
    private IExpression condition = null;

    public With(){
        super(false);
    }


    @Override
    public boolean isDistinct() {
        return distinct;
    }

    @Override
    public void setDistinct(boolean isDistinct) {
        this.distinct = isDistinct;
    }

    @Override
    public List<IRet> getReturnList() {
        return symtab.getAliasDefinitions();
    }

    @Override
    public void setReturnList(List<IRet> returnList) {
        this.symtab.setAliasDefinition(returnList);
    }

    @Override
    public IExpression getCondtion() {
        return condition;
    }

    @Override
    public void setCondition(IExpression condtion) {
        this.condition = condtion;
    }

    @Override
    public IWithAnalyzer toAnalyzer() {
        return this;
    }

    @Override
    public ICypherClause getCopy() {
        With with = new With();
        with.distinct = distinct;
        if(condition != null){
            with.condition = condition.getCopy();
        }
        else {
            with.condition = null;
        }
        if(symtab != null){
            with.symtab.setPatterns(symtab.getPatterns().stream().map(p->p.getCopy()).collect(Collectors.toList()));
            with.symtab.setAliasDefinition(symtab.getAliasDefinitions().stream().map(a->a.getCopy()).collect(Collectors.toList()));
        }
        return with;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        //todo distinct
        sb.append("WITH ");
        List<IRet> returnList = getReturnList();
        for(int i = 0; i < returnList.size(); i++){
            returnList.get(i).toTextRepresentation(sb);
            if(i != returnList.size()-1){
                sb.append(", ");
            }
        }
        if(condition != null){
            sb.append(" WHERE");
            condition.toTextRepresentation(sb);
        }
    }

    @Override
    public List<IPattern> getLocalPatternContainsIdentifier(IIdentifier identifier) {
        return new ArrayList<>();
    }

    @Override
    public IWith getSource() {
        return this;
    }
}
