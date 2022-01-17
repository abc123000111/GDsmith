package gdsmith.neo4j;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import gdsmith.DBMSSpecificOptions;
import gdsmith.OracleFactory;
import gdsmith.common.oracle.TestOracle;
import gdsmith.neo4j.Neo4jOptions.Neo4jOracleFactory;
import gdsmith.neo4j.oracle.Neo4jSmithCrashOracle;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Parameters(separators = "=", commandDescription = "Neo4J (default port: " + Neo4jOptions.DEFAULT_PORT
        + ", default host: " + Neo4jOptions.DEFAULT_HOST)
public class Neo4jOptions implements DBMSSpecificOptions<Neo4jOracleFactory> {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 7689; //todo 改

    public static Neo4jOptions parseOptionFromFile(JsonObject jsonObject){
        Neo4jOptions options = new Neo4jOptions();
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
    public List<Neo4jOracleFactory> oracles = Arrays.asList(Neo4jOracleFactory.RANDOM_CRASH);

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
    public String username = "neo4j";

    @Parameter(names = "--password")
    public String password = "sqlancer";

    @Override
    public List<Neo4jOracleFactory> getTestOracleFactory() {
        return oracles;
    }

    public enum Neo4jOracleFactory implements OracleFactory<Neo4jGlobalState> {

        RANDOM_CRASH {

            @Override
            public TestOracle create(Neo4jGlobalState globalState) throws SQLException {
                return new Neo4jSmithCrashOracle(globalState);
            }
        }
    }
}
