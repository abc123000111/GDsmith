package gdsmith.composite;

import gdsmith.common.query.GDSmithResultSet;
import gdsmith.cypher.CypherConnection;

import java.util.ArrayList;
import java.util.List;

public class CompositeConnection extends CypherConnection {

    private List<CypherConnection> connections = new ArrayList<>();

    public CompositeConnection(List<CypherConnection> connections){
        this.connections = connections;
    }

    @Override
    public String getDatabaseVersion() throws Exception {
        return "";
    }

    @Override
    public void close() throws Exception {
        for(CypherConnection connection : connections){
            connection.close();
        }
    }

    @Override
    public void executeStatement(String arg) throws Exception{
        for(CypherConnection connection : connections){
            connection.executeStatement(arg);
        }
    }

    @Override
    public List<GDSmithResultSet> executeStatementAndGet(String arg) throws Exception{
        //System.out.println("execute statement: "+arg);
        List<GDSmithResultSet> results = new ArrayList<>();
        for(CypherConnection connection : connections){
            List<GDSmithResultSet> result = connection.executeStatementAndGet(arg);
            if(result == null || result.get(0) == null){
                results.add(null);
                throw new Exception("a specific database failed"); // todo
            }
            results.addAll(result);
        }
        return results;
    }
}
