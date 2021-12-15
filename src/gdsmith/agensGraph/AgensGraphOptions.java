package gdsmith.agensGraph;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import gdsmith.DBMSSpecificOptions;
import gdsmith.OracleFactory;
import gdsmith.agensGraph.oracle.AgensGraphAlwaysTrueOracle;
import gdsmith.common.oracle.TestOracle;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Parameters(separators = "=", commandDescription = "AgensGraph (default port: " + AgensGraphOptions.DEFAULT_PORT
        + ", default host: " + AgensGraphOptions.DEFAULT_HOST)
public class AgensGraphOptions implements DBMSSpecificOptions<AgensGraphOptions.AgensGraphOracleFactory> {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 5432; //todo 改

    @Parameter(names = "--oracle")
    public List<AgensGraphOracleFactory> oracles = Arrays.asList(AgensGraphOracleFactory.ALWAYS_TRUE);

    @Override
    public List<AgensGraphOracleFactory> getTestOracleFactory() {
        return oracles;
    }

    public enum AgensGraphOracleFactory implements OracleFactory<AgensGraphGlobalState> {

        ALWAYS_TRUE {

            @Override
            public TestOracle create(AgensGraphGlobalState globalState) throws SQLException {
                return new AgensGraphAlwaysTrueOracle(globalState);
            }
        }
    }
}
