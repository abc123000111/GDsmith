package gdsmith.neo4j;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
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
    public static final int DEFAULT_PORT = 7687; //todo 改

    @Parameter(names = "--oracle")
    public List<Neo4jOracleFactory> oracles = Arrays.asList(Neo4jOracleFactory.RANDOM_CRASH);

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
