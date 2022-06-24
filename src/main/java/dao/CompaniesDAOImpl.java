package dao;

import beans.Company;
import db.JDBCUtils;
import db.ResultsUtils;
import exceptions.CouponsSystemException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by yacob at 03/06/2022 this class implements companyDAO
 */
public class CompaniesDAOImpl implements CompaniesDAO {
    private static final String QUERY_INSERT = "INSERT INTO `coupons_database`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?);\n";
    private static final String QUERY_UPDATE = "UPDATE `coupons_database`.`companies` SET `name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);\n";
    private static final String QUERY_DELETE ="DELETE FROM `coupons_database`.`companies` WHERE (`id` = ?);\n";
    private static final String QUERY_GET_ALL = "SELECT * FROM coupons_database.companies;\n";
    private static final String QUERY_GET_ONE = "SELECT * FROM coupons_database.companies WHERE (`id` = ?);\n";
    private static final String QUERY_IS_EXISTS_BY_ID = "select exists(SELECT * FROM coupons_database.companies WHERE (`id` = ?)) as res;\n";
    private static final String QUERY_IS_EXISTS_BY_EMAIL_PASSWORD = "select exists(SELECT * FROM coupons_database.companies where (email = ? and password = ?)) as res;";
    private static final String QUERY_GET_COMPANY_BY_EMAIL_PASSWORD  = "select * FROM coupons_database.companies where (email = ? and password = ?);";
    private static final String QUERY_IS_NAME_ALREADY_EXISTS = "select exists(SELECT * FROM coupons_database.companies where (name = ? )) as res;";
    private static final String QUERY_IS_EMAIL_ALREADY_EXISTS = "select exists(SELECT * FROM coupons_database.companies where (email = ? )) as res;";
    private static final String QUERY_DELETE_ALL_COUPONS_RELATED_TO_COMPANY =" delete from coupons_database.customer_vs_coupons\n" +
            " where coupon_id in (select id from  coupons_database.coupons where company_id = ?)";
    private static final String QUERY_IS_TITLE_ALREADY_EXISTS_FOR_A_COMPANY = "select exists(SELECT * FROM coupons_database.coupons where (title = ? and company_id = ?)) as res;";

    @Override
    public void add(Company company) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        JDBCUtils.executeQuery(QUERY_INSERT, params);
    }

    @Override
    public void update(Integer integer, Company company) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        params.put(4, integer);
        JDBCUtils.executeQuery(QUERY_UPDATE, params);
    }

    @Override
    public void delete(Integer integer) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, integer);
        JDBCUtils.executeQuery(QUERY_DELETE, params);
    }
    public void deleteCouponsByCompany(Integer integer) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, integer);
        JDBCUtils.executeQuery(QUERY_DELETE_ALL_COUPONS_RELATED_TO_COMPANY, params);
    }

    @Override
    public boolean isTitleAlreadyExistsForASSpecificCompany(String title, Integer companyId) throws CouponsSystemException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, title);
        params.put(2, companyId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IS_TITLE_ALREADY_EXISTS_FOR_A_COMPANY, params);
        if (rows.isEmpty()) {
            result = false;
        } else {
            result = ResultsUtils.booleanFromRow((HashMap<String, Object>) (rows.get(0)));
        }
        return result;
    }

    @Override
    public List<Company> getAll() throws CouponsSystemException, SQLException, InterruptedException {
        List<Company> results = new ArrayList<>();

        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL);
        for (Object object : rows) {
            results.add(ResultsUtils.companyFromRow((HashMap<Company, Object>) object));
        }
        return results;
    }


    @Override
    public Company getSingle(Integer integer) throws CouponsSystemException {
        Company result = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, integer);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ONE, params);
        if (rows.isEmpty()) {
            result = null;
        } else {
            result = ResultsUtils.companyFromRow((HashMap<Company, Object>) (rows.get(0)));
        }
        return result;
    }
    public Company getCompanyDetailsAccordingToEmailAndPassword(String email,String password) throws CouponsSystemException {
        Company result = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_COMPANY_BY_EMAIL_PASSWORD, params);
        if (rows.isEmpty()) {
            result = null;
        } else {
            result = ResultsUtils.companyFromRow((HashMap<Company, Object>) (rows.get(0)));
        }
        return result;
    }

    @Override
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
    public boolean isExists(String email,String password) throws CouponsSystemException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IS_EXISTS_BY_EMAIL_PASSWORD, params);
        if (rows.isEmpty()) {
            result = false;
        } else {
            result = Boolean.valueOf(ResultsUtils.booleanFromRow((HashMap<String, Object>) (rows.get(0))));
        }
        return result;
    }
    public boolean isNameAlreadyExists(String name) throws CouponsSystemException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, name);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IS_NAME_ALREADY_EXISTS, params);
        if (rows.isEmpty()) {
            result = false;
        } else {
            result = Boolean.valueOf(ResultsUtils.booleanFromRow((HashMap<String, Object>) (rows.get(0))));
        }
        return result;
    }
    public boolean isEmailAlreadyExists(String email) throws CouponsSystemException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IS_EMAIL_ALREADY_EXISTS, params);
        if (rows.isEmpty()) {
            result = false;
        } else {
            result = Boolean.valueOf(ResultsUtils.booleanFromRow((HashMap<String, Object>) (rows.get(0))));
        }
        return result;
    }
}
