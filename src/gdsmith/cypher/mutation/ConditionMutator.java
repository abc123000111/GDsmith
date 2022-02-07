package gdsmith.cypher.mutation;

import gdsmith.Randomly;
import gdsmith.cypher.ast.*;
import gdsmith.cypher.dsl.ClauseVisitor;
import gdsmith.cypher.dsl.IContext;
import gdsmith.cypher.dsl.IIdentifierBuilder;
import gdsmith.cypher.schema.CypherSchema;

public class ConditionMutator<S extends CypherSchema<?,?>> extends ClauseVisitor<ConditionMutator.ConditionMutatorContext<S>> {

    Randomly randomly;

    public ConditionMutator(IClauseSequence clauseSequence) {
        super(clauseSequence, new ConditionMutatorContext<>());
        randomly = new Randomly();
    }

    public static class ConditionMutatorContext<S extends CypherSchema<?,?>> implements IContext {
    }

    @Override
    public void visitMatch(IMatch matchClause, ConditionMutatorContext<S> context) {
        if(randomly.getInteger(0, 100) < 20){
            matchClause.setCondition(null);
        }

    }

    public void mutate(){
        startVisit();
    }

    @Override
    public void visitWith(IWith withClause, ConditionMutatorContext<S> context) {
        if(randomly.getInteger() < 20){
            withClause.setCondition(null);
        }
    }

    @Override
    public void visitReturn(IReturn returnClause, ConditionMutatorContext<S> context) {
        return;
    }

    @Override
    public void visitCreate(ICreate createClause, ConditionMutatorContext<S> context) {
        return;
    }

    @Override
    public void visitUnwind(IUnwind unwindClause, ConditionMutatorContext<S> context) {
        return;
    }



}
