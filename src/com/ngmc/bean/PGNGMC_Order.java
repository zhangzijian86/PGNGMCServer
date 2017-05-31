package com.ngmc.bean;

public class PGNGMC_Order {
	
	private String ORDER_ID;          //订单ID
	private String ORDER_Code;        //订单编码
	private String USER_ID;           //用户ID
	private String BIKE_ID;           //自行车ID
	private String ORDER_StartDate;   //开始日期
	private String ORDER_EndtDate;    //结束日期
	private String ORDER_Status;      //用户状态(0正常 1车辆故障停止 -1删除)  
	private String ORDER_Price;       //费用
	private String ORDER_PriceStatus; //费用状态(0未交 1已交)
	
	//select ORDER_ID, ORDER_Code,USER_ID,BIKE_ID,ORDER_StartDate
	//ORDER_EndtDate,ORDER_Status,ORDER_Price,ORDER_PriceStatus	

	public String getORDER_ID() {
		return ORDER_ID;
	}
	public void setORDER_ID(String oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}
	public String getORDER_Code() {
		return ORDER_Code;
	}
	public void setORDER_Code(String oRDER_Code) {
		ORDER_Code = oRDER_Code;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getBIKE_ID() {
		return BIKE_ID;
	}
	public void setBIKE_ID(String bIKE_ID) {
		BIKE_ID = bIKE_ID;
	}
	public String getORDER_StartDate() {
		return ORDER_StartDate;
	}
	public void setORDER_StartDate(String oRDER_StartDate) {
		ORDER_StartDate = oRDER_StartDate;
	}
	public String getORDER_EndtDate() {
		return ORDER_EndtDate;
	}
	public void setORDER_EndtDate(String oRDER_EndtDate) {
		ORDER_EndtDate = oRDER_EndtDate;
	}
	public String getORDER_Status() {
		return ORDER_Status;
	}
	public void setORDER_Status(String oRDER_Status) {
		ORDER_Status = oRDER_Status;
	}
	public String getORDER_Price() {
		return ORDER_Price;
	}
	public void setORDER_Price(String oRDER_Price) {
		ORDER_Price = oRDER_Price;
	}
	public String getORDER_PriceStatus() {
		return ORDER_PriceStatus;
	}
	public void setORDER_PriceStatus(String oRDER_PriceStatus) {
		ORDER_PriceStatus = oRDER_PriceStatus;
	}
}
