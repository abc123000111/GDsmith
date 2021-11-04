package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.IMatch;
import gdsmith.cypher.ast.IWith;

public interface IConditionGenerator {
    void fillMatchCondtion(IMatch matchClause);
    void fillWithCondition(IWith withClause);
}
