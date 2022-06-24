package facades;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import dao.CustomersDAO;
import exceptions.CouponsSystemException;
import exceptions.LayerMsg;

import java.sql.SQLException;
import java.util.List;

/**
 * created by yacob at 12/06/2022 - class is AdminFacade for doing admin actions.
 */
public class AdminFacade extends ClientFacade{

    @Override
    public boolean login(String email, String password) {
        return (email == "admin@admin.com") && (password == "admin");
    }

    public AdminFacade() {
        super();
    }

    public void addCompany(Company company) throws CouponsSystemException {
        if (companiesDAO.isNameAlreadyExists(company.getName()))
        {
            throw new CouponsSystemException(LayerMsg.PROBLEM_WITH_COMPANY_NAME);
        }
        if (companiesDAO.isEmailAlreadyExists(company.getEmail())) {
            throw new CouponsSystemException(LayerMsg.PROBLEM_WITH_COMPANY_EMAIL);
        }
        companiesDAO.add(company);


    }
    public void updateCompany(Integer companyId, Company company) throws CouponsSystemException {
        Company comp =  companiesDAO.getSingle(companyId);
        if (comp == null) {
            throw new CouponsSystemException(LayerMsg.NOT_POSSIBLE_TO_UPDATE_COMPANY_ID);
        }
        if (!comp.getName().equals(company.getName())){
            throw new CouponsSystemException(LayerMsg.NOT_POSSIBLE_TO_UPDATE_COMPANY_NAME);
        }
        companiesDAO.update(companyId, company);

    }
    public void deleteCompany(Integer companyId) throws CouponsSystemException {
        if (!companiesDAO.isExists(companyId)){
            throw new CouponsSystemException(LayerMsg.COMPANY_DOES_NOT_EXISTS);
        }else {
            companiesDAO.deleteCouponsByCompany(companyId);
            couponsDAO.deleteCouponForCompany(companyId);
            companiesDAO.delete(companyId);
        }
    }
    public void getAllCompanies() throws CouponsSystemException, SQLException, InterruptedException {
        List<Company> listOfCompanies = companiesDAO.getAll();
        for (Company comp:listOfCompanies) {
            comp.setCoupons(couponsDAO.getAllCouponsForCompany(comp.getId()));
        }
        listOfCompanies.forEach(System.out::println);
    }
    public void getASpecifcCompany(Integer companyId) throws CouponsSystemException, SQLException, InterruptedException {
        Company comp = companiesDAO.getSingle(companyId);
        if (comp != null) {
            comp.setCoupons(couponsDAO.getAllCouponsForCompany(comp.getId()));
            System.out.println(comp);
        }else{
            throw new CouponsSystemException(LayerMsg.COMPANY_DOES_NOT_EXISTS);
        }

    }
    public void addCustomer(Customer customer) throws CouponsSystemException {
        if (customersDAO.isExistsByEmail(customer.getEmail()))
        {
            throw new CouponsSystemException(LayerMsg.EMAIL_FOR_CUSTOMER_ALREADY_EXISTS);
        }else {
            customersDAO.add(customer);
        }
    }
    public void updateACustomer(Customer customer) throws CouponsSystemException {
        if (customersDAO.isExists(customer.getId())) {
            customersDAO.update(customer.getId(), customer);
        }else {
            //TODO if email somehow exsits in other user...cannot update
            throw new CouponsSystemException(LayerMsg.CUSTOMER_ID_CANNOT_BE_CHANGED);
        }
    }
    public void deleteACustomer(Integer customerId) throws CouponsSystemException {
        customersDAO.deleteCouponsForCustomer(customerId);
        customersDAO.delete(customerId);

    }
    public void getAllCustomers() throws CouponsSystemException, SQLException, InterruptedException {
        List<Customer> listOfCustomers = customersDAO.getAll();
        for (Customer cust:listOfCustomers) {
            cust.setCoupons(couponsDAO.getAllCouponsRelatedToCustomer(cust.getId()));
        }
        listOfCustomers.forEach(System.out::println);
    }
    public void getASpecifcCustomer(Integer customerId) throws CouponsSystemException, SQLException, InterruptedException {
        if (!customersDAO.isExists(customerId)) {
            throw new CouponsSystemException(LayerMsg.CUSTOMER_DOES_NOT_EXISTS);
        }
        Customer cust = customersDAO.getSingle(customerId);
        cust.setCoupons(couponsDAO.getAllCouponsRelatedToCustomer(cust.getId()));
        System.out.println(cust);


    }


}
