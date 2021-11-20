package gdsmith.cypher.ast;

import java.util.List;

public interface IClauseSequence extends ITextRepresentation, ICopyable {
    List<ICypherClause> getClauseList();

    @Override
    IClauseSequence getCopy();

}
