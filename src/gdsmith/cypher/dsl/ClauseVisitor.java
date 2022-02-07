package gdsmith.cypher.dsl;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.standard_ast.ClauseSequence;

import java.util.List;

public abstract class ClauseVisitor<C extends IContext> {

    private IClauseSequence clauseSequence;
    private C context;
    private boolean continueVisit = true;
    private int presentIndex = 0;

    public ClauseVisitor(IClauseSequence clauseSequence, C context){
        this.clauseSequence = clauseSequence;
    }

    public void startVisit(){
        if(this.clauseSequence.getClauseList() == null || this.clauseSequence.getClauseList().size() == 0){
            return;
        }
        List<ICypherClause> clauses = this.clauseSequence.getClauseList();
        for(int i = 0; i < clauses.size(); i++){
            presentIndex = i;
            visitClause(clauses.get(i));
            if(!continueVisit){
                return;
            }
        }
    }

    public void reverseVisit(){
        if(this.clauseSequence.getClauseList() == null || this.clauseSequence.getClauseList().size() == 0){
            return;
        }
        List<ICypherClause> clauses = this.clauseSequence.getClauseList();
        for(int i = clauses.size() - 1; i >= 0; i--){
            presentIndex = i;
            visitClause(clauses.get(i));
            if(!continueVisit){
                return;
            }
        }
    }

    public IClauseSequence getClauseSequence(){
        return clauseSequence;
    }

    protected int getPresentIndex(){
        return presentIndex;
    }

    protected void stopVisit(){
        continueVisit = false;
    }

    public void visitClause(ICypherClause clause){
        if(clause instanceof IMatch){
            visitMatch((IMatch) clause, context);
        }
        else if(clause instanceof IWith){
            visitWith((IWith) clause, context);
        }
        else if(clause instanceof ICreate){
            visitCreate((ICreate) clause, context);
        }
        else if(clause instanceof IReturn){
            visitReturn((IReturn) clause, context);
        }
        else if(clause instanceof IUnwind){
            visitUnwind((IUnwind) clause, context);
        }
    }

    public abstract void visitMatch(IMatch matchClause, C context);
    public abstract void visitWith(IWith withClause, C context);
    public abstract void visitReturn(IReturn returnClause, C context);
    public abstract void visitCreate(ICreate createClause, C context);
    public abstract void visitUnwind(IUnwind unwindClause, C context);

}
