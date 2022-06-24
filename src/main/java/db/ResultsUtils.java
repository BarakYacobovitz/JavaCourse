package db;

import beans.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.HashMap;

/**
 * Created by kobis on 15 May, 2022 - results util to catagory,company,customer,coupon
 */
public class ResultsUtils {

    public static boolean booleanFromRow(HashMap<String, Object> map) {
        long val = (long) map.get("res");
        return val==1;
    }


    public static String catagoryFromRow(HashMap<String, Object> map) {
        String name = (String) map.get("name");
        return name;
    }
    public static Company companyFromRow(HashMap<Company,Object> map){
        int id = (int) map.get("id");
        String name = (String) map.get("name");
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        return new Company(id, name, email, password);

    }
    public static Customer customerFromRow(HashMap<Customer,Object> map){
        int id = (int) map.get("id");
        String firstName = (String) map.get("first_name");
        String lastName = (String) map.get("last_name");
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        return new Customer(id, firstName,lastName,email,password);

    }
    public static Coupon couponFromRow(HashMap<Coupon,Object> map){
        int id = (int) map.get("id");
        int companyId = (int) map.get("company_id");
        int catagoryId = (int) map.get("catagory_id");
        String title = (String) map.get("title");
        String desc = (String) map.get("description");
        Date startDate = (Date) map.get("start_date");
        Date endDate = (Date) map.get("end_date");
        int amount = (int) map.get("amount");
        Double price = (double) map.get("price");
        String image = (String) map.get("image");
        return new Coupon(id,companyId,Catagory.values()[catagoryId - 1],title,desc,startDate,endDate,amount,price,image);

    }




}
