package exceptions;

/**
 * Created by kobis on 15 May, 2022
 */
public enum LayerMsg {

    CONNECTION_POOL("Something went wrong inside Connection Pool"),
    JDBC_UTILS("Something went wrong inside JDBC Utils"),
    DAO("Something wend wrong inside DAO Layer"),
    PROBLEM_WITH_COMPANY_NAME("Company name is already exists, not allowed to create with the same name"),
    PROBLEM_WITH_COMPANY_EMAIL("Company email is already exists, not allowed to create with the same email"),
    NOT_POSSIBLE_TO_UPDATE_COMPANY_ID("Can't update company id"),
    NOT_POSSIBLE_TO_UPDATE_COMPANY_NAME("Can't change company name"),
    COMPANY_DOES_NOT_EXISTS("Company does not exists in the DB"),
    EMAIL_FOR_CUSTOMER_ALREADY_EXISTS("Email for this customer is already exists"),
    CUSTOMER_ID_CANNOT_BE_CHANGED("Customer id can't be updated "),
    CUSTOMER_DOES_NOT_EXISTS("Customer does not exists"),
    TITLE_ALREADY_EXISTS("Title for Coupon for that Company already exists"),
    NOT_ABLE_TO_CHANGE_COMPANY_ID("The coupon is not realted to your company"),
    PLEASE_LOG_IN_FOR_USING("please log in - we need to know which company you are from"),
    CUSTOMER_ALREADY_PURCHESE_THIS_COUPON("customer already purchese this coupon"),
    NOT_ENOUGH_COUPONS("not enough coupons for this purchese"),
    COUPON_IS_EXPIRED("coupon is expired"),
    COUPON_ID_DOES_NOT_EXISTS("coupon id does not exists"),
    EMAIL_OR_PASSWORD_DOES_NOT_MATCH("email or password does not match");


    private String msg;
    LayerMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
