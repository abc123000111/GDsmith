package gdsmith.arcadeDB;

import com.arcadedb.database.Database;
import com.arcadedb.database.DatabaseFactory;
import com.google.gson.JsonObject;
import gdsmith.*;
import gdsmith.common.log.LoggableFactory;

import gdsmith.cypher.*;
import gdsmith.arcadeDB.gen.ArcadeDBGraphGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

public class ArcadeDBProvider extends CypherProviderAdapter<ArcadeDBGlobalState, ArcadeDBOptions> {
    public ArcadeDBProvider() {
        super(ArcadeDBGlobalState.class, ArcadeDBOptions.class);
    }

    @Override
    public CypherConnection createDatabase(ArcadeDBGlobalState globalState) throws Exception {
        return createDatabaseWithOptions(globalState.getOptions(), globalState.getDbmsSpecificOptions());
    }

    @Override
    public String getDBMSName() {
        return "arcadedb";
    }

    @Override
    public LoggableFactory getLoggableFactory() {
        return new CypherLoggableFactory();
    }

    @Override
    protected void checkViewsAreValid(ArcadeDBGlobalState globalState) {

    }

    @Override
    public void generateDatabase(ArcadeDBGlobalState globalState) throws Exception {
        List<CypherQueryAdapter> queries = ArcadeDBGraphGenerator.createGraph(globalState);
        for(CypherQueryAdapter query : queries){
            globalState.executeStatement(query);
        }

        /*for(int i = 0; i < 10; i++){
            CypherQueryAdapter createNode = ArcadeDBNodeGenerator.createNode(globalState);
            globalState.executeStatement(createNode);
        }*/
        /*while (globalState.getSchema().getDatabaseTables().size() < Randomly.smallNumber() + 1) { //创建tables
            String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());//只是负责命名的final类
            SQLQueryAdapter createTable = MySQLTableGenerator.generate(globalState, tableName);
            globalState.executeStatement(createTable);
        }

        //似乎Action列出了所有的对应数据库的语句，每一个Action对应于mysql/gen中的一个语句
        StatementExecutor<ArcadeDBGlobalState, MySQLProvider.Action> se = new StatementExecutor<>(globalState, MySQLProvider.Action.values(),
                MySQLProvider::mapActions, (q) -> {
            if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                throw new IgnoreMeException();
            }
        });
        se.executeStatements(); //执行query，相当于随机地改变表的结构并添加行？*/
    }

    @Override
    public ArcadeDBOptions generateOptionsFromConfig(JsonObject config) {
        return ArcadeDBOptions.parseOptionFromFile(config);
    }

    @Override
    public CypherConnection createDatabaseWithOptions(MainOptions mainOptions, ArcadeDBOptions specificOptions) throws Exception {
        String username = specificOptions.getUsername();
        String password = specificOptions.getPassword();
        String host = specificOptions.getHost();
        int port = specificOptions.getPort();
        String path = specificOptions.EMBEDDED_PATH;
        if (host == null) {
            host = ArcadeDBOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = ArcadeDBOptions.DEFAULT_PORT;
        }

        ArcadeDBConnection con = null;

        try{
            DatabaseFactory arcade = new DatabaseFactory(path);
            Database database;
            if(!arcade.exists()){
                database = arcade.create();
            }
            else{
                database = arcade.open();
            }
            con = new ArcadeDBConnection(database);
            //todo 初始化
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
