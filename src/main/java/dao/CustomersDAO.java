package dao;

import beans.Coupon;
import beans.Customer;
import exceptions.CouponsSystemException;

import java.util.List;

/**
 * created by yacob at 03/06/2022 interface for customerDAO
 */
public interface CustomersDAO extends DAO<Customer,Integer> {
    boolean isExists(String email,String password) throws CouponsSystemException;
    boolean isExistsByEmail(String email) throws CouponsSystemException;
    List<Coupon> getCouponsForACustomer(Integer customerId) throws CouponsSystemException;
    void deleteCouponsForCustomer(Integer customerID) throws CouponsSystemException;
    Customer getCustomerWithEmailAndPassword(String email, String password) throws CouponsSystemException;

}
