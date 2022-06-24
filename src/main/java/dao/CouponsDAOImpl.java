package dao;

import beans.Catagory;
import beans.Coupon;
import db.JDBCUtils;
import db.ResultsUtils;
import exceptions.CouponsSystemException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by yacob at 03/06/2022 - this class implements couponsDAO
 */
public class CouponsDAOImpl implements CouponsDAO{
    private static final String QUERY_INSERT = "INSERT INTO `coupons_database`.`coupons` (`company_id`, `catagory_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?,?,?);\n";
    private static final String QUERY_UPDATE = "UPDATE `coupons_database`.`coupons` SET `company_id` = ?, `catagory_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?);\n";
    private static final String QUERY_DELETE = "DELETE FROM `coupons_database`.`coupons` WHERE (`id` = ?);\n";
    private static final String QUERY_DELETE_COUPON_FOR_COMPANY = "DELETE FROM `coupons_database`.`coupons` WHERE (`company_id` = ?);\n";
    private static final String QUERY_GET_ALL = "SELECT * FROM coupons_database.coupons;\n";
    private static final String QUERY_GET_ALL_COUPON_FOR_COMPANY = "SELECT * FROM coupons_database.coupons WHERE (`company_id` = ?);\n";
    private static final String QUERY_GET_ALL_COUPON_FOR_COMPANY_AND_CATAGORY = "SELECT * FROM coupons_database.coupons where company_id = ? and catagory_id = ?;\n";
    private static final String QUERY_GET_ONE = "SELECT * FROM coupons_database.coupons WHERE (`id` = ?);\n";
    private static final String QUERY_IS_EXISTS_BY_ID = "select exists( SELECT * FROM coupons_database.coupons WHERE (`id` = ?) ) as res;";
    private static final String QUERY_ADD_COUPON_PURCHASE = "INSERT INTO `coupons_database`.`customer_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?);\n";
    private static final String QUERY_DELETE_COUPON_PURCHASE = "DELETE FROM `coupons_database`.`customer_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?);\n";
    private static final String QUERY_DELETE_COUPON_PURCHASE_BY_COUPON_ID = "DELETE FROM `coupons_database`.`customer_vs_coupons` WHERE (`coupon_id` = ?);\n";
    private static final String QUERY_GET_ALL_COUPONS_PER_CUSTOMER = "select * from coupons_database.coupons where id in(\n" +
            "select coupon_id from coupons_database.customer_vs_coupons where customer_id = ? )";
    private static final String QUERY_EVERYTHING_BUT_MAX_PRICE_FOR_COMPANY ="select * from coupons_database.coupons where company_id = ? and price < ?; \n";
    private static final String QUERY_IS_EXISTS_COUPON_TO_CUSTOMER = "select exists(\n" +
            "SELECT * FROM coupons_database.customer_vs_coupons where coupon_id = ? and customer_id =?) as res";
    private static final String QUERY_DELETE_ALL_EXPIRED_COUPON_FROM_CUSTOMER_VS_COUPONS = "delete from coupons_database.customer_vs_coupons where coupon_id in( select id from coupons_database.coupons where end_date < now())";
    private static final String QUERY_DELETE_ALL_EXPIRED_COUPON = "delete from coupons_database.coupons where end_date < now()";

    @Override
    public void add(Coupon coupon) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyID());
        params.put(2, coupon.getCatagory().ordinal() + 1);
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescripition());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        JDBCUtils.executeQuery(QUERY_INSERT, params);

    }

    @Override
    public void update(Integer integer, Coupon coupon) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyID());
        params.put(2, coupon.getCatagory().ordinal() + 1);
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescripition());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        params.put(10, integer);
        JDBCUtils.executeQuery(QUERY_UPDATE, params);

    }

    @Override
    public void delete(Integer integer) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, integer);
        JDBCUtils.executeQuery(QUERY_DELETE, params);
    }
    public void deleteCouponForCompany(Integer companyId) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        JDBCUtils.executeQuery(QUERY_DELETE_COUPON_FOR_COMPANY, params);
    }



    @Override
    public List<Coupon> getAll() throws CouponsSystemException, SQLException, InterruptedException {
        List<Coupon> results = new ArrayList<>();

        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL);
        for (Object object : rows) {
            results.add(ResultsUtils.couponFromRow((HashMap<Coupon,Object>) object));
        }
        return results;
    }

    @Override
    public Coupon getSingle(Integer integer) throws CouponsSystemException {
        Coupon result = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, integer);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ONE, params);
        if (rows.isEmpty()) {
            result = null;
        } else {
            result = ResultsUtils.couponFromRow((HashMap<Coupon, Object>) (rows.get(0)));
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

    @Override
    public void addCouponPurchase(int customerId, int couponId) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, couponId);
        JDBCUtils.executeQuery(QUERY_ADD_COUPON_PURCHASE, params);
    }

    @Override
    public void deleteCouponPurchase(int customerId, int couponId) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, couponId);
        JDBCUtils.executeQuery(QUERY_DELETE_COUPON_PURCHASE, params);
    }
    @Override
    public void deleteSpecificCouponFromAllCustomers(Integer couponId) throws CouponsSystemException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        JDBCUtils.executeQuery(QUERY_DELETE_COUPON_PURCHASE_BY_COUPON_ID, params);
    }

    @Override
    public List<Coupon> getAllCouponsRelatedToCustomer(int customerId) throws CouponsSystemException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL_COUPONS_PER_CUSTOMER,params);
        for (Object object : rows) {
            results.add(ResultsUtils.couponFromRow((HashMap<Coupon,Object>) object));
        }
        return results;
    }

    @Override
    public List<Coupon> getAllCouponsForCompany(Integer companyId) throws CouponsSystemException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL_COUPON_FOR_COMPANY, params);
        for (Object object : rows) {
            results.add(ResultsUtils.couponFromRow((HashMap<Coupon,Object>) object));
        }
        return results;
    }

    @Override
    public List<Coupon> getAllCouponsForCompanyAndCatagory(Integer companyId, Catagory catagory) throws CouponsSystemException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, catagory.ordinal() + 1);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL_COUPON_FOR_COMPANY_AND_CATAGORY, params);
        for (Object object : rows) {
            results.add(ResultsUtils.couponFromRow((HashMap<Coupon,Object>) object));
        }
        return results;
    }

    @Override
    public List<Coupon> getAllCouponsForCompanyUntilMaxValue(Integer companyId, double maxPrice) throws CouponsSystemException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, maxPrice);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_EVERYTHING_BUT_MAX_PRICE_FOR_COMPANY, params);
        for (Object object : rows) {
            results.add(ResultsUtils.couponFromRow((HashMap<Coupon,Object>) object));
        }
        return results;
    }

    @Override
    public boolean isCouponExistsForCustomer(Integer couponId, Integer customerId) throws CouponsSystemException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        params.put(2, customerId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IS_EXISTS_COUPON_TO_CUSTOMER, params);
        if (rows.isEmpty()) {
            result = false;
        } else {
            result = Boolean.valueOf(ResultsUtils.booleanFromRow((HashMap<String, Object>) (rows.get(0))));
        }
        return result;
    }

    @Override
    public void deleteExpiredCoupons() throws SQLException, InterruptedException {
        JDBCUtils.executeQuery(QUERY_DELETE_ALL_EXPIRED_COUPON_FROM_CUSTOMER_VS_COUPONS);
        JDBCUtils.executeQuery(QUERY_DELETE_ALL_EXPIRED_COUPON);
    }
}
