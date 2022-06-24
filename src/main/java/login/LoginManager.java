package login;

import exceptions.CouponsSystemException;
import exceptions.LayerMsg;
import facades.AdminFacade;
import facades.ClientFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;

/**
 * created by yacob at 19/06/2022
 */
public class LoginManager {

    private static LoginManager instance = null;
    private LoginManager(){
    }
    public static LoginManager getInstance(){
        if (instance == null){
            synchronized (LoginManager.class){
                if (instance == null) {
                    instance = new LoginManager();
                }

            }
        }
        return instance;
    }
    public ClientFacade login(String email, String password,ClientType clientType) throws CouponsSystemException {

        if (clientType == ClientType.Admin){
            if(new AdminFacade().login(email,password)) {
                return new AdminFacade();
            }else{
                throw new CouponsSystemException(LayerMsg.EMAIL_OR_PASSWORD_DOES_NOT_MATCH);
            }
        }
        if (clientType == ClientType.Company){
            CompanyFacade compFacade = new CompanyFacade();
            if(compFacade.login(email,password)) {
                return compFacade;
            }
        }
        if (clientType == ClientType.Customer) {
            CustomerFacade custFacade = new CustomerFacade();
            if(custFacade.login(email,password)) {
                return custFacade;
            }
        }

        return null;
    }

}
