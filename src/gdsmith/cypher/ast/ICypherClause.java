package gdsmith.cypher.ast;

import java.util.List;

public interface ICypherClause extends ITextRepresentation{
    void setNextClause(ICypherClause next);
    ICypherClause getNextClause();
    void setPrevClause(ICypherClause prev);
    ICypherClause getPrevClause();

    /**
     * 本clause定义的identifier
     * @return
     */
    List<IIdentifier> getLocalIdentifiers();

    /**
     * 本clause定义的node identifier
     * @return
     */
    List<INodeIdentifier> getLocalNodeIdentifiers();

    /**
     * 本clause定义的relation identifier
     * @return
     */
    List<IRelationIdentifier> getLocalRelationIdentifiers();

    /**
     * 本clause的作用域中可以使用的identifier，即where中可以使用的，包括了本地定义的和继承自之前clause的
     * @return
     */
    List<IIdentifier> getAvailableIdentifiers();

    /**
     * 本clause的作用域中可以使用的node identifier，即where中可以使用的，包括了本地定义的和继承自之前clause的
     * @return
     */
    List<INodeIdentifier> getAvailableNodeIdentifiers();

    /**
     * 本clause的作用域中可以使用的relation identifier，即where中可以使用的，包括了本地定义的和继承自之前clause的
     * @return
     */
    List<IRelationIdentifier> getAvailableRelationIdentifiers();

    /**
     * 本clause继承自上一clause的identifier，可以在定义pattern和alias时使用
     * @return
     */
    List<IIdentifier> getExtendableIdentifiers();

    /**
     * 本clause继承自上一clause的node identifier，可以在定义pattern和alias时使用
     * @return
     */
    List<INodeIdentifier> getExtendableNodeIdentifiers();

    /**
     * 本clause继承自上一clause的relation identifier，可以在定义pattern和alias时使用
     * @return
     */
    List<IRelationIdentifier> getExtendablePatternIdentifiers();

}
