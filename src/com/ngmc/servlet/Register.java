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
public class Register extends HttpServlet {


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
		String phoneNumber=request.getParameter("phoneNumber");
		System.out.println("==Register=="+phoneNumber);
		DaoImpl userDaoImpl=new DaoImpl();
		PGNGMC_User usr=userDaoImpl.register(phoneNumber);
		if (usr!=null) 
		{
			List<PGNGMC_User> PGU_List= new ArrayList<PGNGMC_User>();	
			Gson gson=new Gson();
			PGU_List.add(usr);
			String jsonstring=gson.toJson(PGU_List);
			out.write(jsonstring);
		}
		else {			
			out.write("no");
		}
		out.flush();
		out.close();

	}

}
