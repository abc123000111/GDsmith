package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Create implements ICreateAnalyzer {
    private final Symtab symtab;
    private ICypherClause nextClause = null, prevClause = null;

    public Create(){
        symtab = new Symtab(this, true);
    }

    @Override
    public IPattern getPattern() {
        return symtab.getPatterns().get(0);
    }

    @Override
    public void setPattern(IPattern pattern) {
        //符号表同步更新
        symtab.setPatterns(Arrays.asList(pattern));
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
    public ICreateAnalyzer toAnalyzer() {
        return this;
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

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("CREATE ");
        List<INodeIdentifier> nodePatterns = new ArrayList<>();
        List<IRelationIdentifier> relationPatterns = new ArrayList<>();
        getPattern().toTextRepresentation(sb);
    }
}
