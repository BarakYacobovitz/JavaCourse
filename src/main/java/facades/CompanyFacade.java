package facades;

import beans.Catagory;
import beans.Company;
import beans.Coupon;
import dao.CompaniesDAO;
import exceptions.CouponsSystemException;
import exceptions.LayerMsg;

import java.sql.SQLException;
import java.util.List;

/**
 * created by yacob at 18/06/2022 - company facade to handle all actions realted to company
 */
public class CompanyFacade extends ClientFacade {
    private int companyId = 0;
    public CompanyFacade() {
        super();
    }
    public CompanyFacade(CompanyFacade companyFacade){
        this.companyId = companyFacade.companyId;
    }
    @Override
    public boolean login(String email, String password) throws CouponsSystemException {
        if (companiesDAO.isExists(email,password)) {
            this.companyId = companiesDAO.getCompanyDetailsAccordingToEmailAndPassword(email, password).getId();
        }else
            throw new CouponsSystemException(LayerMsg.EMAIL_OR_PASSWORD_DOES_NOT_MATCH);
        return companiesDAO.isExists(email,password);

    }
    public void addCoupon(Coupon coupon) throws CouponsSystemException {
        coupon.setCompanyID(companyId);
        if (companiesDAO.isTitleAlreadyExistsForASSpecificCompany(coupon.getTitle(), companyId)){
            throw new CouponsSystemException(LayerMsg.TITLE_ALREADY_EXISTS);
        }
        couponsDAO.add(coupon);
    }
    public void updateCouponPerCompany(int couponId, Coupon coupon) throws CouponsSystemException {
        Coupon couponForUdpate = couponsDAO.getSingle(couponId);
        if (couponForUdpate == null){
            throw new CouponsSystemException(LayerMsg.COUPON_ID_DOES_NOT_EXISTS);
        }
        if (couponForUdpate.getCompanyID() != companyId){
            throw new CouponsSystemException(LayerMsg.NOT_ABLE_TO_CHANGE_COMPANY_ID);
        }
        if (companyId == 0 ) {
            throw new CouponsSystemException(LayerMsg.PLEASE_LOG_IN_FOR_USING);
        }
        couponsDAO.update(couponId,coupon);
    }
    public void deleteCoupon(Integer couponId) throws CouponsSystemException {
        couponsDAO.deleteSpecificCouponFromAllCustomers(couponId);
        couponsDAO.delete(couponId);
    }
    public List<Coupon> getListOfAllCouponsRelatedToCompany() throws CouponsSystemException {
        return couponsDAO.getAllCouponsForCompany(this.companyId);
    }
    public List<Coupon> getListOfAllCouponsRelatedToCompanyFromSpecifcCatagory(Catagory cat) throws CouponsSystemException {
        return couponsDAO.getAllCouponsForCompanyAndCatagory(this.companyId,cat);
    }
    public List<Coupon> getListOfAllCouponsWithMaxPrice(double maxPrice) throws CouponsSystemException {
        return couponsDAO.getAllCouponsForCompanyUntilMaxValue(this.companyId, maxPrice);
    }
    public Company getCompanyDetails() throws CouponsSystemException {
        Company comp = companiesDAO.getSingle(this.companyId);
        comp.setCoupons(couponsDAO.getAllCouponsForCompany(comp.getId()));
        return comp;
    }


}
