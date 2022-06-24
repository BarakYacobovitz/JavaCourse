package beans;

import java.sql.Date;

/**
 * created by yacob at 29/05/2022 coupon bean
 */
public class Coupon {
    private int id;
    private int companyID;
    private Catagory catagory;
    private String title;
    private String descripition;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    public Coupon(int id, int companyID, Catagory catagory, String title, String descripition, Date startDate, Date endDate, int amount, double price, String image) {
        this(companyID,catagory,title,descripition,startDate,endDate,amount,price,image);
        this.id = id;

    }

    public Coupon(int companyID, Catagory catagory, String title, String descripition, Date startDate, Date endDate, int amount, double price, String image) {
        this(catagory,title, descripition, startDate, endDate, amount, price, image );
        this.companyID = companyID;

    }

    public Coupon(Catagory catagory, String title, String desc, Date startDate, Date endDate, int amount, double price, String image) {
        this.catagory = catagory;
        this.title = title;
        this.descripition = desc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public Catagory getCatagory() {
        return catagory;
    }

    public void setCatagory(Catagory catagory) {
        this.catagory = catagory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescripition() {
        return descripition;
    }

    public void setDescripition(String descripition) {
        this.descripition = descripition;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyID=" + companyID +
                ", catagory=" + catagory +
                ", title='" + title + '\'' +
                ", descripition='" + descripition + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
