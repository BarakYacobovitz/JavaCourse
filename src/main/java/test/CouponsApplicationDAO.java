package test;

import beans.Catagory;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import dao.*;
import db.ConnectionPool;
import db.DatabaseManager;
import exceptions.CouponsSystemException;
import facades.AdminFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;
import login.ClientType;
import login.LoginManager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by kobis on 08 May, 2022
 */
public class CouponsApplicationDAO {


    private static final CatagoriesDAO catagoriesDAO = new CatagoriesDAOImpl();
    private static final CompaniesDAO companiesDAO = new CompaniesDAOImpl();
    private static final CustomersDAO customersDAO = new CustomersDAOImpl();
    private static final CouponsDAO couponsDAO = new CouponsDAOImpl();


    public static void main(String[] args) throws CouponsSystemException, SQLException, InterruptedException {
        System.out.println("START");
        DatabaseManager.getInstance().dropCreateStrategy();
        System.out.println("---------------- Get All Catagories ---------------");
        catagoriesDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- Insert All Catagories ---------------");
        catagoriesDAO.add(Catagory.Food.toString());
        catagoriesDAO.add(Catagory.Electricty.toString());
        catagoriesDAO.add(Catagory.Vacation.toString());
        catagoriesDAO.add(Catagory.Restaurant.toString());
        System.out.println("---------------- Print All Catagories ---------------");
        catagoriesDAO.getAll().forEach(System.out::println);
        /*
        System.out.println("---------------- update a Catagory ---------------");
        catagoriesDAO.update(3,"Travel");
        catagoriesDAO.update(1,"Home Decortion");
        System.out.println("---------------- Print All Catagories ---------------");
        catagoriesDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- delete a Catagory ---------------");
        catagoriesDAO.delete(4);
        System.out.println("---------------- Print All Catagories ---------------");
        catagoriesDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- get s specifc  Catagory and print it---------------");
        System.out.println(catagoriesDAO.getSingle(2));
        System.out.println("---------------- is exists by id/Catagory---------------");
        System.out.println(catagoriesDAO.isExists(1));
        System.out.println(catagoriesDAO.isExists(2));
        System.out.println(catagoriesDAO.isExists(80));
        System.out.println(catagoriesDAO.isExists("Travel"));
        System.out.println(catagoriesDAO.isExists("Traaaael"));*/
        System.out.println("---------------- Moving on to Companies ---------------");
        System.out.println("---------------- Print All Companies ---------------");
        companiesDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- Insert Companies ---------------");
        companiesDAO.add(new Company("aaaa","bbbb","cccc"));
        companiesDAO.add(new Company("dddd","eeee","ffff"));
        companiesDAO.add(new Company("gggg","hhhh","iiii"));
        companiesDAO.add(new Company("jjjj","kkkk","llll"));
        companiesDAO.add(new Company("mmmm","nnnn","oooo"));
        System.out.println("---------------- Print All Companies ---------------");
        companiesDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- update Companies ---------------");
        companiesDAO.update(1,new Company("pppp","qqqq","cccc"));
        System.out.println("---------------- Print All Companies ---------------");
        companiesDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- delete Companies ---------------");
        companiesDAO.delete(5);
        System.out.println("---------------- Print All Companies ---------------");
        companiesDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- get s specifc  Companies and print it---------------");
        System.out.println(companiesDAO.getSingle(2));
        System.out.println("---------------- is exists by id/Company---------------");
        System.out.println(companiesDAO.isExists(1));
        System.out.println(companiesDAO.isExists(18));
        System.out.println(companiesDAO.isExists("hhhh","iiii"));
        System.out.println(companiesDAO.isExists("hhhh","ii"));
        System.out.println("---------------- Moving on to Customers ---------------");
        System.out.println("---------------- Print All Customers ---------------");
        customersDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- Insert Customers ---------------");
        for (Customer customer : Arrays.asList(new Customer("pppp", "qqqq", "rrrr", "ssss"), new Customer("tttt", "uuuu", "vvvv","wwww"),
                new Customer("xxxx", "yyyyy", "zzzz","11111"), new Customer("2222", "3333", "4444","5555"), new Customer("6666", "7777", "8888","9999"))) {
            customersDAO.add(customer);
        }
        System.out.println("---------------- Print All Customers ---------------");
        customersDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- update Customers ---------------");
        customersDAO.update(3, new Customer("Yossi","Osi","mail@laMail.com","123456"));

        System.out.println("---------------- Print All Customers ---------------");
        customersDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- delete Customers ---------------");
        customersDAO.delete(5);
        System.out.println("---------------- Print All Customers ---------------");
        companiesDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- get s specifc Customers and print it---------------");
        System.out.println(companiesDAO.getSingle(2));
        System.out.println("---------------- is exists by id/Customers---------------");
        System.out.println(companiesDAO.isExists(1));
        System.out.println(companiesDAO.isExists(18));
        System.out.println(companiesDAO.isExists("kkkk","llll"));
        System.out.println(companiesDAO.isExists("kkkk","ll"));
        System.out.println("---------------- Moving on to Coupons ---------------");
        System.out.println("---------------- Print All Coupons ---------------");
        couponsDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- Insert Coupons ---------------");
        couponsDAO.add((new Coupon(1,Catagory.Food,"title","desc",
                Date.valueOf("2022-01-01"), Date.valueOf("2023-01-01"),5,25.6,"path")));
        couponsDAO.add((new Coupon(2,Catagory.Food,"te","desc",
                Date.valueOf("2022-01-10"), Date.valueOf("2023-01-10"),8,12.4,"path")));
        couponsDAO.add((new Coupon(1,Catagory.Electricty,"aaaa","descssa",
                Date.valueOf("2022-01-01"), Date.valueOf("2023-01-01"),5,25.6,"path")));
        couponsDAO.add((new Coupon(3,Catagory.Food,"aavc","dasesc",
                Date.valueOf("2020-01-10"), Date.valueOf("2021-01-10"),8,12.4,"path")));
        System.out.println("---------------- Print All Coupons ---------------");
        couponsDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- Update Coupons ---------------");
        couponsDAO.update(1,(new Coupon(4,Catagory.Food,"aavc","dasesc",
                Date.valueOf("2020-01-10"), Date.valueOf("2021-01-10"),8,12.4,"path")));
        System.out.println("---------------- Print All Coupons ---------------");
        couponsDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- delete  a Coupon ---------------");
        couponsDAO.delete(4);
        System.out.println("---------------- Print All Coupons ---------------");
        couponsDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- get s specifc Coupon and print it---------------");
        System.out.println(couponsDAO.getSingle(2));
        System.out.println("---------------- is exists by id---------------");
        System.out.println(couponsDAO.isExists(1));
        System.out.println(couponsDAO.isExists(18));
        System.out.println("---------------- add customer vs Coupons ---------------");
        System.out.println("---------------- get all coupons related to customer---------------");
        System.out.println(couponsDAO.getAllCouponsRelatedToCustomer(3));
        System.out.println("---------------- insert coupons to the customer---------------");
        couponsDAO.addCouponPurchase(2,1);
        couponsDAO.addCouponPurchase(2,2);
        couponsDAO.addCouponPurchase(3,2);
        couponsDAO.addCouponPurchase(4,3);
        System.out.println("---------------- Print All Coupons ---------------");
        couponsDAO.getAll().forEach(System.out::println);
        System.out.println("---------------- insert coupons to a customer---------------");
        couponsDAO.addCouponPurchase(1,1);
        System.out.println("---------------- get all coupons related to customer---------------");
        System.out.println(couponsDAO.getAllCouponsRelatedToCustomer(1));

        System.out.println("---------------- get all coupons related to customer---------------");
        System.out.println(couponsDAO.getAllCouponsRelatedToCustomer(2));

        System.out.println("###########TESTING FOR FACADE#####");

        try{
            AdminFacade admin = new AdminFacade();
            System.out.println("###########ADMIN TESTING#####");
            System.out.println("###########login admin#####");
            System.out.println(admin.login("admin@admin.com","admin"));
            System.out.println(admin.login("admin12344@admin.com","admin"));
            System.out.println("###########Insert company#####");
            admin.addCompany(new Company("nbnbb","aaafdsfas","fffa"));
          //  admin.addCompany(new Company("nbnbb","aaafdsfas","fffa"));
            System.out.println("###########Update company#####");
           // admin.updateCompany(1,new Company(2,"pppp","qqqqrrrr","cccc"));
           // admin.updateCompany(1,new Company(1,"pppp","qqqqrrr222","cccc"));
            System.out.println("###########Delete company#####");
           // admin.deleteCompany(4);
            System.out.println("###########Get ALL companies#####");
            admin.getAllCompanies();
            System.out.println("###########Get specifc company#####");
            admin.getASpecifcCompany(4);
            System.out.println("###########Add an existing customer#####");
            //admin.addCustomer(new Customer("xxxx", "yyyyy", "mail@laMail.com","11111"));
            admin.addCustomer(new Customer("xxxx", "yyyyy", "mailmailmail@laMail.com","11111"));
            admin.updateACustomer(new Customer(1,"Kobi", "Shasha", "mail@laMail.com","11111"));
            //admin.updateACustomer(new Customer(11,"Kobi", "Shasha", "mail@laMail.com","11111"));
            System.out.println("###########delete  customer#####");
            //admin.deleteACustomer(1);
            System.out.println("###########get all  customers#####");
            admin.getAllCustomers();
            System.out.println("###########get specific  customers#####");
            admin.getASpecifcCustomer(1);

            System.out.println("###########COMPANY TESTING#####");
            CompanyFacade comp = new CompanyFacade();
            System.out.println("###########login company#####");
            System.out.println(comp.login("hhhh","iiii"));
            //System.out.println(comp.login("hh222hh","iiii"));
            System.out.println("###########add coupon #####");
            comp.addCoupon(new Coupon(Catagory.Electricty,"aavc","desc",
                    Date.valueOf("2022-01-01"), Date.valueOf("2023-01-01"),5,28,"path"));
            couponsDAO.getAll().forEach(System.out::println);
            comp.addCoupon(new Coupon(Catagory.Food,"walla","2222dsaa",
                    Date.valueOf("2023-01-01"), Date.valueOf("2025-01-01"),5,50, "path"));
            couponsDAO.getAll().forEach(System.out::println);
            System.out.println("###########update coupon #####");
            //comp.updateCouponPerCompany(new Coupon(Catagory.Electricty,"aavc","desc",
            //        Date.valueOf("2022-01-31"), Date.valueOf("2023-01-01"),5,29,"path"));
            couponsDAO.getAll().forEach(System.out::println);
            //comp.updateCouponPerCompany(new Coupon(7,10,Catagory.Electricty,"aavc","desc",
            //        Date.valueOf("2022-01-31"), Date.valueOf("2023-01-01"),5,35,"path"));
            //couponsDAO.getAll().forEach(System.out::println);
            System.out.println("###########for test purpose add coupon purchese for the new coupon #####");
            couponsDAO.addCouponPurchase(4,5);
            System.out.println("###########delete coupon #####");
            //comp.deleteCoupon(5);
            System.out.println("###########get all coupons #####");
            comp.getListOfAllCouponsRelatedToCompany().forEach(System.out::println);
            System.out.println("###########get all coupons by catagory $1#####");
            comp.getListOfAllCouponsRelatedToCompanyFromSpecifcCatagory(Catagory.Electricty).forEach(System.out::println);
            System.out.println("###########get all coupons by catagory $2#####");
            comp.getListOfAllCouponsRelatedToCompanyFromSpecifcCatagory(Catagory.Food).forEach(System.out::println);
            System.out.println("###########get all coupons by MAX VALUE $3#####");
            comp.getListOfAllCouponsWithMaxPrice(25).forEach(System.out::println);
            System.out.println("###########get all company details#####");
            System.out.println(comp.getCompanyDetails());
            System.out.println("###########CUSTOMER TESTING#####");
            CustomerFacade cust = new CustomerFacade();
            System.out.println("###########login company#####");
            System.out.println(cust.login("hhhh","iiii"));
            System.out.println(cust.login("4444","5555"));
            System.out.println("###########customer purchese of coupons#####");
            cust.purcheseCouponForCustomer(6);
            //cust.purcheseCouponForCustomer(1);
            System.out.println("###########list of all coupons purchase#####");
            cust.getAllCouponsForCustomer().forEach(System.out::println);
            System.out.println("###########list of all coupons purchase by catagory#####");
            cust.getAllCouponsForCustomer(Catagory.Electricty).forEach(System.out::println);
            System.out.println("###########list of all coupons purchase max value#####");
            cust.getAllCouponsForCustomerWithMaxAmount(200).forEach(System.out::println);
            System.out.println("###########customer info#####");
            System.out.println(cust.getCustomerDetails());
            System.out.println("###########login info#####");
            LoginManager.getInstance().login("hhhh","iiii", ClientType.Company);

        } catch(Exception ex){
            throw ex;

        }
        ConnectionPool.getInstance().closeAllConnections();
        System.out.println("END");


    }
}
