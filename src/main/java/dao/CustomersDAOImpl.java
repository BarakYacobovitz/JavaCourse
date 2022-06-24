package dao;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import db.JDBCUtils;
import db.ResultsUtils;
import exceptions.CouponsSystemException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by yacob at 03/06/2022 - this class implements customerDAO
 */
public class CustomersDAOImpl implements CustomersDAO {
    private static final String QUERY_INSERT = "INSERT INTO `coupons_database`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?);\n";
    private static final String QUERY_UPDATE = "UPDATE `coupons_database`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);\n";
    private static final String QUERY_DELETE = "DELETE FROM `coupons_database`.`customers` WHERE (`id` = ?);\n";
    private static final String QUERY_GET_ALL = "SELECT * FROM coupons_database.customers;\n";
    private static final String QUERY_GET_ONE = "SELECT * FROM coupons_database.customers WHERE (`id` = ?);\n";
    private static final String QUERY_GET_CUSTOMER_BY_EMAIL_PASSWORD  = "select * FROM coupons_database.customers where (email = ? and password = ?);";
    private static final String QUERY_IS_EXISTS_BY_ID = "select exists(select * FROM coupons_database.customers WHERE (`id` = ?)) as res;\n";
    private static final String QUERY_IS_EXISTS_BY_EMAIL_PASSWORD = "select exists(select * FROM coupons_database.customers WHERE (`email` = ? and `password` = ? )) as res;\n";
    private static final String QUERY_IS_EXISTS_BY_EMAIL = "select exists(select * FROM coupons_database.customers WHERE (`email` = ? )) as res;\n";
    private static final String QUERY_GET_A_CUSTOMER_COUPONS = "select * from coupons_database.coupons where id in(\n" +
            "SELECT coupon_id FROM coupons_database.customer_vs_coupons WHERE (`customer_id` = ?))";
    private static final String QUERY_DELETE_COUPONS_FROM_CUSTOMER = "DELETE FROM `coupons_database`.`customer_vs_coupons` WHERE (`customer_id` = ?);\n";
    @Override
    public void add(Customer customer) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        JDBCUtils.executeQuery(QUERY_INSERT, params);

    }

    @Override
    public void update(Integer integer, Customer customer) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, integer);
        JDBCUtils.executeQuery(QUERY_UPDATE, params);
    }

    @Override
    public void delete(Integer integer) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, integer);
        JDBCUtils.executeQuery(QUERY_DELETE, params);
    }

    @Override
    public List<Customer> getAll() throws CouponsSystemException, SQLException, InterruptedException {
        List<Customer> results = new ArrayList<>();

        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL);
        for (Object object : rows) {
            results.add(ResultsUtils.customerFromRow((HashMap<Customer, Object>) object));
        }
        return results;
    }

    @Override
    public Customer getSingle(Integer integer) throws CouponsSystemException {
        Customer result = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, integer);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ONE, params);
        if (rows.isEmpty()) {
            result = null;
        } else {
            result = ResultsUtils.customerFromRow((HashMap<Customer, Object>) (rows.get(0)));
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

    @Override
    public boolean isExistsByEmail(String email) throws CouponsSystemException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IS_EXISTS_BY_EMAIL, params);
        if (rows.isEmpty()) {
            result = false;
        } else {
            result = Boolean.valueOf(ResultsUtils.booleanFromRow((HashMap<String, Object>) (rows.get(0))));
        }
        return result;
    }
    public List<Coupon> getCouponsForACustomer(Integer customerId) throws CouponsSystemException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_A_CUSTOMER_COUPONS, params);
        for (Object object : rows) {
            results.add(ResultsUtils.couponFromRow((HashMap<Coupon, Object>) object));
        }
        return results;
    }

    @Override
    public void deleteCouponsForCustomer(Integer customerID) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        JDBCUtils.executeQuery(QUERY_DELETE_COUPONS_FROM_CUSTOMER, params);
    }

    @Override
    public Customer getCustomerWithEmailAndPassword(String email, String password) throws CouponsSystemException {
        Customer result = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_CUSTOMER_BY_EMAIL_PASSWORD, params);
        if (rows.isEmpty()) {
            result = null;
        } else {
            result = ResultsUtils.customerFromRow((HashMap<Customer, Object>) (rows.get(0)));
        }
        return result;
    }
}

