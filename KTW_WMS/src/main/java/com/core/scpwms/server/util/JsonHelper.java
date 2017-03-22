package com.core.scpwms.server.util;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;



public class JsonHelper {
    public static String toJsonString(Object obj, String[] includs){
    	JSONSerializer serializer = new JSONSerializer();
    	if( includs == null )
    		return new JSONSerializer().serialize(obj);
    	return new JSONSerializer().include(includs).serialize(obj);
    }
	
	public static String convertToJsonObject(Object obj) {
    	JSONSerializer serializer = new JSONSerializer();
    	return new JSONSerializer().include("head","details").serialize(obj);
    }
    
    public static Object toJavaObject(Object obj, String jsonStr) {
    	JSONSerializer serializer = new JSONSerializer();
        return new JSONDeserializer().deserialize(jsonStr,obj.getClass());
    }
    
    public static Object toJavaObjectByName(String className, String jsonStr) {
    	
    	Object result = null;
    	
    	try{
        	JSONSerializer serializer = new JSONSerializer();
			result = Class.forName(className).newInstance();
			result = new JSONDeserializer().deserialize(jsonStr,result.getClass());
    	}catch(ClassNotFoundException e){
    		e.printStackTrace();
    	} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return result;
    }    

}
