package sample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author yuanjie.wang
 * @create 2018-09-20 上午9:46
 * good luck
 **/
public class DatabaseModel {
    private String url = "jdbc:mysql://localhost:3306/wechat?useUnicode=true&characterEncoding=utf-8";
    private final static String driver = "com.mysql.jdbc.Driver";
    private String userName = "root";
    private String password = "";
    private Connection connection;
    private Statement statement;//静态查询
    private PreparedStatement preparedStatement;//动态查询
    public DatabaseModel() {

    }
    /*
    链接数据库
     */
    public void connect(){
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
