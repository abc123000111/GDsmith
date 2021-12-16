package gdsmith.redisGraph;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import gdsmith.DBMSSpecificOptions;
import gdsmith.OracleFactory;
import gdsmith.redisGraph.oracle.RedisGraphAlwaysTrueOracle;
import gdsmith.common.oracle.TestOracle;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Parameters(separators = "=", commandDescription = "RedisGraph (default port: " + RedisGraphOptions.DEFAULT_PORT
        + ", default host: " + RedisGraphOptions.DEFAULT_HOST)
public class RedisGraphOptions implements DBMSSpecificOptions<RedisGraphOptions.RedisGraphOracleFactory> {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 5432; //todo 改

    @Parameter(names = "--oracle")
    public List<RedisGraphOracleFactory> oracles = Arrays.asList(RedisGraphOracleFactory.ALWAYS_TRUE);

    @Override
    public List<RedisGraphOracleFactory> getTestOracleFactory() {
        return oracles;
    }

    public enum RedisGraphOracleFactory implements OracleFactory<RedisGraphGlobalState> {

        ALWAYS_TRUE {

            @Override
            public TestOracle create(RedisGraphGlobalState globalState) throws SQLException {
                return new RedisGraphAlwaysTrueOracle(globalState);
            }
        }
    }
}
