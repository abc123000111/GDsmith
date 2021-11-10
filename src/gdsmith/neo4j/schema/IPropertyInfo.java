package gdsmith.neo4j.schema;

import gdsmith.cypher.ast.ICypherType;
import gdsmith.neo4j.ast.Neo4jType;

public interface IPropertyInfo {
    /**
     * 属性名
     * @return
     */
    String getKey();

    /**
     * 属性的类型
     * @return
     */
    Neo4jType getType();

    /**
     * 是否label/type下的每一个node/relation都具有该属性
     * @return
     */
    boolean isOptional();
}
