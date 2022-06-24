package dao;

import beans.Catagory;
import exceptions.CouponsSystemException;

/**
 * created by yacob at 29/05/2022 CatagoryDAO
 */
public interface CatagoriesDAO extends DAO<String,Integer> {
    boolean isExists(String catagory) throws CouponsSystemException;

}
