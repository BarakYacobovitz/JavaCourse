package jobs;

import dao.CouponsDAO;
import dao.CouponsDAOImpl;

import java.sql.SQLException;

/**
 * created by yacob at 20/06/2022
 */
public class CouponExpirationDailyJob implements Runnable {

    private boolean quit = true ;
    private CouponsDAO couponsDAO = new CouponsDAOImpl();
    @Override
    public void run() {
        System.out.println("System UP");
        while(quit){
            try {
                System.out.println("expired coupon process is about to run");
                couponsDAO.deleteExpiredCoupons();
                  Thread.sleep(1000*60*60*24); // 1 day
                //Thread.sleep(10); // every 10 milisecond for testing purpose


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println("job stop");
    }

    public CouponExpirationDailyJob() {
    }

    public void stop() {
        System.out.println("Goodbye System is going to sleep...");
        quit = false;

    }
}
