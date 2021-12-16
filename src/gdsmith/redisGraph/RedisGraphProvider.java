package gdsmith.redisGraph;

import com.redislabs.redisgraph.impl.api.RedisGraph;
import gdsmith.*;
import gdsmith.redisGraph.RedisGraphConnection;
import gdsmith.redisGraph.RedisGraphGlobalState;
import gdsmith.redisGraph.RedisGraphOptions;
import gdsmith.common.log.LoggableFactory;

import gdsmith.cypher.*;
import gdsmith.redisGraph.gen.RedisGraphGraphGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class RedisGraphProvider extends CypherProviderAdapter<RedisGraphGlobalState, RedisGraphOptions> {
    public RedisGraphProvider() {
        super(RedisGraphGlobalState.class, RedisGraphOptions.class);
    }

    @Override
    public CypherConnection createDatabase(RedisGraphGlobalState globalState) throws Exception {
        String username = globalState.getOptions().getUserName();
        String password = globalState.getOptions().getPassword();
        String host = globalState.getOptions().getHost();
        int port = globalState.getOptions().getPort();
        if (host == null) {
            host = RedisGraphOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = RedisGraphOptions.DEFAULT_PORT;
        }
        RedisGraphConnection con = null;
        try{
            con = new RedisGraphConnection(new RedisGraph(host, port), "sqlancer");
            con.executeStatement("MATCH (n) DETACH DELETE n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    @Override
    public String getDBMSName() {
        return "redisgraph";
    }

    @Override
    public LoggableFactory getLoggableFactory() {
        return new CypherLoggableFactory();
    }

    @Override
    protected void checkViewsAreValid(RedisGraphGlobalState globalState) {

    }

    @Override
    public void generateDatabase(RedisGraphGlobalState globalState) throws Exception {
        List<CypherQueryAdapter> queries = RedisGraphGraphGenerator.createGraph(globalState);
        for(CypherQueryAdapter query : queries){
            globalState.executeStatement(query);
        }

        /*for(int i = 0; i < 10; i++){
            CypherQueryAdapter createNode = RedisGraphNodeGenerator.createNode(globalState);
            globalState.executeStatement(createNode);
        }*/
        /*while (globalState.getSchema().getDatabaseTables().size() < Randomly.smallNumber() + 1) { //创建tables
            String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());//只是负责命名的final类
            SQLQueryAdapter createTable = MySQLTableGenerator.generate(globalState, tableName);
            globalState.executeStatement(createTable);
        }

        //似乎Action列出了所有的对应数据库的语句，每一个Action对应于mysql/gen中的一个语句
        StatementExecutor<RedisGraphGlobalState, MySQLProvider.Action> se = new StatementExecutor<>(globalState, MySQLProvider.Action.values(),
                MySQLProvider::mapActions, (q) -> {
            if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                throw new IgnoreMeException();
            }
        });
        se.executeStatements(); //执行query，相当于随机地改变表的结构并添加行？*/
    }
}
