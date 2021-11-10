package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;

import java.util.ArrayList;
import java.util.List;

public class Return implements IReturn {

    private List<IRet> returnList = new ArrayList<>();
    private ICypherClause nextClause = null, prevClause = null;

    private Symtab symtab = null;

    public Return(){
        symtab = new Symtab(this,  false);
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
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("RETURN ");
        List<IRet> returnList = getReturnList();
        for(int i = 0; i < returnList.size(); i++){
            returnList.get(i).toTextRepresentation(sb);
            if(i != returnList.size()-1){
                sb.append(", ");
            }
        }
    }

    @Override
    public List<IAlias> getLocalAliases() {
        return symtab.getLocalAliasDefs();
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
        return symtab.getAvailableAliasDefs();
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
}
