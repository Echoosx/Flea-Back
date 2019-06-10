package cn.util.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;

public class Database {
	
//	读取配置文件建立数据库连接
	public static Connection db_properties() throws FileNotFoundException, IOException, SQLException{
		ResourceBundle resource = ResourceBundle.getBundle("jdbc_mysql");
		String URL=resource.getString("URL");
		String USER=resource.getString("USER");
		String PASSWORD=resource.getString("PASSWORD");
		String DRIVER=resource.getString("DRIVER");
		Connection con=db_connect(DRIVER,URL, USER, PASSWORD);
		return con;
	}
	
//	提供IP，用户名，密码，建立数据库连接
	public static Connection db_connect(String DRIVER,String URL,String USER,String PASSWORD) {
		Connection con=null;
	    try {
	        //1.加载驱动程序
	        Class.forName(DRIVER);
	        //2.获得数据库的连接
	        con=DriverManager.getConnection(URL, USER, PASSWORD);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	    	System.out.print("ERROR:数据库建立连接失败");
	        e.printStackTrace();
	    }
		return con;
	}
}

