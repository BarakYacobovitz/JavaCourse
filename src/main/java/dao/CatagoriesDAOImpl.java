package dao;

import beans.Catagory;
import db.JDBCUtils;
import db.ResultsUtils;
import exceptions.CouponsSystemException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by yacob at 29/05/2022 - this class implements catagoryDAO
 */
public class CatagoriesDAOImpl implements CatagoriesDAO{
    private static final String QUERY_INSERT = "INSERT INTO `coupons_database`.`categories` (`name`) VALUES (?);\n";
    private static final String QUERY_UPDATE = "UPDATE `coupons_database`.`categories` SET `name` = ? WHERE (`id` = ?);\n";
    private static final String QUERY_DELETE = "DELETE FROM `coupons_database`.`categories` WHERE (`id` = ?);\n";
    private static final String QUERY_GET_ALL = "SELECT * FROM coupons_database.categories;\n";
    private static final String QUERY_GET_ONE = "SELECT * FROM coupons_database.categories WHERE (`id` = ?);\n";
    private static final String QUERY_IS_EXISTS_BY_ID = "select exists(SELECT * FROM coupons_database.categories WHERE (`id` = ?)) as res;\n";
    private static final String QUERY_IS_EXISTS_BY_NAME = "select exists(SELECT * FROM coupons_database.categories WHERE (`name` = ?)) as res;\n";
    @Override
    public void add(String catagory) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, catagory);
        JDBCUtils.executeQuery(QUERY_INSERT, params);
    }
    public void AddAllCatagories( ) throws CouponsSystemException{
        for (Catagory cat:Catagory.values()) {
            this.add(String.valueOf(cat));

        }
    }
    @Override
    public void update(Integer integer, String catagory) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, catagory);
        params.put(2, integer);
        JDBCUtils.executeQuery(QUERY_UPDATE, params);
    }

    @Override
    public void delete(Integer integer) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, integer);
        JDBCUtils.executeQuery(QUERY_DELETE, params);

    }

    @Override
    public List<String> getAll() throws CouponsSystemException, SQLException, InterruptedException {
        List<String> results = new ArrayList<>();

        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL);
        for (Object object : rows) {
            results.add(ResultsUtils.catagoryFromRow((HashMap<String, Object>) object));
        }
        return results;
    }

    @Override
    public String getSingle(Integer integer) throws CouponsSystemException {
        String result = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, integer);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ONE, params);
        if (rows.isEmpty()) {
            result = null;
        } else {
            result = ResultsUtils.catagoryFromRow((HashMap<String, Object>) (rows.get(0)));
        }
        return result;
    }
    public boolean isExists(Integer integer) throws CouponsSystemException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, integer);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IS_EXISTS_BY_ID, params);
        if (rows.isEmpty()) {
            result = false;
        } else {
            result = Boolean.valueOf(ResultsUtils.booleanFromRow((HashMap<String, Object>) (rows.get(0))));
        }
        return result;


    }
    public boolean isExists(String catagory) throws CouponsSystemException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, catagory);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IS_EXISTS_BY_NAME, params);
        if (rows.isEmpty()) {
            result = false;
        } else {
            result = Boolean.valueOf(ResultsUtils.booleanFromRow((HashMap<String, Object>) (rows.get(0))));
        }
        return result;


    }
}
