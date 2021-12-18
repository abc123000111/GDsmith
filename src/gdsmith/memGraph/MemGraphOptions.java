package gdsmith.memGraph;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import gdsmith.DBMSSpecificOptions;
import gdsmith.OracleFactory;
import gdsmith.memGraph.oracle.MemGraphAlwaysTrueOracle;
import gdsmith.common.oracle.TestOracle;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Parameters(separators = "=", commandDescription = "MemGraph (default port: " + MemGraphOptions.DEFAULT_PORT
        + ", default host: " + MemGraphOptions.DEFAULT_HOST)
public class MemGraphOptions implements DBMSSpecificOptions<MemGraphOptions.MemGraphOracleFactory> {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 7687; //todo 改

    @Parameter(names = "--oracle")
    public List<MemGraphOracleFactory> oracles = Arrays.asList(MemGraphOracleFactory.ALWAYS_TRUE);

    @Override
    public List<MemGraphOracleFactory> getTestOracleFactory() {
        return oracles;
    }

    public enum MemGraphOracleFactory implements OracleFactory<MemGraphGlobalState> {

        ALWAYS_TRUE {

            @Override
            public TestOracle create(MemGraphGlobalState globalState) throws SQLException {
                return new MemGraphAlwaysTrueOracle(globalState);
            }
        }
    }
}
