package com.example.demo.utils;


/**
 * restful接口返回对象
 */
public class RestResponse {
	/**http状态码*/
    private HttpCode code;
    /**需要返回的对象*/
    private Object data;
    /**跳转地址,有些参数时,会跳转页面,并在新页面弹出提示消息*/
    private String url;
    /**提示信息*/
    private String message;

    public RestResponse(){
    	this(HttpCode.OK, "");
    }
    
    public RestResponse(Object data, HttpCode code, String url, String message) {
    	this.code = code;
        this.data = data;
        this.url = url;
        this.message = message;
    }

    public RestResponse(Object data) {
    	this(data, HttpCode.OK, "", "");
    }

    public RestResponse(String message) {
        this(null, HttpCode.OK, "", message);
    }
    
    public RestResponse(String url, String message){
    	this(null, HttpCode.OK, url, message);
    }
    
    public RestResponse(HttpCode code , String message){
    	this(code,null,message);
    }
    
    public RestResponse(HttpCode code , String url , String message){
    	this(null,code,url,message);
    }
    
    public RestResponse(Object data , String url , String message){
    	this(data,HttpCode.OK,url,message);
    }
    
	public HttpCode getCode() {
		return code;
	}

	public void setCode(HttpCode code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
