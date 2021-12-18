package gdsmith.arcadeDB;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import gdsmith.DBMSSpecificOptions;
import gdsmith.OracleFactory;
import gdsmith.arcadeDB.oracle.ArcadeDBAlwaysTrueOracle;
import gdsmith.common.oracle.TestOracle;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Parameters(separators = "=", commandDescription = "ArcadeDB (default port: " + ArcadeDBOptions.DEFAULT_PORT
        + ", default host: " + ArcadeDBOptions.DEFAULT_HOST)
public class ArcadeDBOptions implements DBMSSpecificOptions<ArcadeDBOptions.ArcadeDBOracleFactory> {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 2424; //todo 改

    @Parameter(names = "--path")
    public String EMBEDDED_PATH = "databases/mydb";

    @Parameter(names = "--oracle")
    public List<ArcadeDBOracleFactory> oracles = Arrays.asList(ArcadeDBOracleFactory.ALWAYS_TRUE);

    @Override
    public List<ArcadeDBOracleFactory> getTestOracleFactory() {
        return oracles;
    }

    public enum ArcadeDBOracleFactory implements OracleFactory<ArcadeDBGlobalState> {

        ALWAYS_TRUE {

            @Override
            public TestOracle create(ArcadeDBGlobalState globalState) throws SQLException {
                return new ArcadeDBAlwaysTrueOracle(globalState);
            }
        }
    }
}
