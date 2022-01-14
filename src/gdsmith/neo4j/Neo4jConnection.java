package gdsmith.neo4j;

import gdsmith.common.query.GDSmithResultSet;
import gdsmith.cypher.CypherConnection;
import org.neo4j.driver.*;

import java.util.Arrays;
import java.util.List;

public class Neo4jConnection extends CypherConnection {

    private Driver driver;

    public Neo4jConnection(Driver driver){
        this.driver = driver;
    }


    @Override
    public String getDatabaseVersion() throws Exception {
        //todo complete
        return "";
    }

    @Override
    public void close() throws Exception {
        Neo4jDriverManager.closeDriver(driver);
    }

    @Override
    public void executeStatement(String arg) throws Exception{
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run(arg);
                    return "";
                }
            } );
            //System.out.println( greeting );
        }
    }


    @Override
    public List<GDSmithResultSet> executeStatementAndGet(String arg) throws Exception{
        try ( Session session = driver.session() )
        {
            return Arrays.asList(new GDSmithResultSet(session.run(arg)));
        }
    }
}
