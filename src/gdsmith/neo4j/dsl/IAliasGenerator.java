package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.IReturn;
import gdsmith.cypher.ast.IWith;

public interface IAliasGenerator {
    void fillReturnAlias(IReturn returnClause);
    void fillWithAlias(IWith withClause);
}
