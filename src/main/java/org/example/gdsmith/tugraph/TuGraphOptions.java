package org.example.gdsmith.tugraph;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.gson.JsonObject;
import org.example.gdsmith.DBMSSpecificOptions;
import org.example.gdsmith.OracleFactory;
import org.example.gdsmith.common.oracle.TestOracle;
import org.example.gdsmith.cypher.dsl.IQueryGenerator;
import org.example.gdsmith.tugraph.TuGraphOptions.TuGraphOracleFactory;
import org.example.gdsmith.tugraph.oracle.TuGraphNoRecOracle;
import org.example.gdsmith.tugraph.oracle.TuGraphSmithCrashOracle;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Parameters(separators = "=", commandDescription = "TuGraph (default port: " + TuGraphOptions.DEFAULT_PORT
        + ", default host: " + TuGraphOptions.DEFAULT_HOST)
public class TuGraphOptions implements DBMSSpecificOptions<TuGraphOracleFactory> {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 9090;

    public static TuGraphOptions parseOptionFromFile(JsonObject jsonObject){
        TuGraphOptions options = new TuGraphOptions();
        if(jsonObject.has("host")){
            options.host = jsonObject.get("host").getAsString();
        }
        if(jsonObject.has("port")){
            options.port = jsonObject.get("port").getAsInt();
        }
        if(jsonObject.has("username")){
            options.username = jsonObject.get("username").getAsString();
        }
        if(jsonObject.has("password")){
            options.password = jsonObject.get("password").getAsString();
        }
        return options;
    }

    @Parameter(names = "--oracle")
    public List<TuGraphOracleFactory> oracles = Arrays.asList(TuGraphOracleFactory.RANDOM_CRASH);

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    @Parameter(names = "--host")
    public String host = DEFAULT_HOST;

    @Parameter(names = "--port")
    public int port = DEFAULT_PORT;

    @Parameter(names = "--username")
    public String username = "admin";

    @Parameter(names = "--password")
    public String password = "73@TuGraph";

    @Parameter(names = "--database_name")
    public String databaseName = "gdsmith";

    @Override
    public List<TuGraphOracleFactory> getTestOracleFactory() {
        return oracles;
    }

    @Override
    public IQueryGenerator getQueryGenerator() {
        return null;
    }

    public enum TuGraphOracleFactory implements OracleFactory<TuGraphGlobalState> {

        RANDOM_CRASH {

            @Override
            public TestOracle create(TuGraphGlobalState globalState) throws SQLException {
                return new TuGraphSmithCrashOracle(globalState);
            }
        },
        NO_REC {
            @Override
            public TestOracle create(TuGraphGlobalState globalState) throws SQLException {
                return new TuGraphNoRecOracle(globalState);
            }
        }
    }
}
