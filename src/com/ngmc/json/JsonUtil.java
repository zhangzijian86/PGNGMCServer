package com.ngmc.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ngmc.bean.Pgdr_User;


public class JsonUtil {
	public List<Pgdr_User> StringFromJson (String jsondata)
	{     
		Type listType = new TypeToken<List<Pgdr_User>>(){}.getType();
		Gson gson=new Gson();
		ArrayList<Pgdr_User> list=gson.fromJson(jsondata, listType);
		return list;

	}
//	public List<Ppdr_dailyrecycle> StringFromJsonRecycle (String jsondata)
//	{     
//		Type listType = new TypeToken<List<Ppdr_dailyrecycle>>(){}.getType();
//		Gson gson=new Gson();
//		ArrayList<Ppdr_dailyrecycle> list=gson.fromJson(jsondata, listType);
//		return list;
//
//	}
}
