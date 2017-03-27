package com.ngmc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetExcelConn {
	public Connection getConnection(String dizhi,String duankou,String mingzi,String zhanghu,String mima) 
	{ 
		Connection connection = null;
		try {
			Class.forName("org.gjt.mm.mysql.Driver");			
			connection=DriverManager.getConnection("jdbc:mysql://"+dizhi+":"+duankou+"/"+mingzi+"?characterEncoding=utf-8", zhanghu, mima);
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {	
			e.printStackTrace();
		}   	
		return connection;
	}
	
	public void closeconn(Connection connection)
	{  	 
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
