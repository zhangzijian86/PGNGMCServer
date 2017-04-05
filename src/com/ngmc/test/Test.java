package com.ngmc.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ngmc.bean.PGNGMC_Bike;
import com.ngmc.bean.PGNGMC_Bike_Position;
import com.ngmc.db.GetConn;

public class Test {

	public static void main(String[] args) {
		//116.649794,40.14439
		  double longitude=116.649794;
		  double latitude=40.14439;
		  double r = 6371;//地球半径千米  
	      double dis = 0.5;//0.5千米距离  
	      double dlng =  2*Math.asin(Math.sin(dis/(2*r))/Math.cos(latitude*Math.PI/180));  
	      dlng = dlng*180/Math.PI;//角度转为弧度  
	      double dlat = dis/r;  
	      dlat = dlat*180/Math.PI;          
	      double minlat =latitude-dlat;  
	      double maxlat = latitude+dlat;  
	      double minlng = longitude -dlng;  
	      double maxlng = longitude + dlng;  
		// TODO Auto-generated method stub
		GetConn getConn=new GetConn();
		ResultSet rs = null;
		Connection conn=getConn.getConnection();
		List<PGNGMC_Bike> PGB_List = new ArrayList<PGNGMC_Bike>();			
		try {
			PreparedStatement ps=conn.prepareStatement(
			"select BIKE_ID,BIKE_Code,BIKE_EnableDate,BIKE_Company,BIKE_Status,BIKE_Type,Result1.POSITION_X,"
			+ "Result1.POSITION_Y from ("
			+ "select POSITION_BIKE_ID,POSITION_X,POSITION_Y from ("
			+ "select POSITION_ID,max(POSITION_RecordDate),POSITION_X,POSITION_Y,POSITION_BIKE_ID "
			+ "from PG_BIKE_POSITION " 
			+ "group by POSITION_BIKE_ID ) Result "
			+ "where Result.POSITION_X>? and Result.POSITION_X <? and Result.POSITION_Y>? and Result.POSITION_Y<? ) Result1 "
			+ "inner join PG_BIKE on Result1.POSITION_BIKE_ID = BIKE_ID where BIKE_Status = 0;");
			
			//select POSITION_ID,POSITION_Code,POSITION_X,POSITION_Y,POSITION_RecordDate,POSITION_BIKE_ID
			//from PG_BIKE_POSITION;"
			ps.setDouble(1,minlng);
			ps.setDouble(2,maxlng);
			ps.setDouble(3,minlat);
			ps.setDouble(4,maxlat);		
			System.out.println("=getPrice=sql="+ps.toString());
			rs=ps.executeQuery();
			while (rs.next())
			{
				PGNGMC_Bike PGB = new PGNGMC_Bike();
				PGB.setBIKE_ID(rs.getString("BIKE_ID"));
				PGB.setBIKE_Code(rs.getString("BIKE_Code"));
				PGB.setBIKE_EnableDate(rs.getString("BIKE_EnableDate"));
				PGB.setBIKE_Company(rs.getString("BIKE_Company"));
				PGB.setBIKE_Type(rs.getString("BIKE_Type"));
				PGB.setPOSITION_X(rs.getString("POSITION_X"));
				PGB.setPOSITION_Y(rs.getString("POSITION_Y"));
				System.out.println("=BIKE_ID="+rs.getString("BIKE_ID"));
				System.out.println("=BIKE_Code="+rs.getString("BIKE_Code"));
				System.out.println("=BIKE_EnableDate="+rs.getString("BIKE_EnableDate"));
				System.out.println("=BIKE_Company="+rs.getString("BIKE_Company"));
				System.out.println("=BIKE_Type="+rs.getString("BIKE_Type"));
				System.out.println("=POSITION_X="+rs.getString("POSITION_X"));
				System.out.println("=POSITION_Y="+rs.getString("POSITION_Y"));
				PGB_List.add(PGB);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("=getRecycle==pdrlist==="+PGB_List.size());
	}

}
