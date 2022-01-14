package gdsmith.cypher;

import gdsmith.SQLancerDBConnection;
import gdsmith.common.query.GDSmithResultSet;

import java.util.List;

public abstract class CypherConnection implements SQLancerDBConnection {

    public void executeStatement(String arg) throws Exception{
        System.out.println("execute statement: "+arg);
    }

    public List<GDSmithResultSet> executeStatementAndGet(String arg) throws Exception{
        System.out.println("execute statement: "+arg);
        return null;
    }
}
