package dao;

import exceptions.CouponsSystemException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by kobis on 15 May, 2022 - generics DAO interface for all DAOS
 */
public interface DAO<T, ID> {

    void add(T t) throws CouponsSystemException;
    void update(ID id, T t) throws CouponsSystemException;
    void delete(ID id) throws CouponsSystemException;

    List<T> getAll() throws CouponsSystemException, SQLException, InterruptedException;
    T getSingle(ID id) throws CouponsSystemException;
    boolean isExists(ID id) throws CouponsSystemException;

}
