package dao;

import beans.Company;
import exceptions.CouponsSystemException;

/**
 * created by yacob at 03/06/2022 - interface for companyDAO
 */
public interface CompaniesDAO  extends DAO<Company,Integer>{
    boolean isExists(String email,String password) throws CouponsSystemException;

    boolean isNameAlreadyExists(String name) throws CouponsSystemException;
    boolean isEmailAlreadyExists(String email) throws CouponsSystemException;
    void deleteCouponsByCompany(Integer integer) throws CouponsSystemException;
    boolean isTitleAlreadyExistsForASSpecificCompany(String title, Integer companyId) throws CouponsSystemException;
    Company getCompanyDetailsAccordingToEmailAndPassword(String email, String password) throws CouponsSystemException;

}

