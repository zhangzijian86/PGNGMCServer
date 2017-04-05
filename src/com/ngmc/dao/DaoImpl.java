package com.ngmc.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ngmc.bean.PGNGMC_Bike;
import com.ngmc.bean.PGNGMC_Bike_Position;
import com.ngmc.bean.Pgdr_User;
import com.ngmc.db.GetConn;



public class DaoImpl 
{
	
	public List<PGNGMC_Bike> GetBikeByPosition(String latitude,String longitude) 
	{
		//double longitude=116.649794;
		//double latitude=40.14439;
		double r = 6371;//地球半径千米  
	    double dis = 0.5;//0.5千米距离  
	    double dlng =  2*Math.asin(Math.sin(dis/(2*r))/Math.cos(Double.valueOf(latitude)*Math.PI/180));  
	    dlng = dlng*180/Math.PI;//角度转为弧度  
	    double dlat = dis/r;  
	    dlat = dlat*180/Math.PI;          
	    double minlat =Double.valueOf(latitude) - dlat;  
		double maxlat = Double.valueOf(latitude) + dlat;  
	    double minlng = Double.valueOf(longitude) - dlng;  
		double maxlng = Double.valueOf(longitude) + dlng;   
		// TODO Auto-generated method stub
		GetConn getConn=new GetConn();
		ResultSet rs = null;
		Connection conn=getConn.getConnection();
		List<PGNGMC_Bike> PGB_List = new ArrayList<PGNGMC_Bike>();			
		try {
			PreparedStatement ps=conn.prepareStatement(
			"select BIKE_ID,BIKE_Code,DATE_FORMAT(BIKE_EnableDate,'%Y-%m-%d') as BIKE_EnableDate,BIKE_Company,BIKE_Status,BIKE_Type,Result1.POSITION_X,"
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
				PGB.setBIKE_Status(rs.getString("BIKE_Status"));
				PGB.setBIKE_Type(rs.getString("BIKE_Type"));
				PGB.setPOSITION_X(rs.getString("POSITION_X"));
				PGB.setPOSITION_Y(rs.getString("POSITION_Y"));
				System.out.println("=BIKE_ID="+rs.getString("BIKE_ID"));
				System.out.println("=BIKE_Code="+rs.getString("BIKE_Code"));
				System.out.println("=BIKE_EnableDate="+rs.getString("BIKE_EnableDate"));
				System.out.println("=BIKE_Company="+rs.getString("BIKE_Company"));
				System.out.println("=BIKE_Status="+rs.getString("BIKE_Status"));
				System.out.println("=BIKE_Type="+rs.getString("BIKE_Type"));
				System.out.println("=POSITION_X="+rs.getString("POSITION_X"));
				System.out.println("=POSITION_Y="+rs.getString("POSITION_Y"));
				PGB_List.add(PGB);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return PGB_List;
	}
	
	
	public Pgdr_User login(String usermobile) 
	{
		boolean b = false;
		GetConn getConn=new GetConn();
		ResultSet rs = null;
		Connection conn=getConn.getConnection();
		Pgdr_User puser = new Pgdr_User();
		try {
			PreparedStatement ps=conn.prepareStatement("select USER_ID,USER_MOBILE,USER_NAME,USER_PASSWORD"
					+ ",USER_ADDRESS,USER_EMAIL,USER_STATUS,USER_TYPE,USER_PHOTO"
					+ " from PGDR_USER where USER_MOBILE=? ");
			ps.setString(1,usermobile);
			rs=ps.executeQuery();
			if (rs.next())
			{
				b=true;
				puser.setUser_id(rs.getString("USER_ID"));
				puser.setUser_name(rs.getString("USER_NAME"));
				puser.setUser_password(rs.getString("USER_PASSWORD"));
				puser.setUser_mobile(rs.getString("USER_MOBILE"));
				puser.setUser_address(rs.getString("USER_ADDRESS"));
				puser.setUser_email(rs.getString("USER_EMAIL"));
				puser.setUser_status(rs.getString("USER_STATUS"));
				puser.setUser_type(rs.getString("USER_TYPE"));
				puser.setUser_photo(rs.getString("USER_PHOTO"));
				puser.setUser_return(true);
			}
			else
			{
				puser.setUser_return(false);
				b=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return puser;
	}
	
//	public List<Pgdr_price> getPrice(String pricetype) 
//	{
//		GetConn getConn=new GetConn();
//		ResultSet rs = null;
//		Connection conn=getConn.getConnection();
//		List<Pgdr_price> pdplist = new ArrayList<Pgdr_price>();
//		
//		String uertype = "";
//			
//		try {
//			PreparedStatement ps=conn.prepareStatement(
//					  "select "
//					+ "PRICE_ID,PRICE_NAME,PRICE_ISVALID,"
//					+ "PRICE_TYPE,PRICE_PRICE,PRICE_EXPLAIN " 
//					+ "from PGDR_PRICE where PRICE_TYPE=?  and PRICE_ISVALID = 1"
//					);
//			ps.setString(1,pricetype);
//			System.out.println("=getPrice=sql="+ps.toString());
//			System.out.println("===uertype===="+uertype);
//			rs=ps.executeQuery();
//			while (rs.next())
//			{
//				Pgdr_price pdp = new Pgdr_price();
//				pdp.setPrice_id(rs.getString("PRICE_ID"));
//				pdp.setPrice_name(rs.getString("PRICE_NAME"));
//				pdp.setPrice_isvalid(rs.getString("PRICE_ISVALID"));
//				pdp.setPrice_type(rs.getString("PRICE_TYPE"));
//				pdp.setPrice_price(rs.getString("PRICE_PRICE"));
//				pdp.setPrice_explain(rs.getString("PRICE_EXPLAIN"));
//				pdplist.add(pdp);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		System.out.println("=getRecycle==pdrlist==="+pdplist.size());
//		return pdplist;
//	}
	
//	public List<Ppdr_dailyrecycle> getRecycle(String phoneNumber) 
//	{
//		GetConn getConn=new GetConn();
//		ResultSet rs = null;
//		Connection conn=getConn.getConnection();
//		List<Ppdr_dailyrecycle> pdrlist = new ArrayList<Ppdr_dailyrecycle>();
//		
//		String uertype = "";
//		
//		
//		try {
//			PreparedStatement ps=conn.prepareStatement("select USER_ID,USER_MOBILE,USER_NAME,USER_PASSWORD"
//					+ ",USER_ADDRESS,USER_EMAIL,USER_STATUS,USER_TYPE,USER_PHOTO"
//					+ " from PGDR_USER where USER_MOBILE=? ");
//			ps.setString(1,phoneNumber);
//			rs=ps.executeQuery();
//			if (rs.next())
//			{
//				uertype = rs.getString("USER_TYPE");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}	
//		
//		try {
//			PreparedStatement ps=conn.prepareStatement(
//					  "select * from (select "
//					+ "DAILYRECYCLE_ID,DAILYRECYCLE_USER_MOBILE,DAILYRECYCLE_DATE,"
//					+ "DAILYRECYCLE_WEEK,DAILYRECYCLE_ISCYCLE,DAILYRECYCLE_CYCLETYPE,"
//					+ "DAILYRECYCLE_ISVALID,DAILYRECYCLE_STATUS,DAILYRECYCLE_RECYCLINGMANPHONE,"
//					+ "DAILYRECYCLE_FINISHTIME,DAILYRECYCLE_TYPE,DAILYRECYCLE_EXPLAIN,"
//					+ "DAILYRECYCLE_ADDRESS,DAILYRECYCLE_NAME "
//					+ "from PGDR_DAILYRECYCLE where DAILYRECYCLE_USER_MOBILE=? "
//					+ "and DAILYRECYCLE_ISVALID = 1 "
//					+ "order by DAILYRECYCLE_STATUS) t1 "
//					+ "UNION "
//					+ "select * from (select "
//					+ "DAILYRECYCLE_ID,DAILYRECYCLE_USER_MOBILE,DAILYRECYCLE_DATE,"
//					+ "DAILYRECYCLE_WEEK,DAILYRECYCLE_ISCYCLE,DAILYRECYCLE_CYCLETYPE,"
//					+ "DAILYRECYCLE_ISVALID,DAILYRECYCLE_STATUS,DAILYRECYCLE_RECYCLINGMANPHONE,"
//					+ "DAILYRECYCLE_FINISHTIME,DAILYRECYCLE_TYPE,DAILYRECYCLE_EXPLAIN,"
//					+ "DAILYRECYCLE_ADDRESS,DAILYRECYCLE_NAME "
//					+ "from PGDR_DAILYRECYCLE where DAILYRECYCLE_RECYCLINGMANPHONE=? "
//					+ "and DAILYRECYCLE_ISVALID = 1 "
//					+ "order by DAILYRECYCLE_STATUS) t2 "
//					);
//			ps.setString(1,phoneNumber);
//			ps.setString(2,phoneNumber);
//			System.out.println("=getRecycle=sql="+ps.toString());
//			System.out.println("===uertype===="+uertype);
//			rs=ps.executeQuery();
//			while (rs.next())
//			{
//				Ppdr_dailyrecycle pdr = new Ppdr_dailyrecycle();
//				pdr.setDailyrecycle_id(rs.getString("DAILYRECYCLE_ID"));
//				pdr.setDailyrecycle_user_mobile(rs.getString("DAILYRECYCLE_USER_MOBILE"));				
//				pdr.setDailyrecycle_date(rs.getString("DAILYRECYCLE_DATE"));				
//				pdr.setDailyrecycle_week(rs.getString("DAILYRECYCLE_WEEK"));
//				pdr.setDailyrecycle_iscycle(rs.getString("DAILYRECYCLE_ISCYCLE"));
//				pdr.setDailyrecycle_cycletype(rs.getString("DAILYRECYCLE_CYCLETYPE"));				
//				pdr.setDailyrecycle_isvalid(rs.getString("DAILYRECYCLE_ISVALID"));
//				pdr.setDailyrecycle_status(rs.getString("DAILYRECYCLE_STATUS"));
//				pdr.setDailyrecycle_recyclingmanphone(rs.getString("DAILYRECYCLE_RECYCLINGMANPHONE"));				
//				pdr.setDailyrecycle_finishtime(rs.getString("DAILYRECYCLE_FINISHTIME"));
//				pdr.setDailyrecycle_type(rs.getString("DAILYRECYCLE_TYPE"));
//				pdr.setDailyrecycle_explain(rs.getString("DAILYRECYCLE_EXPLAIN"));				
//				pdr.setDailyrecycle_address(rs.getString("DAILYRECYCLE_ADDRESS"));
//				pdr.setDailyrecycle_name(rs.getString("DAILYRECYCLE_NAME"));
//				pdr.setUser_type(uertype);
//				pdrlist.add(pdr);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		System.out.println("=getRecycle==pdrlist==="+pdrlist.size());
//		return pdrlist;
//	}
	
	public boolean register(Pgdr_User pgdr_user)
	{
		boolean b=false;
		GetConn getConn=new GetConn();
		int i = 0;
		Connection conn=getConn.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("insert into PGDR_USER (USER_MOBILE,USER_PASSWORD) values (?,?)");
			ps.setString(1,pgdr_user.getUser_mobile());
			ps.setString(2,pgdr_user.getUser_password());
			i=ps.executeUpdate();
			if (i>0)
			{
				b=true;
			}
			else
			{
				b=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getConn.closeconn(conn);
		return b;		
	}
	
	public boolean updateUser(Pgdr_User pgdr_user)
	{
		System.out.println("====UpdateUser=============33======");
		boolean b=false;
		GetConn getConn=new GetConn();
		int i = 0;
		Connection conn=getConn.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("update PGDR_USER "
													+ "set USER_NAME = ? , "
													+ "USER_PASSWORD = ? , "
													+ "USER_ADDRESS = ? , "
													+ "USER_EMAIL = ? , "
													+ "USER_STATUS = ? , "
													+ "USER_TYPE = ? , "
													+ "USER_PHOTO = ?  "
													+ "where USER_MOBILE = ? "
													);
			System.out.println("====UpdateUser=============44======");
			if(pgdr_user.getUser_name()!=null){
				ps.setString(1,pgdr_user.getUser_name());
			}else{
				ps.setString(1,null);
			}
			if(pgdr_user.getUser_password()!=null){
				ps.setString(2,pgdr_user.getUser_password());
			}else{
				ps.setString(2,null);
			}
			
			if(pgdr_user.getUser_address()!=null){
				String address = pgdr_user.getUser_address();
				System.out.println("====UpdateUser=============55======");
				try {
					String addressstr = new String(address.getBytes("UTF-8"));
					ps.setString(3,addressstr);
					System.out.println("====UpdateUser=============66======"+addressstr);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}	
			}else{
				ps.setString(3,null);
			}
			
			if(pgdr_user.getUser_email()!=null){
				ps.setString(4,pgdr_user.getUser_email());
			}else{
				ps.setString(4,null);
			}
			
			if(pgdr_user.getUser_status()!=null){
				ps.setString(5,pgdr_user.getUser_status());
			}else{
				ps.setString(5,null);
			}
			
			if(pgdr_user.getUser_type()!=null){
				ps.setString(6,pgdr_user.getUser_type());
			}else{
				ps.setString(6,null);
			}
			
			if(pgdr_user.getUser_photo()!=null){
				ps.setString(7,pgdr_user.getUser_photo());
			}else{
				ps.setString(7,null);
			}
			
			if(pgdr_user.getUser_mobile()!=null){
				ps.setString(8,pgdr_user.getUser_mobile());
			}else{
				ps.setString(8,null);
			}
			System.out.println("====UpdateUser=============77====sql=="+ps.toString());
			i=ps.executeUpdate();
			if (i>0)
			{
				b=true;
			}
			else
			{
				b=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getConn.closeconn(conn);
		return b;		
	}
	
//	public boolean addRecycle(Ppdr_dailyrecycle pgdr_recycle)
//	{
//		System.out.println("====addRecycle=============33======");
//		boolean b=false;
//		GetConn getConn=new GetConn();
//		int i = 0;
//		Connection conn=getConn.getConnection();
//		try {
//			PreparedStatement ps=conn.prepareStatement("insert into PGDR_DAILYRECYCLE("
//			+ "DAILYRECYCLE_USER_MOBILE,DAILYRECYCLE_DATE,DAILYRECYCLE_WEEK,"
//			+ "DAILYRECYCLE_ISCYCLE,DAILYRECYCLE_CYCLETYPE,DAILYRECYCLE_ISVALID,"
//			+ "DAILYRECYCLE_STATUS,DAILYRECYCLE_RECYCLINGMANPHONE,DAILYRECYCLE_FINISHTIME,"
//			+ "DAILYRECYCLE_TYPE,DAILYRECYCLE_EXPLAIN,DAILYRECYCLE_ADDRESS,DAILYRECYCLE_NAME"
//			+ ")values("
//			+ "?,?,?,"
//			+ "?,?,?,"
//			+ "?,?,?,"
//			+ "?,?,?,"
//			+ "?)"
//			);			
//			if(pgdr_recycle.getDailyrecycle_user_mobile()!=null){
//				ps.setString(1,pgdr_recycle.getDailyrecycle_user_mobile());
//			}else{
//				ps.setString(1,null);
//			}
//			if(pgdr_recycle.getDailyrecycle_date()!=null){
//				ps.setString(2,pgdr_recycle.getDailyrecycle_date());		
//			}else{
//				ps.setString(2,null);
//			}
//			if(pgdr_recycle.getDailyrecycle_week()!=null){
//				ps.setString(3,pgdr_recycle.getDailyrecycle_week());
//			}else{
//				ps.setString(3,null);
//			}
//			if(pgdr_recycle.getDailyrecycle_iscycle()!=null){
//				ps.setString(4,pgdr_recycle.getDailyrecycle_iscycle());
//			}else{
//				ps.setString(4,null);
//			}	
//			if(pgdr_recycle.getDailyrecycle_cycletype()!=null){
//				ps.setString(5,pgdr_recycle.getDailyrecycle_cycletype());
//			}else{
//				ps.setString(5,null);
//			}	
//			ps.setString(6,"1");
//			ps.setString(7,"0");
//			if(pgdr_recycle.getDailyrecycle_recyclingmanphone()!=null){
//				ps.setString(8,pgdr_recycle.getDailyrecycle_recyclingmanphone());
//			}else{
//				ps.setString(8,null);
//			}				
//			if(pgdr_recycle.getDailyrecycle_finishtime()!=null){
//				ps.setString(9,pgdr_recycle.getDailyrecycle_finishtime());
//			}else{
//				ps.setString(9,null);
//			}
//			if(pgdr_recycle.getDailyrecycle_type()!=null){
//				ps.setString(10,pgdr_recycle.getDailyrecycle_type());
//			}else{
//				ps.setString(10,null);
//			}
//			if(pgdr_recycle.getDailyrecycle_explain()!=null){
//				ps.setString(11,pgdr_recycle.getDailyrecycle_explain());
//			}else{
//				ps.setString(11,null);
//			}	
//			if(pgdr_recycle.getDailyrecycle_address()!=null){
//				String address = pgdr_recycle.getDailyrecycle_address();
//				System.out.println("====addRecycle=============55======");
//				try {
//					String addressstr = new String(address.getBytes("UTF-8"));
//					ps.setString(12,addressstr);
//					System.out.println("====UpdateUser=============66======"+addressstr);
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}	
//			}else{
//				ps.setString(12,":");
//			}
//			if(pgdr_recycle.getDailyrecycle_name()!=null){
//				ps.setString(13,pgdr_recycle.getDailyrecycle_name());
//			}else{
//				ps.setString(13,null);
//			}	
//			System.out.println("====addRecycle=============77====sql=="+ps.toString());
//			i=ps.executeUpdate();
//			if (i>0)
//			{
//				b=true;
//			}
//			else
//			{
//				b=false;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		getConn.closeconn(conn);
//		return b;		
//	}
	
	public List<Pgdr_User> selectAlluser ()
	{
		List<Pgdr_User> list=new ArrayList<Pgdr_User>();
		GetConn getConn=new GetConn();	
		Connection conn=getConn.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("select * from PGDR_USER");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) 
            {
            	Pgdr_User user=new Pgdr_User();
				user.setUser_mobile(rs.getString(1));
				list.add(user);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getConn.closeconn(conn);
		return list;
	}
//	public List<Ppdr_dailyrecycle> selectAllcontact (String username)
//	{
//		List<Ppdr_dailyrecycle> list=new ArrayList<Ppdr_dailyrecycle>();
//		GetConn getConn=new GetConn();	
//		Connection conn=getConn.getConnection();
//		try {
//			PreparedStatement ps=conn.prepareStatement(
//					"select DAILYRECYCLE_ID ,"+
//					       "DAILYRECYCLE_USER_MOBILE ,"+
//					       "DAILYRECYCLE_DATE ,"+
//					       "DAILYRECYCLE_WEEK ,"+
//					       "DAILYRECYCLE_ISCYCLE ,"+
//					       "DAILYRECYCLE_CYCLETYPE ,"+
//					       "DAILYRECYCLE_ISVALID ,"+
//					       "DAILYRECYCLE_STATUS ,"+
//					       "DAILYRECYCLE_RECYCLINGMANPHONE ,"+
//					       "DAILYRECYCLE_FINISHTIME ,"+
//					       "from PGDR_DAILYRECYCLE where DAILYRECYCLE_USER_MOBILE=? or DAILYRECYCLE_RECYCLINGMANPHONE =? and DAILYRECYCLE_ISVALID = 1  order by DAILYRECYCLE_USER_MOBILE");
//          ps.setString(1, username);
//			ResultSet rs = ps.executeQuery();
//          while (rs.next()){
//            	Ppdr_dailyrecycle ppdr_dailyrecycle=new Ppdr_dailyrecycle();
//            	ppdr_dailyrecycle.setDailyrecycle_id(rs.getString(0));
//            	ppdr_dailyrecycle.setDailyrecycle_user_mobile(rs.getString(1));
//            	ppdr_dailyrecycle.setDailyrecycle_date(rs.getString(2));
//            	ppdr_dailyrecycle.setDailyrecycle_week(rs.getString(3));
//            	ppdr_dailyrecycle.setDailyrecycle_iscycle(rs.getString(4));
//            	ppdr_dailyrecycle.setDailyrecycle_cycletype(rs.getString(5));
//            	ppdr_dailyrecycle.setDailyrecycle_isvalid(rs.getString(6));
//            	ppdr_dailyrecycle.setDailyrecycle_status(rs.getString(7));
//            	ppdr_dailyrecycle.setDailyrecycle_recyclingmanphone(rs.getString(8));
//            	ppdr_dailyrecycle.setDailyrecycle_finishtime(rs.getString(9));				
//				list.add(ppdr_dailyrecycle);
//			}
//		
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		getConn.closeconn(conn);
//		return list;
//	}
	public boolean check(String user_mobile) 
	{
		System.out.println("====check=============00======");
		boolean b = false;
		System.out.println("====check=============11======");
		GetConn getConn=new GetConn();
		System.out.println("====check=============22======");
		ResultSet rs = null;
		System.out.println("====check=============33======");
		Connection conn=getConn.getConnection();
		System.out.println("====check=============44======");
		try {
			System.out.println("====check=========55==========");
			PreparedStatement ps=conn.prepareStatement("select * from PGDR_USER where USER_MOBILE=? and USER_STATUS = '1' ");
			System.out.println("====check=========66==========");
			ps.setString(1,user_mobile);
			System.out.println("====check=========77==========");
			rs=ps.executeQuery();
			if (rs.next())
			{
				System.out.println("====check=========88==========");
				b=false;
			}
			else
			{
				System.out.println("====check=========99==========");
				int i = 0;
				PreparedStatement psin=conn.prepareStatement("insert into PGDR_USER (USER_MOBILE,USER_STATUS,USER_TYPE) values (?,1,0)");
				psin.setString(1,user_mobile);
				i=psin.executeUpdate();
				if (i>0)
				{
					b=false;
				}
				else
				{
					b=true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getConn.closeconn(conn);
		return b;
	}
	
}
