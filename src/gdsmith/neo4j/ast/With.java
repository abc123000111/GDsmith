package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.*;

import java.util.ArrayList;
import java.util.List;

public class With implements IWithAnalyzer {

    private boolean distinct = false;
    private IExpression condition = null;
    private ICypherClause nextClause = null, prevClause = null;

    private Symtab symtab = null;

    public With(){
        symtab = new Symtab(this,  false);
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
    public void setNextClause(ICypherClause next) {
        this.nextClause = next;
        if(next != null) {
            next.setPrevClause(this);
        }
    }

    @Override
    public ICypherClause getNextClause() {
        return nextClause;
    }

    @Override
    public void setPrevClause(ICypherClause prev) {
        this.prevClause = prev;
    }

    @Override
    public ICypherClause getPrevClause() {
        return this.prevClause;
    }

    @Override
    public IWithAnalyzer toAnalyzer() {
        return this;
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
    public List<IAliasAnalyzer> getLocalAliases() {
        return symtab.getLocalAliasDefs();
    }

    @Override
    public List<INodeAnalyzer> getLocalNodeIdentifiers() {
        return symtab.getLocalNodePatterns();
    }

    @Override
    public List<IRelationAnalyzer> getLocalRelationIdentifiers() {
        return symtab.getLocalRelationPatterns();
    }

    @Override
    public List<IAliasAnalyzer> getAvailableAliases() {
        return symtab.getAvailableAliasDefs();
    }

    @Override
    public List<INodeAnalyzer> getAvailableNodeIdentifiers() {
        return symtab.getAvailableNodePatterns();
    }

    @Override
    public List<IRelationAnalyzer> getAvailableRelationIdentifiers() {
        return symtab.getAvailableRelationPatterns();
    }

    @Override
    public List<IAliasAnalyzer> getExtendableAliases() {
        if(prevClause == null)
            return new ArrayList<>();
        return prevClause.toAnalyzer().getAvailableAliases();
    }

    @Override
    public List<INodeAnalyzer> getExtendableNodeIdentifiers() {
        if(prevClause == null)
            return new ArrayList<>();
        return prevClause.toAnalyzer().getAvailableNodeIdentifiers();
    }

    @Override
    public List<IRelationAnalyzer> getExtendablePatternIdentifiers() {
        if(prevClause == null)
            return new ArrayList<>();
        return prevClause.toAnalyzer().getAvailableRelationIdentifiers();
    }
}
