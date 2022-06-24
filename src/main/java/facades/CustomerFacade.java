package facades;

import beans.Catagory;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponsSystemException;
import exceptions.LayerMsg;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * created by yacob at 19/06/2022
 */
public class CustomerFacade extends ClientFacade {
    private int customerId;

    public CustomerFacade() {
        super();
    }

    @Override
    public boolean login(String email, String password) throws CouponsSystemException {
        if(customersDAO.isExists(email,password)) {
            this.customerId = customersDAO.getCustomerWithEmailAndPassword(email, password).getId();
        }else{
            throw new CouponsSystemException(LayerMsg.EMAIL_OR_PASSWORD_DOES_NOT_MATCH);
        }
        return customersDAO.isExists(email,password);
    }
    public void purcheseCouponForCustomer(Integer couponId) throws CouponsSystemException {
        Coupon coupon = couponsDAO.getSingle(couponId);
        if (coupon == null){
            throw new CouponsSystemException(LayerMsg.COUPON_ID_DOES_NOT_EXISTS);
        }
        if (couponsDAO.isCouponExistsForCustomer(couponId, this.customerId)){
            throw new CouponsSystemException(LayerMsg.CUSTOMER_ALREADY_PURCHESE_THIS_COUPON);
        }
        if (coupon.getAmount() == 0) {
            throw new CouponsSystemException(LayerMsg.NOT_ENOUGH_COUPONS);
        }
        Date today = Date.valueOf(LocalDate.now());
        if (today.after(coupon.getEndDate())){
            throw new CouponsSystemException(LayerMsg.COUPON_IS_EXPIRED);
        }
        couponsDAO.addCouponPurchase(this.customerId, coupon.getId());
        coupon.setAmount(coupon.getAmount() - 1);
        couponsDAO.update(coupon.getId(), coupon);
        //not allowed to buy same coupon (already exists)
        //amount =0
        //expiry date already over...
        //remember to withdraw one from amount
    }
    public List<Coupon> getAllCouponsForCustomer() throws CouponsSystemException {
        return customersDAO.getCouponsForACustomer(this.customerId);
    }
    public List<Coupon> getAllCouponsForCustomer(Catagory catagory) throws CouponsSystemException {
         List<Coupon> listOfCoupons =  getAllCouponsForCustomer();
         List<Coupon> results = new ArrayList<>();
        for (Coupon coupon :listOfCoupons) {
            if (coupon.getCatagory().ordinal() == catagory.ordinal()) {
                results.add(coupon);
            }
        }
        return results;
    }
    public List<Coupon> getAllCouponsForCustomerWithMaxAmount(double maxPrice) throws CouponsSystemException {
        List<Coupon> listOfCoupons =  getAllCouponsForCustomer();
        List<Coupon> results = new ArrayList<>();
        for (Coupon coupon :listOfCoupons) {
            if (coupon.getPrice() < maxPrice) {
                results.add(coupon);
            }
        }
        return results;
    }
    public Customer getCustomerDetails() throws CouponsSystemException {
        Customer cust = customersDAO.getSingle(customerId);
        cust.setCoupons(couponsDAO.getAllCouponsRelatedToCustomer(customerId));
        return cust;

    }




}
