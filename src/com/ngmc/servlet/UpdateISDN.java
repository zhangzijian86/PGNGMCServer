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
import com.ngmc.bean.PGNGMC_User;
import com.ngmc.bean.Pgdr_User;
import com.ngmc.dao.DaoImpl;
import com.ngmc.json.JsonUtil;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

@SuppressWarnings("serial")
public class UpdateISDN extends HttpServlet {


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
		String isdnnumber=request.getParameter("isdnnumber");
		String phoneNumber=request.getParameter("phoneNumber");
		System.out.println("==UpdateISDN=isdnnumber="+isdnnumber);
		System.out.println("==UpdateISDN=phoneNumber="+phoneNumber);
		DaoImpl userDaoImpl=new DaoImpl();
		boolean flag=userDaoImpl.UpdateISDN(isdnnumber,phoneNumber);
		if (flag) 
		{
			out.write("yes");
		}
		else {			
			out.write("no");
		}
		out.flush();
		out.close();

	}

}
