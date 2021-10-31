package gdsmith.mysql;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import gdsmith.DBMSSpecificOptions;
import gdsmith.OracleFactory;
import gdsmith.common.oracle.TestOracle;
import gdsmith.mysql.MySQLOptions.MySQLOracleFactory;
import gdsmith.mysql.oracle.MySQLPivotedQuerySynthesisOracle;
import gdsmith.mysql.oracle.MySQLTLPWhereOracle;

@Parameters(separators = "=", commandDescription = "MySQL (default port: " + MySQLOptions.DEFAULT_PORT
        + ", default host: " + MySQLOptions.DEFAULT_HOST)
public class MySQLOptions implements DBMSSpecificOptions<MySQLOracleFactory> {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 3306;

    @Parameter(names = "--oracle")
    public List<MySQLOracleFactory> oracles = Arrays.asList(MySQLOracleFactory.TLP_WHERE);

    public enum MySQLOracleFactory implements OracleFactory<MySQLGlobalState> {

        TLP_WHERE {

            @Override
            public TestOracle create(MySQLGlobalState globalState) throws SQLException {
                return new MySQLTLPWhereOracle(globalState);
            }

        },
        PQS {

            @Override
            public TestOracle create(MySQLGlobalState globalState) throws SQLException {
                return new MySQLPivotedQuerySynthesisOracle(globalState);
            }

            @Override
            public boolean requiresAllTablesToContainRows() {
                return true;
            }

        }
    }

    @Override
    public List<MySQLOracleFactory> getTestOracleFactory() {
        return oracles;
    }

}
