package gdsmith.neo4j.ast;


import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.*;

import java.util.ArrayList;
import java.util.List;

public class Match implements IMatchAnalyzer {
    private final Symtab symtab;
    private boolean isOptional = false;
    private IExpression conditon = null;
    private ICypherClause nextClause = null, prevClause = null;

    public Match(){
        symtab = new Symtab(this, true);
    }

    @Override
    public List<IPattern> getPatternTuple() {
        return symtab.getPatterns();
    }

    @Override
    public void setPatternTuple(List<IPattern> patternTuple) {
        //符号表同步更新
        symtab.setPatterns(patternTuple);
    }

    @Override
    public boolean isOptional() {
        return isOptional;
    }

    @Override
    public void setOptional(boolean optional) {
        this.isOptional = optional;
    }

    @Override
    public IExpression getCondition() {
        return conditon;
    }

    @Override
    public void setCondition(IExpression condition) {
        this.conditon = conditon;
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
    public IMatchAnalyzer toAnalyzer() {
        return this;
    }

    @Override
    public List<IAliasAnalyzer> getLocalAliases() {
        return getLocalAliases();
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
        return getAvailableAliases();
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

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        if(isOptional()){
            sb.append("OPTIONAL ");
        }
        sb.append("MATCH ");
        List<IPattern> patterns = getPatternTuple();
        List<INodeIdentifier> nodePatterns = new ArrayList<>();
        List<IRelationIdentifier> relationPatterns = new ArrayList<>();

        for(int i = 0; i < patterns.size(); i++){
            IPattern pattern = patterns.get(i);
            pattern.toTextRepresentation(sb);
            if(i != patterns.size() - 1){
                sb.append(", ");
            }
        }
        if(conditon != null){
            sb.append(" WHERE ");
            conditon.toTextRepresentation(sb);
        }
    }
}
