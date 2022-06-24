package dao;

import beans.Catagory;
import beans.Coupon;
import exceptions.CouponsSystemException;

import java.sql.SQLException;
import java.util.List;

/**
 * created by yacob at 03/06/2022 - couponDAO interface
 */
public interface CouponsDAO extends DAO<Coupon,Integer> {
     void addCouponPurchase(int customerId, int couponId) throws CouponsSystemException;
     void deleteCouponPurchase(int customerId, int couponId) throws CouponsSystemException;
     void deleteCouponForCompany(Integer companyId) throws CouponsSystemException;
     void deleteSpecificCouponFromAllCustomers(Integer couponId) throws CouponsSystemException;
    List<Coupon> getAllCouponsRelatedToCustomer(int customerId) throws CouponsSystemException;
    List<Coupon> getAllCouponsForCompany(Integer companyId) throws CouponsSystemException;
    List<Coupon> getAllCouponsForCompanyAndCatagory(Integer companyId, Catagory catagory) throws CouponsSystemException;
    List<Coupon> getAllCouponsForCompanyUntilMaxValue(Integer companyId, double maxPrice) throws CouponsSystemException;
    boolean isCouponExistsForCustomer(Integer couponId, Integer customerId) throws CouponsSystemException;
    void deleteExpiredCoupons() throws SQLException, InterruptedException;
}
