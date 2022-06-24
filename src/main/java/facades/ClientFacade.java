package facades;

import dao.*;
import exceptions.CouponsSystemException;

/**
 * created by yacob at 12/06/2022 - abstract for all type of facades (customer,company,admin)
 */
public abstract class ClientFacade {
    protected   CatagoriesDAO catagoriesDAO =null;
    protected  CompaniesDAO companiesDAO = null;
    protected  CustomersDAO customersDAO = null;
    protected  CouponsDAO couponsDAO = null;

    public ClientFacade() {
        catagoriesDAO = new CatagoriesDAOImpl();
        companiesDAO = new CompaniesDAOImpl();
        customersDAO = new CustomersDAOImpl();
        couponsDAO = new CouponsDAOImpl();
    }


    public abstract boolean login(String email, String password) throws CouponsSystemException;


}
