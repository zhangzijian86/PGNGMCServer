package com.ngmc.servlet;


import java.io.IOException;
import java.io.PrintWriter;
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
public class GetOrder extends HttpServlet {


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
		System.out.println("==GetOrder=="+userid);
		DaoImpl userDaoImpl=new DaoImpl();
		List<PGNGMC_Order> orderlist=userDaoImpl.GetOrder(userid);
		if (orderlist!=null&&orderlist.size()>0) 
		{
			Gson gson=new Gson();			
			String jsonstring=gson.toJson(orderlist);
			out.write(jsonstring);
		}
		else {			
			out.write("no");
		}
		out.flush();
		out.close();
	}
}
