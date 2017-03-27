package com.ngmc.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		List<PGNGMC_Bike_Position> PGBP_List = new ArrayList<PGNGMC_Bike_Position>();			
		try {
			PreparedStatement ps=conn.prepareStatement(
					  "select POSITION_ID,POSITION_Code,POSITION_X,POSITION_Y,POSITION_RecordDate,"
					  + "POSITION_BIKE_ID from PG_BIKE_POSITION "
					  + "where POSITION_RecordDate in ( "
					  + "select max(POSITION_RecordDate) "
					  + "from PG_BIKE_POSITION "					  
					  + "group by POSITION_BIKE_ID) "
					  + "and POSITION_X>? and POSITION_X <? and POSITION_Y>? and POSITION_Y<? "
					);
			
			//select POSITION_ID,POSITION_Code,POSITION_X,POSITION_Y,POSITION_RecordDate,POSITION_BIKE_ID
			//from PG_BIKE_POSITION;
			ps.setString(1,String.valueOf(minlng));
			ps.setString(2,String.valueOf(maxlng));
			ps.setString(3,String.valueOf(minlat));
			ps.setString(4,String.valueOf(maxlat));		
			System.out.println("=getPrice=sql="+ps.toString());
			rs=ps.executeQuery();
			while (rs.next())
			{
				PGNGMC_Bike_Position PGBP = new PGNGMC_Bike_Position();
				PGBP.setPOSITION_ID(rs.getString("POSITION_ID"));
				PGBP.setPOSITION_Code(rs.getString("POSITION_Code"));
				PGBP.setPOSITION_X(rs.getString("POSITION_X"));
				PGBP.setPOSITION_Y(rs.getString("POSITION_Y"));
				PGBP.setPOSITION_RecordDate(rs.getString("POSITION_RecordDate"));
				PGBP.setPOSITION_BIKE_ID(rs.getString("POSITION_BIKE_ID"));
				System.out.println("=POSITION_ID="+rs.getString("POSITION_ID"));
				System.out.println("=POSITION_Code="+rs.getString("POSITION_Code"));
				System.out.println("=POSITION_X="+rs.getString("POSITION_X"));
				System.out.println("=POSITION_Y="+rs.getString("POSITION_Y"));
				System.out.println("=POSITION_RecordDate="+rs.getString("POSITION_RecordDate"));
				System.out.println("=POSITION_BIKE_ID="+rs.getString("POSITION_BIKE_ID"));
				PGBP_List.add(PGBP);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("=getRecycle==pdrlist==="+PGBP_List.size());
	}

}
