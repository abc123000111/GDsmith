package gdsmith;

public abstract class CypherConnection implements SQLancerDBConnection{

    public void executeStatement(String arg) throws Exception{
        System.out.println("execute statement: "+arg);
    }

}
