package gdsmith.memGraph;

import com.google.gson.JsonObject;
import gdsmith.*;
import gdsmith.common.log.LoggableFactory;

import gdsmith.cypher.*;
import gdsmith.memGraph.gen.MemGraphGraphGenerator;
import org.neo4j.driver.Driver;

import java.util.List;

public class MemGraphProvider extends CypherProviderAdapter<MemGraphGlobalState, MemGraphOptions> {
    public MemGraphProvider() {
        super(MemGraphGlobalState.class, MemGraphOptions.class);
    }

    @Override
    public CypherConnection createDatabase(MemGraphGlobalState globalState) throws Exception {
        return createDatabaseWithOptions(globalState.getOptions(), globalState.getDbmsSpecificOptions());
    }

    @Override
    public String getDBMSName() {
        return "memgraph";
    }

    @Override
    public LoggableFactory getLoggableFactory() {
        return new CypherLoggableFactory();
    }

    @Override
    protected void checkViewsAreValid(MemGraphGlobalState globalState) {

    }

    @Override
    public void generateDatabase(MemGraphGlobalState globalState) throws Exception {
        List<CypherQueryAdapter> queries = MemGraphGraphGenerator.createGraph(globalState);
        for(CypherQueryAdapter query : queries){
            globalState.executeStatement(query);
        }

        /*for(int i = 0; i < 10; i++){
            CypherQueryAdapter createNode = MemGraphNodeGenerator.createNode(globalState);
            globalState.executeStatement(createNode);
        }*/
        /*while (globalState.getSchema().getDatabaseTables().size() < Randomly.smallNumber() + 1) { //创建tables
            String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());//只是负责命名的final类
            SQLQueryAdapter createTable = MySQLTableGenerator.generate(globalState, tableName);
            globalState.executeStatement(createTable);
        }

        //似乎Action列出了所有的对应数据库的语句，每一个Action对应于mysql/gen中的一个语句
        StatementExecutor<MemGraphGlobalState, MySQLProvider.Action> se = new StatementExecutor<>(globalState, MySQLProvider.Action.values(),
                MySQLProvider::mapActions, (q) -> {
            if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                throw new IgnoreMeException();
            }
        });
        se.executeStatements(); //执行query，相当于随机地改变表的结构并添加行？*/
    }

    @Override
    public MemGraphOptions generateOptionsFromConfig(JsonObject config) {
        return MemGraphOptions.parseOptionFromFile(config);
    }

    @Override
    public CypherConnection createDatabaseWithOptions(MainOptions mainOptions, MemGraphOptions specificOptions) throws Exception {
        String username = specificOptions.getUsername();
        String password = specificOptions.getPassword();
        String host = specificOptions.getHost();
        int port = specificOptions.getPort();
        if (host == null) {
            host = MemGraphOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = MemGraphOptions.DEFAULT_PORT;
        }

        String url = String.format("bolt://%s:%d", host, port);
        Driver driver = MemGraphDriverManager.getDriver(url, username, password);
        MemGraphConnection con = new MemGraphConnection(driver);
        con.executeStatement("MATCH (n) DETACH DELETE n");
        //con.executeStatement("CALL apoc.schema.assert({}, {})");
        return con;
    }
}
