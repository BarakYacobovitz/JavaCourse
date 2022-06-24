package test;

import beans.Catagory;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import db.DatabaseManager;
import exceptions.CouponsSystemException;
import facades.AdminFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;
import jobs.CouponExpirationDailyJob;
import login.ClientType;
import login.LoginManager;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * created by yacob at 21/06/2022
 */
public class Test {

    public static void testAll() throws CouponsSystemException, SQLException, InterruptedException {
        CouponExpirationDailyJob job = null;
        Thread t1 = null;
        try {
            //customer for testing purpose - later we will try to insert them
            Customer cust1 = new Customer("Barak", "Yacobovitz", "bawak.y10@gmail.com", "12345");
            Customer cust2 = new Customer("Charon", "Sherri", "sheri@gmail.com", "141");
            Customer cust3 = new Customer("Josh", "Cohen", "JoshJosh@gmail.com", "1111");
            Customer cust4 = new Customer("Gal", "Gadot", "galgadot@gmail.com", "hot");
            Customer cust5 = new Customer("Yaniv", "Katan", "yk20@gmail.com", "2020");
            Customer cust6 = new Customer("Reuven", "Atar", "yk20@gmail.com", "2020"); // for checking email can't repeat itself...
            //Company for testing purpose - later we will try to insert them
            Company comp1 = new Company("Tnuva","Tnuva@nuva","123456");
            Company comp2 = new Company("Shtruss","Shtrauss@com","123456");
            Company comp3 = new Company("Hashmal Market","Hashmal@com","1q2we3341");
            Company comp4 = new Company("Dizenhouse","disen@com","21111");
            Company comp5 = new Company("Osem","bamba@com","22222");
            Company comp6 = new Company("Osem","bamba@com","22222");
            Company comp7 = new Company("Tara","Tara@com","22222");
            //Coupon for testing purpose - later we will try to insert them
            Coupon coup1 = new Coupon(Catagory.Electricty,"Oven","nice Oven", Date.valueOf(LocalDate.of(2022,01,01)),Date.valueOf(LocalDate.of(2023,01,01)),55,25.5,"path1");
            Coupon coup2 = new Coupon(Catagory.Electricty,"Dryer","good dryer", Date.valueOf(LocalDate.of(2021,01,01)),Date.valueOf(LocalDate.of(2022,01,01)),10,5000,"path2");
            Coupon coup3 = new Coupon(Catagory.Food,"French Fries","yami ", Date.valueOf(LocalDate.of(2021,01,01)),Date.valueOf(LocalDate.of(2024,01,01)),5000,10,"path3");
            Coupon coup4 = new Coupon(Catagory.Vacation,"France and Belguim","fun ", Date.valueOf(LocalDate.of(2022,10,01)),Date.valueOf(LocalDate.of(2024,01,01)),2,10000,"path4");
            Coupon coup5 = new Coupon(Catagory.Vacation,"France and Belguim old","fun ", Date.valueOf(LocalDate.of(2020,10,01)),Date.valueOf(LocalDate.of(2021,01,01)),1,50000,"path5");
            Coupon coup6 = new Coupon(Catagory.Restaurant,"BBB","burgers ", Date.valueOf(LocalDate.of(2022,10,01)),Date.valueOf(LocalDate.of(2025,01,01)),100,11,"path6");

            //drop and create strategy + start application
            DatabaseManager.getInstance().dropCreateStrategy();
            //Start job for removing expiry coupons
            job = new CouponExpirationDailyJob();
            t1 = new Thread(job);
            t1.start();
            System.out.println("|||||login to Admin|||||");
            AdminFacade admin = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.Admin);
            //AdminFacade admin2 = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "aaa", ClientType.Admin); //login failed..
            System.out.println("|||||adding  customers with Admin user|||||");
            admin.addCustomer(cust1);
            admin.addCustomer(cust2);
            admin.addCustomer(cust3);
            admin.addCustomer(cust4);
            admin.addCustomer(cust5);
            //admin.addCustomer(cust6); // fail because it has the same mail
            admin.getAllCustomers();
            cust6.setEmail("atar@reuven.com");
            System.out.println("|||||update 5th customer with Admin user|||||");
            //admin.updateACustomer(c6) // fail because we can't update id...
            cust6.setId(5);
            admin.updateACustomer(cust6);
            admin.getAllCustomers();
            System.out.println("|||||delete 5th customer with Admin user|||||");
            admin.deleteACustomer(cust6.getId());
            admin.getAllCustomers();
            System.out.println("|||||get 2nd customer with Admin user|||||");
            admin.getASpecifcCustomer(2);
            //admin.getASpecifcCustomer(0); //fail because we dont have this customer id.
            System.out.println("|||||adding a company with Admin user|||||");
            admin.addCompany(comp1);
            admin.addCompany(comp2);
            admin.addCompany(comp3);
            admin.addCompany(comp4);
            admin.addCompany(comp5);
            //admin.addCompany(comp6); ///fail duplicate mail or name
            admin.getAllCompanies();
            System.out.println("|||||updating a company with Admin user|||||");
            //admin.updateCompany(6,comp6); //fail company id does not exists
            //admin.updateCompany(5,comp7); //fail name is not the same
            comp5.setEmail("UpdatedEmail@com");
            admin.updateCompany(5,comp5);
            admin.getAllCompanies();
            System.out.println("|||||deleting a company with Admin user|||||");
            //admin.deleteCompany(6); //company does not exists
            admin.deleteCompany(1);
            admin.getAllCompanies();
            System.out.println("|||||get a specifc company with Admin user|||||");
            //admin.getASpecifcCompany(1); //company does not exists
            admin.getASpecifcCompany(4);
            System.out.println("|||||login to Company|||||");
            //CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.Company); //failed login

            CompanyFacade companyFacade3 = (CompanyFacade) LoginManager.getInstance().login("Hashmal@com", "1q2we3341", ClientType.Company);
            CompanyFacade companyFacade4 = (CompanyFacade) LoginManager.getInstance().login("disen@com", "21111", ClientType.Company);
            //CompanyFacade companyFacade5 = (CompanyFacade) LoginManager.getInstance().login("bamba@com", "22222", ClientType.Company); //failed old email
            CompanyFacade companyFacade5 = (CompanyFacade) LoginManager.getInstance().login("UpdatedEmail@com", "22222", ClientType.Company); //failed old email
            companyFacade3.addCoupon(coup1);
            companyFacade3.addCoupon(coup2);
            companyFacade4.addCoupon(coup4);
            companyFacade4.addCoupon(coup5);
            companyFacade5.addCoupon(coup3);
            System.out.println("|||||After adding coupons to company 3|||||");
            System.out.println(companyFacade3.getCompanyDetails());
            System.out.println(companyFacade3.getListOfAllCouponsRelatedToCompany());
            System.out.println("|||||After adding coupons to company 4 |||||");
            System.out.println(companyFacade4.getCompanyDetails());
            //System.out.println(companyFacade4.getListOfAllCouponsRelatedToCompany());
            System.out.println("|||||After adding coupons to company 5 |||||");
            System.out.println(companyFacade5.getCompanyDetails());
           // System.out.println(companyFacade5.getListOfAllCouponsRelatedToCompany());
            System.out.println("|||||Update coupon  to company 3|||||");
            //companyFacade3.updateCouponPerCompany(1, coup1);// failed not able to update coupon id
            //companyFacade3.updateCouponPerCompany(7, coup1);// failed not able to update coupon id
            coup1.setPrice(2500);
            coup2.setAmount(700);
            companyFacade3.updateCouponPerCompany(1, coup1);
            companyFacade3.updateCouponPerCompany(2, coup2);
            System.out.println(companyFacade3.getListOfAllCouponsRelatedToCompany());
            System.out.println("|||||Delete coupon 1 to company 3|||||");
            //TODO will test after insert connections to customers...
            //companyFacade3.deleteCoupon(1);
            //System.out.println(companyFacade3.getListOfAllCouponsRelatedToCompany());
            System.out.println("|||||get coupon for a specifc catagory|||||");
            System.out.println(companyFacade3.getListOfAllCouponsRelatedToCompanyFromSpecifcCatagory(Catagory.Electricty));
            System.out.println(companyFacade3.getListOfAllCouponsRelatedToCompanyFromSpecifcCatagory(Catagory.Food));
            System.out.println("|||||get coupon until a specifc price range|||||");
            System.out.println(companyFacade3.getListOfAllCouponsWithMaxPrice(2600));
            System.out.println("|||||login to Customer|||||");
            CustomerFacade customerFacade1 = (CustomerFacade) LoginManager.getInstance().login("bawak.y10@gmail.com", "12345", ClientType.Customer);
            //CustomerFacade customerFacade1 = (CustomerFacade) LoginManager.getInstance().login("bawak.y10@gmail.com", "12345666", ClientType.Customer); //failed wrong password
            //CustomerFacade customerFacade1 = (CustomerFacade) LoginManager.getInstance().login("notexistsmail@com", "12345", ClientType.Customer); //failed wrong password
            CustomerFacade customerFacade2 = (CustomerFacade) LoginManager.getInstance().login("sheri@gmail.com", "141", ClientType.Customer);
            CustomerFacade customerFacade3 = (CustomerFacade) LoginManager.getInstance().login("JoshJosh@gmail.com", "1111", ClientType.Customer);
            System.out.println(customerFacade1.getCustomerDetails());
            System.out.println(customerFacade2.getCustomerDetails());
            System.out.println(customerFacade3.getCustomerDetails());
            System.out.println("|||||insert coupon to Customers|||||");

            customerFacade1.purcheseCouponForCustomer(1); //checked amount change...55 to 54
            System.out.println(customerFacade1.getCustomerDetails());
            customerFacade2.purcheseCouponForCustomer(1); //checked amount change...54 to 53
            System.out.println(customerFacade2.getCustomerDetails());
            customerFacade1.purcheseCouponForCustomer(3);//checked amount change...2 to 1
            System.out.println(customerFacade1.getCustomerDetails());
            //customerFacade1.purcheseCouponForCustomer(3);
            //customerFacade1.purcheseCouponForCustomer(4);
            //customerFacade1.purcheseCouponForCustomer(1);//failed cant buy same coupon
            //coup1.setAmount(0);
            //companyFacade3.updateCouponPerCompany(1,coup1);
            //customerFacade1.purcheseCouponForCustomer(1); //failed cant buy 0 amount
            //coup1.setEndDate(Date.valueOf(LocalDate.of(2022,01, 01)));
            //companyFacade3.updateCouponPerCompany(1,coup1);
            //customerFacade1.purcheseCouponForCustomer(1); //failed coupon is expired
            System.out.println("|||||return all coupons from a specfic catagory|||||");
            System.out.println(customerFacade1.getAllCouponsForCustomer(Catagory.Electricty));
            System.out.println(customerFacade1.getAllCouponsForCustomer(Catagory.Food));
            System.out.println("|||||return all coupons until max amount|||||");
            System.out.println(customerFacade1.getAllCouponsForCustomerWithMaxAmount(15000));
            System.out.println("|||||Delete coupon 1 to company 3||||| (from company user...)");
            companyFacade3.deleteCoupon(1);
            System.out.println(companyFacade3.getListOfAllCouponsRelatedToCompany());
            System.out.println(customerFacade1.getCustomerDetails());
            System.out.println(customerFacade2.getCustomerDetails());

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            job.stop();



        }

    }
}
