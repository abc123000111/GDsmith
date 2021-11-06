package gdsmith.cypher.ast;

import java.util.List;

public interface IClauseSequence extends ITextRepresentation {
    List<ICypherClause> getClauseList();
}
