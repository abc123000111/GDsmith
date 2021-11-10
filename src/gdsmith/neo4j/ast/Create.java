package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Create implements ICreate {
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
    public List<IAlias> getLocalAliases() {
        return getLocalAliases();
    }

    @Override
    public List<INodeIdentifier> getLocalNodeIdentifiers() {
        return symtab.getLocalNodePatterns();
    }

    @Override
    public List<IRelationIdentifier> getLocalRelationIdentifiers() {
        return symtab.getLocalRelationPatterns();
    }

    @Override
    public List<IAlias> getAvailableAliases() {
        return getAvailableAliases();
    }

    @Override
    public List<INodeIdentifier> getAvailableNodeIdentifiers() {
        return symtab.getAvailableNodePatterns();
    }

    @Override
    public List<IRelationIdentifier> getAvailableRelationIdentifiers() {
        return symtab.getAvailableRelationPatterns();
    }

    @Override
    public List<IAlias> getExtendableAliases() {
        if(prevClause == null)
            return new ArrayList<>();
        return prevClause.getAvailableAliases();
    }

    @Override
    public List<INodeIdentifier> getExtendableNodeIdentifiers() {
        if(prevClause == null)
            return new ArrayList<>();
        return prevClause.getAvailableNodeIdentifiers();
    }

    @Override
    public List<IRelationIdentifier> getExtendablePatternIdentifiers() {
        if(prevClause == null)
            return new ArrayList<>();
        return prevClause.getAvailableRelationIdentifiers();
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("CREATE ");
        List<INodeIdentifier> nodePatterns = new ArrayList<>();
        List<IRelationIdentifier> relationPatterns = new ArrayList<>();
        getPattern().toTextRepresentation(sb);
    }
}
