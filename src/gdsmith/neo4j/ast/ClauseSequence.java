package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.IClauseSequence;
import gdsmith.cypher.ast.ICypherClause;

import java.util.ArrayList;
import java.util.List;

public class ClauseSequence implements IClauseSequence {

    List<ICypherClause> clauses = new ArrayList<>();

    @Override
    public List<ICypherClause> getClauseList() {
        return clauses;
    }

    public void addClause(ICypherClause clause){
        if(clauses.size() != 0 ) {
            clauses.get(clauses.size() - 1).setNextClause(clause);
        }
        clauses.add(clause);
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        for(int i = 0; i < clauses.size(); i ++){
            clauses.get(i).toTextRepresentation(sb);
            if(i != clauses.size() - 1){
                sb.append(" ");
            }
        }
    }
}