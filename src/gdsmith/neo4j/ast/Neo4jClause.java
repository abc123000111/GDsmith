package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.ICypherClause;
import gdsmith.cypher.ast.IIdentifier;
import gdsmith.cypher.ast.IPattern;
import gdsmith.cypher.ast.analyzer.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Neo4jClause implements IClauseAnalyzer {
    protected final Symtab symtab;
    protected ICypherClause nextClause = null, prevClause = null;

    public Neo4jClause(boolean extendParent){
        symtab = new Symtab(this, extendParent);
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

    protected List<IIdentifierAnalyzer> getAvailableIdentifiers(){
        List<IIdentifierAnalyzer> identifierAnalyzers = new ArrayList<>();
        identifierAnalyzers.addAll(getAvailableNodeIdentifiers());
        identifierAnalyzers.addAll(getAvailableRelationIdentifiers());
        identifierAnalyzers.addAll(getAvailableAliases());
        return identifierAnalyzers;
    }

    @Override
    public IIdentifierAnalyzer getIdentifierAnalyzer(String name){
        List<IIdentifierAnalyzer> identifierAnalyzers = getAvailableIdentifiers();
        for(IIdentifierAnalyzer identifierAnalyzer: identifierAnalyzers){
            if(identifierAnalyzer.getName().equals(name)){
                return identifierAnalyzer;
            }
        }
        return null;
    }

    @Override
    public IIdentifierAnalyzer getIdentifierAnalyzer(IIdentifier identifier){
        return getIdentifierAnalyzer(identifier.getName());
    }

}
