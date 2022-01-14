package gdsmith.composite;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import gdsmith.DBMSSpecificOptions;
import gdsmith.OracleFactory;
import gdsmith.composite.oracle.CompositeAlwaysTrueOracle;
import gdsmith.common.oracle.TestOracle;
import gdsmith.composite.oracle.CompositeDifferentialOracle;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Parameters(separators = "=", commandDescription = "Composite (default port: " + CompositeOptions.DEFAULT_PORT
        + ", default host: " + CompositeOptions.DEFAULT_HOST)
public class CompositeOptions implements DBMSSpecificOptions<CompositeOptions.CompositeOracleFactory> {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 2424; //todo 改


    @Parameter(names = "--oracle")
    public List<CompositeOracleFactory> oracles = Arrays.asList(CompositeOracleFactory.DIFFERENTIAL);

    public String getConfigPath() {
        return configPath;
    }

    @Parameter(names = "--config")
    public String configPath = "./config.json";

    @Override
    public List<CompositeOracleFactory> getTestOracleFactory() {
        return oracles;
    }

    public enum CompositeOracleFactory implements OracleFactory<CompositeGlobalState> {

        ALWAYS_TRUE {

            @Override
            public TestOracle create(CompositeGlobalState globalState) throws SQLException {
                return new CompositeAlwaysTrueOracle(globalState);
            }
        },
        DIFFERENTIAL {
            @Override
            public TestOracle create(CompositeGlobalState globalState) throws SQLException{
                return new CompositeDifferentialOracle(globalState);
            }
        }
    }
}
