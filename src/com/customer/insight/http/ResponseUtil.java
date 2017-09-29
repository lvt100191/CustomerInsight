package com.customer.insight.http;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;


public class ResponseUtil
{
    String timeout = "5000";
     private final String USER_AGENT = "Mozilla/5.0";
 
    public ResponseUtil() { }
    
    // http get
    public  static String sendGet(String url)  throws Exception
    {
        //applog.debug("getResponse|STEP1");
        URL sendUrl = new URL(url);
        URLConnection urlCon = sendUrl.openConnection();
        urlCon.setDoOutput(true);
        urlCon.setDoInput(true);
        HttpURLConnection httpConnection = (HttpURLConnection) urlCon;
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        httpConnection.setRequestProperty("Content-Length", "0");
       // httpConnection.setConnectTimeout(Integer.parseInt(timeout));
       // httpConnection.setReadTimeout(Integer.parseInt(timeout));

        String str = httpConnection.getResponseMessage();
        BufferedReader br = null;
        InputStream is = null;
        if (str.equalsIgnoreCase("OK"))
        {
        is = httpConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is,"utf-8");
        br = new BufferedReader(isr);
        }
        else {
        is = httpConnection.getErrorStream();
        //is = httpConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is,"utf-8");
        br = new BufferedReader(isr);
        }
        str = "";

        StringBuffer sb = new StringBuffer();
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        is.close();
        return sb.toString();
    }
    
    // http get co timeout
    public String sendGetTimeout(String url, int timeout)  throws Exception
    {
        URL sendUrl = new URL(url);
        URLConnection urlCon = sendUrl.openConnection();
        urlCon.setDoOutput(true);
        urlCon.setDoInput(true);
        HttpURLConnection httpConnection = (HttpURLConnection) urlCon;
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
        httpConnection.setRequestProperty("Content-Length", "0");
        httpConnection.setConnectTimeout(timeout);
        httpConnection.setReadTimeout(timeout);
        
         String str = httpConnection.getResponseMessage();
        BufferedReader br = null;
        InputStream is = null;
        if (str.equalsIgnoreCase("OK"))
        {
        is = httpConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is,"utf-8");
        br = new BufferedReader(isr);
        }
        else {
        is = httpConnection.getErrorStream();
        //is = httpConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is,"utf-8");
        br = new BufferedReader(isr);
        }
        str = "";

        StringBuffer sb = new StringBuffer();
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }

        is.close();
        return sb.toString();
    }
    
    // mac dinh
    public String sendPost(String request, String url)  throws Exception
    {
        URL sendUrl = new URL(url);
        URLConnection urlCon = sendUrl.openConnection();
        urlCon.setDoOutput(true);
        urlCon.setDoInput(true);
        HttpURLConnection httpConnection = (HttpURLConnection) urlCon;
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type", "application/json");
        //httpConnection.setRequestProperty("Content-Length", Integer.toString(request.length()));
        httpConnection.setConnectTimeout(Integer.parseInt(timeout));
        httpConnection.setReadTimeout(Integer.parseInt(timeout));
        PrintStream ps = null;

        ps = new PrintStream(httpConnection.getOutputStream(),true,"utf-8");
        ps.write(request.getBytes("utf-8"));
        ps.flush();
        String str = httpConnection.getResponseMessage();
        InputStream is = httpConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is,"utf-8");
        BufferedReader br = new BufferedReader(isr);
        str = "";

        StringBuffer sb = new StringBuffer();
        while ( (str = br.readLine()) != null) {
            sb.append(str);
        }
        is.close();
        return sb.toString();
    }
    
    // ham co su dung timeout
    public String sendPostTimeout(String request, String url, int timeout)  throws Exception
    {
        URL sendUrl = new URL(url);
        URLConnection urlCon = sendUrl.openConnection();
        urlCon.setDoOutput(true);
        urlCon.setDoInput(true);
        HttpURLConnection httpConnection = (HttpURLConnection) urlCon;
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
        httpConnection.setRequestProperty("Content-Length", Integer.toString(request.length()));
        httpConnection.setConnectTimeout(timeout);
        httpConnection.setReadTimeout(timeout);
        
        PrintStream ps = null;

        ps = new PrintStream(httpConnection.getOutputStream(),true,"utf-8");
        ps.write(request.getBytes("utf-8"));
        ps.flush();
        String str = httpConnection.getResponseMessage();
        InputStream is = httpConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is,"utf-8");
        BufferedReader br = new BufferedReader(isr);
        str = "";

        StringBuffer sb = new StringBuffer();
        while ( (str = br.readLine()) != null) {
            sb.append(str);
        }

        is.close();
        return sb.toString();
    }
    	public  String sendPost(String url) throws Exception {
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();

	}
    
    
}
