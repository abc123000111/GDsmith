package gdsmith.cypher.ast.analyzer;

import gdsmith.cypher.ast.IAlias;
import gdsmith.cypher.ast.INodeIdentifier;
import gdsmith.cypher.ast.IRelationIdentifier;

import java.util.List;

public interface IClauseAnalyzer {

    /**
     * 本clause定义的别名
     * @return
     */
    List<IAliasAnalyzer> getLocalAliases();

    /**
     * 本clause定义的node identifier
     * @return
     */
    List<INodeAnalyzer> getLocalNodeIdentifiers();

    /**
     * 本clause定义的relation identifier
     * @return
     */
    List<IRelationAnalyzer> getLocalRelationIdentifiers();

    /**
     * 本clause的作用域中可以使用的别名，即where中可以使用的，包括了本地定义的和继承自之前clause的
     * @return
     */
    List<IAliasAnalyzer> getAvailableAliases();

    /**
     * 本clause的作用域中可以使用的node identifier，即where中可以使用的，包括了本地定义的和继承自之前clause的
     * @return
     */
    List<INodeAnalyzer> getAvailableNodeIdentifiers();

    /**
     * 本clause的作用域中可以使用的relation identifier，即where中可以使用的，包括了本地定义的和继承自之前clause的
     * @return
     */
    List<IRelationAnalyzer> getAvailableRelationIdentifiers();

    /**
     * 本clause继承自上一clause的别名，可以在定义pattern和alias时使用
     * @return
     */
    List<IAliasAnalyzer> getExtendableAliases();

    /**
     * 本clause继承自上一clause的node identifier，可以在定义pattern和alias时使用
     * @return
     */
    List<INodeAnalyzer> getExtendableNodeIdentifiers();

    /**
     * 本clause继承自上一clause的relation identifier，可以在定义pattern和alias时使用
     * @return
     */
    List<IRelationAnalyzer> getExtendablePatternIdentifiers();
}
