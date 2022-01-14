package gdsmith.composite;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import gdsmith.*;
import gdsmith.common.log.LoggableFactory;

import gdsmith.cypher.*;
import gdsmith.composite.gen.CompositeGraphGenerator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompositeProvider extends CypherProviderAdapter<CompositeGlobalState, CompositeOptions> {
    public CompositeProvider() {
        super(CompositeGlobalState.class, CompositeOptions.class);
    }

    @Override
    public CypherConnection createDatabase(CompositeGlobalState globalState) throws Exception {
        return createDatabaseWithOptions(globalState.getOptions(), globalState.getDbmsSpecificOptions());
    }

    @Override
    public String getDBMSName() {
        return "composite";
    }

    @Override
    public LoggableFactory getLoggableFactory() {
        return new CypherLoggableFactory();
    }

    @Override
    protected void checkViewsAreValid(CompositeGlobalState globalState) {

    }

    @Override
    public void generateDatabase(CompositeGlobalState globalState) throws Exception {
        List<CypherQueryAdapter> queries = CompositeGraphGenerator.createGraph(globalState);
        for(CypherQueryAdapter query : queries){
            globalState.executeStatement(query);
        }

        /*for(int i = 0; i < 10; i++){
            CypherQueryAdapter createNode = CompositeNodeGenerator.createNode(globalState);
            globalState.executeStatement(createNode);
        }*/
        /*while (globalState.getSchema().getDatabaseTables().size() < Randomly.smallNumber() + 1) { //创建tables
            String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());//只是负责命名的final类
            SQLQueryAdapter createTable = MySQLTableGenerator.generate(globalState, tableName);
            globalState.executeStatement(createTable);
        }

        //似乎Action列出了所有的对应数据库的语句，每一个Action对应于mysql/gen中的一个语句
        StatementExecutor<CompositeGlobalState, MySQLProvider.Action> se = new StatementExecutor<>(globalState, MySQLProvider.Action.values(),
                MySQLProvider::mapActions, (q) -> {
            if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                throw new IgnoreMeException();
            }
        });
        se.executeStatements(); //执行query，相当于随机地改变表的结构并添加行？*/
    }

    @Override
    public CompositeOptions generateOptionsFromConfig(JsonObject config) {
        return null;
    }

    @Override
    public CypherConnection createDatabaseWithOptions(MainOptions mainOptions, CompositeOptions specificOptions) throws Exception {
        List<CypherConnection> connections = new ArrayList<>();
        Gson gson = new Gson();
        try {
            FileReader fileReader = new FileReader(specificOptions.getConfigPath());
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);
            Set<String> databaseNamesWithVersion = jsonObject.keySet();
            for(DatabaseProvider provider: Main.getDBMSProviders()){
                String databaseName = provider.getDBMSName().toLowerCase();
                MainOptions options = mainOptions;
                for(String nameWithVersion : databaseNamesWithVersion){
                    if(nameWithVersion.contains(provider.getDBMSName().toLowerCase())){
                        DBMSSpecificOptions command = ((CypherProviderAdapter)provider)
                                .generateOptionsFromConfig(jsonObject.getAsJsonObject(nameWithVersion));
                        connections.add(((CypherProviderAdapter)provider).createDatabaseWithOptions(options, command));
                    }
                }

            }
            System.out.println("success");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CompositeConnection compositeConnection = new CompositeConnection(connections);
        return compositeConnection;
    }
}
