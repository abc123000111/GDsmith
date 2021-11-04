package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.ICypherClause;
import gdsmith.cypher.ast.IRet;
import gdsmith.cypher.ast.IReturn;

import java.util.List;

public class Return implements IReturn {

    private List<IRet> returnList;
    private ICypherClause nextClause = null, prevClause = null;


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
}
