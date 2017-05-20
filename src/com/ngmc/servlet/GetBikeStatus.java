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
import com.ngmc.bean.PGNGMC_Bike_Position;
import com.ngmc.bean.Pgdr_User;
import com.ngmc.dao.DaoImpl;

@SuppressWarnings("serial")
public class GetBikeStatus extends HttpServlet {
	
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
		String resultCode=request.getParameter("resultCode");			
		DaoImpl userDaoImpl=new DaoImpl();
		String result=userDaoImpl.GetBikeStatus(resultCode);
		Gson gson=new Gson();
		String jsonstring=gson.toJson(result);
		System.out.println("======jsonstring========="+jsonstring);
		out.write(jsonstring);
		out.flush();
		out.close();
	}

}
