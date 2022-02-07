package gdsmith.cypher.ast;

import java.util.List;

public interface IClauseSequence extends ITextRepresentation, ICopyable {
    List<ICypherClause> getClauseList();
    void setClauseList(List<ICypherClause> clauses);

    @Override
    IClauseSequence getCopy();

}
