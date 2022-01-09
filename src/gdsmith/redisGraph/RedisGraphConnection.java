package gdsmith.redisGraph;

import com.redislabs.redisgraph.RedisGraphContext;
import com.redislabs.redisgraph.RedisGraphTransaction;
import com.redislabs.redisgraph.impl.api.RedisGraph;
import gdsmith.cypher.CypherConnection;

public class RedisGraphConnection extends CypherConnection {

    private final RedisGraph graph;
    private String graphName;

    public RedisGraphConnection(RedisGraph graph, String graphName){
         this.graph = graph;
         this.graphName = graphName;
    }


    @Override
    public String getDatabaseVersion() throws Exception {
        //todo complete
        return "";
    }

    @Override
    public void close() throws Exception {
        graph.deleteGraph(graphName);
    }

    @Override
    public void executeStatement(String arg) throws Exception{
        graph.query(graphName, arg);
    }
}