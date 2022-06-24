package beans;

import java.util.ArrayList;
import java.util.List;

/**
 * created by yacob at 29/05/2022 - company bean
 */
public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Coupon> coupons = new ArrayList<Coupon>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }

    public Company(int id, String name, String email, String password, List<Coupon> coupons) {
        this(name,email,password,coupons);
        this.id = id;
    }

    public Company(String name, String email, String password, List<Coupon> coupons) {
        this(name,email,password);
        this.coupons = coupons;
    }

    public Company(int id,String name,String email,String password){
        this(name,email,password);
        this.id = id;
    }

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
