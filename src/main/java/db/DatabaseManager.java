package db;

import dao.CatagoriesDAOImpl;

/**
 * Created by kobis on 08 May, 2022 - class for manger database including drop create Strategy for start schema
 */
public class DatabaseManager {

    private static final DatabaseManager instance = new DatabaseManager();
    private static final CatagoriesDAOImpl catagoriesDAOImpl = new CatagoriesDAOImpl();
    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    private static final String QUERY_CREATE_SCHEMA = "CREATE SCHEMA `coupons_database`;";
    private static final String QUERY_DROP_SCHEMA = "DROP DATABASE `coupons_database`;";
    private static final String QUERY_CREATE_TABLE_COMPANIES = "CREATE TABLE `coupons_database`.`companies` (\n" +
            "`id` INT NOT NULL AUTO_INCREMENT,\n" +
            "`name` VARCHAR(45) NOT NULL,\n" +
            "`email` VARCHAR(45) NOT NULL,\n" +
            " `password` VARCHAR(45) NOT NULL,\n" +
            "PRIMARY KEY (`id`));\n";

    private static final String QUERY_CREATE_TABLE_CUSTOMERS = "CREATE TABLE `coupons_database`.`customers` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `first_name` VARCHAR(45) NOT NULL,\n" +
            "  `last_name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";

    private static final String QUERY_CREATE_TABLE_CATEGORIES = "CREATE TABLE `coupons_database`.`categories` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));";

    private static final String QUERY_CREATE_TABLE_COUPONS = "CREATE TABLE `coupons_database`.`coupons` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `company_id` INT NOT NULL,\n" +
            "  `catagory_id` INT NOT NULL,\n" +
            "  `title` VARCHAR(45) NOT NULL,\n" +
            "  `description` VARCHAR(45) NOT NULL,\n" +
            "  `start_date` DATE NOT NULL,\n" +
            "  `end_date` DATE NOT NULL,\n" +
            "  `amount` INT NOT NULL,\n" +
            "  `price` DOUBLE NOT NULL,\n" +
            "  `image` VARCHAR(45)  NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `id_idx` (`company_id` ASC) VISIBLE,\n" +
            "  INDEX `id_idx1` (`catagory_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `id_companies`\n" +
            "    FOREIGN KEY (`company_id`)\n" +
            "    REFERENCES `coupons_database`.`companies` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `id_categories`\n" +
            "    FOREIGN KEY (`catagory_id`)\n" +
            "    REFERENCES `coupons_database`.`categories` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

    private static final String QUERY_CREATE_TABLE_CUSTOMER_VS_COUPONS ="CREATE TABLE `coupons_database`.`customer_vs_coupons` (\n" +
            "  `customer_id` INT NOT NULL,\n" +
            "  `coupon_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`customer_id`, `coupon_id`),\n" +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `coupons_id`\n" +
            "    FOREIGN KEY (`coupon_id`)\n" +
            "    REFERENCES `coupons_database`.`coupons` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `customer_id`\n" +
            "    FOREIGN KEY (`customer_id`)\n" +
            "    REFERENCES `coupons_database`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

    public void dropCreateStrategy() {

        try {
            JDBCUtils.executeQuery(QUERY_DROP_SCHEMA);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            JDBCUtils.executeQuery(QUERY_CREATE_SCHEMA);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_COMPANIES);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_CUSTOMERS);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_CATEGORIES);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_COUPONS);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_CUSTOMER_VS_COUPONS);
            catagoriesDAOImpl.AddAllCatagories();



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
