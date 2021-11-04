package gdsmith.neo4j.ast;


import gdsmith.cypher.ast.*;

public class Match implements IMatch {
    private final ICypherSymtab symtab;
    private IPatternTuple patternTuple = null;
    private boolean isOptional = false;
    private IExpression conditon;
    private ICypherClause nextClause = null, prevClause = null;

    public Match(){
        symtab = new Symtab(true);
    }

    @Override
    public IPatternTuple getPatternTuple() {
        return patternTuple;
    }

    @Override
    public void setPatternTuple(IPatternTuple patternTuple) {
        //符号表同步更新
        symtab.deletePattern(this.patternTuple);
        symtab.addPattern(patternTuple);
        this.patternTuple = patternTuple;
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
}
