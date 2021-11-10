package gdsmith.neo4j;

import gdsmith.*;
import gdsmith.common.log.LoggableFactory;

import gdsmith.cypher.*;
import gdsmith.neo4j.gen.Neo4jGraphGenerator;
import org.neo4j.driver.Driver;
import gdsmith.neo4j.gen.Neo4jNodeGenerator;

import java.util.List;

public class Neo4jProvider extends CypherProviderAdapter<Neo4jGlobalState, Neo4jOptions> {
    public Neo4jProvider() {
        super(Neo4jGlobalState.class, Neo4jOptions.class);
    }

    enum Action implements AbstractAction<Neo4jGlobalState> {
        CREATE(Neo4jNodeGenerator::createNode);

        private final CypherQueryProvider<Neo4jGlobalState> cypherQueryProvider;

        //SQLQueryProvider是一个接口，返回SQLQueryAdapter
        Action(CypherQueryProvider<Neo4jGlobalState> cypherQueryProvider) {
            this.cypherQueryProvider = cypherQueryProvider;
        }

        @Override
        public CypherQueryAdapter getQuery(Neo4jGlobalState globalState) throws Exception {
            return cypherQueryProvider.getQuery(globalState);
        }
    }

    @Override
    public CypherConnection createDatabase(Neo4jGlobalState globalState) throws Exception {
        String username = globalState.getOptions().getUserName();
        String password = globalState.getOptions().getPassword();
        String host = globalState.getOptions().getHost();
        int port = globalState.getOptions().getPort();
        if (host == null) {
            host = Neo4jOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = Neo4jOptions.DEFAULT_PORT;
        }

        String url = String.format("bolt://%s:%d", host, port);

        /*String databaseName = globalState.getDatabaseName();
        globalState.getState().logStatement("DROP DATABASE IF EXISTS " + databaseName);
        globalState.getState().logStatement("CREATE DATABASE " + databaseName);
        globalState.getState().logStatement("USE " + databaseName);
        String url = String.format("jdbc:mysql://%s:%d?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true",
                host, port);
        Connection con = DriverManager.getConnection(url, username, password);*/ //这里是从globalstate那里获取到option信息然后建立连接，进而建立数据库
        /*try (Statement s = con.createStatement()) {
            s.execute("DROP DATABASE IF EXISTS " + databaseName);
        }
        try (Statement s = con.createStatement()) {
            s.execute("CREATE DATABASE " + databaseName);
        }
        try (Statement s = con.createStatement()) {
            s.execute("USE " + databaseName);
        }*/
        Driver driver = Neo4jDriverManager.getDriver(url, username, password);
        //todo 根据名字创建新的空数据库
        return new Neo4jConnection(driver);
    }

    @Override
    public String getDBMSName() {
        return "neo4j";
    }

    @Override
    public LoggableFactory getLoggableFactory() {
        return new CypherLoggableFactory();
    }

    @Override
    protected void checkViewsAreValid(Neo4jGlobalState globalState) {

    }

    @Override
    public void generateDatabase(Neo4jGlobalState globalState) throws Exception {
        List<CypherQueryAdapter> queries = Neo4jGraphGenerator.createGraph(globalState);
        for(CypherQueryAdapter query : queries){
            globalState.executeStatement(query);
        }

        /*for(int i = 0; i < 10; i++){
            CypherQueryAdapter createNode = Neo4jNodeGenerator.createNode(globalState);
            globalState.executeStatement(createNode);
        }*/
        /*while (globalState.getSchema().getDatabaseTables().size() < Randomly.smallNumber() + 1) { //创建tables
            String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());//只是负责命名的final类
            SQLQueryAdapter createTable = MySQLTableGenerator.generate(globalState, tableName);
            globalState.executeStatement(createTable);
        }

        //似乎Action列出了所有的对应数据库的语句，每一个Action对应于mysql/gen中的一个语句
        StatementExecutor<Neo4jGlobalState, MySQLProvider.Action> se = new StatementExecutor<>(globalState, MySQLProvider.Action.values(),
                MySQLProvider::mapActions, (q) -> {
            if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                throw new IgnoreMeException();
            }
        });
        se.executeStatements(); //执行query，相当于随机地改变表的结构并添加行？*/
    }
}
