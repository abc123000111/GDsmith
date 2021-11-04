package gdsmith.cypher.ast;

import java.util.List;

public interface IClauseSequence {
    List<ICypherClause> getClauseList();
}
