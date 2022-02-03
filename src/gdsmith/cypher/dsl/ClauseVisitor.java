package gdsmith.cypher.dsl;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.standard_ast.ClauseSequence;

public abstract class ClauseVisitor<C extends IContext> {

    private IClauseSequence clauseSequence;
    private C context;

    public ClauseVisitor(IClauseSequence clauseSequence, C context){
        this.clauseSequence = clauseSequence;
    }

    public void startVisit(){
        if(this.clauseSequence.getClauseList() == null || this.clauseSequence.getClauseList().size() == 0){
            return;
        }
        visitClause(this.clauseSequence.getClauseList().get(0));
    }

    public void visitClause(ICypherClause clause){
        if(clause instanceof IMatch){
            visitMatch((IMatch) clause, context);
            if(clause.getNextClause() != null){
                visitClause(clause.getNextClause());
            }
        }
        else if(clause instanceof IWith){
            visitWith((IWith) clause, context);
            if(clause.getNextClause() != null){
                visitClause(clause.getNextClause());
            }
        }
        else if(clause instanceof ICreate){
            visitCreate((ICreate) clause, context);
            if(clause.getNextClause() != null){
                visitClause(clause.getNextClause());
            }
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
