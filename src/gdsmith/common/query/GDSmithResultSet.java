package gdsmith.common.query;


import org.neo4j.driver.Record;

import java.io.Closeable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;


public class GDSmithResultSet implements Closeable {

    int resultRowNum;
    List<Map<String, Object>> result;

    public List<Map<String, Object>> getResult() {
        return result;
    }

    private String getMapAsString(Map<String, Object> m) {
        String s = "{";
        for (String key : m.keySet()) {
            s += key + ":" + m.get(key).toString() + ",";
        }
        s += "}";
        return s;
    }

    private List<String> resultToStringList() {
        List<String> l = new ArrayList<>();
        for (int i = 0; i < result.size(); ++i) {
            l.add(getMapAsString(result.get(i)));
        }
        return l;
    }

    public boolean compare(GDSmithResultSet secondGDSmithResultSet, boolean withOrder) {
        List<String> firstSortList = new ArrayList<>(resultToStringList());
        List<String> secondSortList = new ArrayList<>(secondGDSmithResultSet.resultToStringList());

        if (firstSortList.size() != secondSortList.size()) {
            return false;
        }

        if (!withOrder) {
            Collections.sort(firstSortList);
            Collections.sort(secondSortList);
        }

        return firstSortList.equals(secondSortList);
    }

    public boolean compareWithOrder(GDSmithResultSet secondGDSmithResultSet) {
        return compare(secondGDSmithResultSet, true);
    }
    public boolean compareWithOutOrder(GDSmithResultSet secondGDSmithResultSet) {
        return compare(secondGDSmithResultSet, false);
    }

    public GDSmithResultSet(org.neo4j.driver.Result rs) {
        List<Record> resultList = rs.list();
        resultRowNum = resultList.size();
        result = new ArrayList<Map<String, Object>>();

        for (Record x : resultList) {
            Map<String, Object> m = x.asMap();
            // System.out.println(m);
            result.add(m);
        }
        // System.out.println("finish parse!");
        System.out.println("result_size=" + resultRowNum);
    }

    public GDSmithResultSet(ResultSet rs) throws SQLException {
        resultRowNum = rs.getRow();
        result = new ArrayList<Map<String, Object>>();

        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> row = new HashMap<String, Object>(columns);
            for(int i = 1; i <= columns; ++i){
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            result.add(row);
        }
        System.out.println("result_size=" + resultRowNum);
    }

    public GDSmithResultSet(com.redislabs.redisgraph.ResultSet rs) throws SQLException {
        resultRowNum = rs.size();
        result = new ArrayList<Map<String, Object>>();

        while (rs.hasNext()) {
            com.redislabs.redisgraph.Record r = rs.next();
            Map<String, Object> row = new HashMap<String, Object>();
            for (String k : r.keys()) {
                row.put(k, r.getValue(k));
            }
            result.add(row);
        }
        System.out.println("result_size=" + resultRowNum);
    }


    public int getRowNum() {
        return resultRowNum;
    }

    @Override
    public void close() {

    }

    public void registerEpilogue(Runnable runnableEpilogue) {

    }

    public boolean next() throws SQLException {
        // TODO: refactor me
        return true;
    }

    public String getString(int i) throws SQLException {
        // TODO: refactor me
        return "zzz";
    }

    public long getLong(int i) throws SQLException {
        // TODO: refactor me
        return 1;
    }

    public boolean isClosed() throws SQLException {
        // TODO: not sure how to handle this method
        return true;
    }

}
