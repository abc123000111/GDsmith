package org.example.gdsmith.tugraph;

import com.google.gson.JsonObject;
import org.example.gdsmith.*;
import org.example.gdsmith.common.log.LoggableFactory;

import org.example.gdsmith.cypher.*;
import org.example.gdsmith.cypher.*;
import org.example.gdsmith.tugraph.gen.TuGraphGraphGenerator;
import org.example.gdsmith.tugraph.gen.TuGraphNodeGenerator;
import org.example.gdsmith.tugraph.schema.TuGraphSchema;
import org.example.gdsmith.AbstractAction;
import org.example.gdsmith.MainOptions;

import java.util.List;

public class TuGraphProvider extends CypherProviderAdapter<TuGraphGlobalState, TuGraphSchema, TuGraphOptions> {
    public TuGraphProvider() {
        super(TuGraphGlobalState.class, TuGraphOptions.class);
    }

    @Override
    public TuGraphOptions generateOptionsFromConfig(JsonObject config) {
        return TuGraphOptions.parseOptionFromFile(config);
    }

    @Override
    public CypherConnection createDatabaseWithOptions(MainOptions mainOptions, TuGraphOptions specificOptions) throws Exception {
        String username = specificOptions.getUsername();
        String password = specificOptions.getPassword();
        String host = specificOptions.getHost();
        int port = specificOptions.getPort();
        if (host == null) {
            host = TuGraphOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = TuGraphOptions.DEFAULT_PORT;
        }

        String url = String.format("list://%s:%d", host, port);
        TuGraphConnection con = new TuGraphConnection(url, username, password, specificOptions.databaseName);
        con.deleteAndCreate();
        return con;
    }

    enum Action implements AbstractAction<TuGraphGlobalState> {
        CREATE(TuGraphNodeGenerator::createNode);

        private final CypherQueryProvider<TuGraphGlobalState> cypherQueryProvider;

        //SQLQueryProvider是一个接口，返回SQLQueryAdapter
        Action(CypherQueryProvider<TuGraphGlobalState> cypherQueryProvider) {
            this.cypherQueryProvider = cypherQueryProvider;
        }

        @Override
        public CypherQueryAdapter getQuery(TuGraphGlobalState globalState) throws Exception {
            return cypherQueryProvider.getQuery(globalState);
        }
    }

    @Override
    public CypherConnection createDatabase(TuGraphGlobalState globalState) throws Exception {
       return createDatabaseWithOptions(globalState.getOptions(), globalState.getDbmsSpecificOptions());
    }

    @Override
    public String getDBMSName() {
        return "tugraph";
    }

    @Override
    public LoggableFactory getLoggableFactory() {
        return new CypherLoggableFactory();
    }

    @Override
    protected void checkViewsAreValid(TuGraphGlobalState globalState) {

    }

    @Override
    public void generateDatabase(TuGraphGlobalState globalState) throws Exception {
        List<CypherQueryAdapter> queries = TuGraphGraphGenerator.createGraph(globalState);

        for(CypherQueryAdapter query : queries){
            globalState.executeStatement(query);
        }
    }
}
