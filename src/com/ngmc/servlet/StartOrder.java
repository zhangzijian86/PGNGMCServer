package com.ngmc.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ngmc.bean.PGNGMC_Bike;
import com.ngmc.bean.PGNGMC_Order;
import com.ngmc.bean.PGNGMC_User;
import com.ngmc.bean.Pgdr_User;
import com.ngmc.dao.DaoImpl;
import com.ngmc.json.JsonUtil;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

@SuppressWarnings("serial")
public class StartOrder extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String userid=request.getParameter("userid");
		String bikeid=request.getParameter("bikeid");
		System.out.println("==StartOrder=userid="+userid);
		System.out.println("==StartOrder=bikeid="+bikeid);
		DaoImpl userDaoImpl=new DaoImpl();
		boolean flag=userDaoImpl.StartOrder(userid,bikeid);
		if (flag) 
		{					
			out.write("ok");
		}
		else {			
			out.write("no");
		}
		out.flush();
		out.close();
	}
	
	
}
