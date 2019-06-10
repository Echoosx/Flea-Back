package cn.util.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;

public class Database {
	
//	��ȡ�����ļ��������ݿ�����
	public static Connection db_properties() throws FileNotFoundException, IOException, SQLException{
		ResourceBundle resource = ResourceBundle.getBundle("jdbc_mysql");
		String URL=resource.getString("URL");
		String USER=resource.getString("USER");
		String PASSWORD=resource.getString("PASSWORD");
		String DRIVER=resource.getString("DRIVER");
		Connection con=db_connect(DRIVER,URL, USER, PASSWORD);
		return con;
	}
	
//	�ṩIP���û��������룬�������ݿ�����
	public static Connection db_connect(String DRIVER,String URL,String USER,String PASSWORD) {
		Connection con=null;
	    try {
	        //1.������������
	        Class.forName(DRIVER);
	        //2.������ݿ������
	        con=DriverManager.getConnection(URL, USER, PASSWORD);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	    	System.out.print("ERROR:���ݿ⽨������ʧ��");
	        e.printStackTrace();
	    }
		return con;
	}
}

