package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Return extends Neo4jClause implements IReturnAnalyzer {

    private List<IRet> returnList = new ArrayList<>();

    private IExpression orderBy = null, skip = null, limit = null;
    private boolean distinct = false;

    public Return(){
        super(false);
    }


    @Override
    public List<IRet> getReturnList() {
        return returnList;
    }

    @Override
    public void setReturnList(List<IRet> returnList) {
        this.returnList = returnList;
    }

    @Override
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean isDistinct() {
        return distinct;
    }

    @Override
    public void setOrderBy(IExpression expression) {
        orderBy = expression;
    }

    @Override
    public IExpression getOrderBy() {
        return orderBy;
    }

    @Override
    public void setLimit(IExpression expression) {
        limit = expression;
    }

    @Override
    public IExpression getLimit() {
        return limit;
    }

    @Override
    public void setSkip(IExpression expression) {
        skip = expression;
    }

    @Override
    public IExpression getSkip() {
        return skip;
    }

    @Override
    public IReturnAnalyzer toAnalyzer() {
        return this;
    }

    @Override
    public ICypherClause getCopy() {
        Return returnClause = new Return();
        returnClause.returnList = returnList.stream().map(ret -> ret.getCopy()).collect(Collectors.toList());
        if(symtab != null){
            if(symtab != null){
                returnClause.symtab.setPatterns(symtab.getPatterns().stream().map(p->p.getCopy()).collect(Collectors.toList()));
                returnClause.symtab.setAliasDefinition(symtab.getAliasDefinitions().stream().map(a->a.getCopy()).collect(Collectors.toList()));
            }
        }
        return returnClause;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("RETURN ");
        if(distinct){
            sb.append("DISTINCT ");
        }
        List<IRet> returnList = getReturnList();
        for(int i = 0; i < returnList.size(); i++){
            returnList.get(i).toTextRepresentation(sb);
            if(i != returnList.size()-1){
                sb.append(", ");
            }
        }
        if(orderBy != null){
            sb.append(" ORDER BY ");
            orderBy.toTextRepresentation(sb);
        }
        if(skip != null){
            sb.append(" SKIP ");
            skip.toTextRepresentation(sb);
        }
        if(limit != null){
            sb.append(" LIMIT ");
            limit.toTextRepresentation(sb);
        }
    }

    @Override
    public List<IPattern> getLocalPatternContainsIdentifier(IIdentifier identifier) {
        return new ArrayList<>();
    }

    @Override
    public IReturn getSource() {
        return this;
    }
}
