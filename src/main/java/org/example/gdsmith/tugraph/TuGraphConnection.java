package org.example.gdsmith.tugraph;

import org.example.gdsmith.common.query.GDSmithResultSet;
import org.example.gdsmith.composite.CompositeConnection;
import org.example.gdsmith.cypher.CypherConnection;

import java.util.Arrays;
import java.util.List;
import com.alipay.tugraph.TuGraphRpcClient;

public class TuGraphConnection extends CypherConnection {

    TuGraphRpcClient client;
    String graphName;

    public TuGraphConnection(String url, String username, String password, String graphName){
        client = new TuGraphRpcClient(url, username, password);
        this.graphName = graphName;
    }


    @Override
    public String getDatabaseVersion() throws Exception {
        //todo complete
        return "tugraph";
    }

    @Override
    public void close() throws Exception {
        client.stopClient();
    }

    public void deleteAndCreate() {
        try {
            // delete graph
            client.callCypher(String.format("CALL dbms.graph.deleteGraph('%s')", graphName), "default", CompositeConnection.TIMEOUT);
        } catch (Exception e) {
        } finally {
            client.callCypher(String.format("CALL dbms.graph.createGraph('%s', 'this is a demo graph', 20)", graphName), "default", CompositeConnection.TIMEOUT);
        }
    }

    @Override
    public void executeStatement(String arg) throws Exception{
        client.callCypher(arg, graphName, CompositeConnection.TIMEOUT);
    }


    @Override
    public List<GDSmithResultSet> executeStatementAndGet(String arg) throws Exception{
        //todo: result set
        return Arrays.asList(new GDSmithResultSet(client.callCypher(arg, graphName, CompositeConnection.TIMEOUT)));
    }
}
