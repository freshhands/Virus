package per.zgk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.tomcat.util.file.Matcher;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.common.apiV2.beans.HttpPojo;
 
//import org.springframework.stereotype.Service;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Pattern;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import per.zgk.virus.bean.Province;
import util.DButil;

public class PageProcess implements PageProcessor {

	private Site site = Site.me();

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

	// 负责解析页面
	@Override
	public void process(Page page) {

		
		//page.putField("pro01", page.getHtml().xpath("//*[@id=\"getAreaStat\"]"));
	
		Html html = page.getHtml();
        //size++;
        String all = html.xpath("//*[@id=\"getAreaStat\"]").get();
        //System.out.println("all="+all);
		
		
        String reg= "window.getAreaStat = (.*?)\\}(?=catch)";
        Pattern totalPattern = Pattern.compile(reg);
        java.util.regex.Matcher totalMatcher = totalPattern.matcher(all);
 
        String result="";
        if (totalMatcher.find()){
            result = totalMatcher.group(1);
           // System.out.println(result);
            //各个省市的是一个列表List，如果想保存到数据库中，要遍历结果，下面是demo
            JSONArray array = JSONArray.parseArray(result);
            for ( int i=0;i<34;++i) {
            JSONObject jsonObject = JSONObject.parseObject(array.getString(i));
            
            String provinceName = jsonObject.getString("provinceName");
            String confirmedCount = jsonObject.getString("confirmedCount");
  		  DButil DB = new DButil();
  		Connection conn = null;
		Statement state = null;
  			PreparedStatement ps = null;
  			conn = DB.getConn();
  			
  		//String sql = "replace into real_time(name,sum,updatetome) values(?,?,?)";
  		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
         String time=df.format(new Date());
         
         String sql="update real_time set sum='" + confirmedCount+ "' , updatetime='"+ time+"' where name='"+provinceName+"'";
  		 //System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
  		  try {
  			 
  			conn = DB.getConn();
			state = conn.createStatement();
			int a = state.executeUpdate(sql);
  				
  	           if(a!=0)
  	           {
  	        	   System.out.println("更新成功");
  	           }
  	                
  	           
  	        } catch (Exception e) {
  				
  				
  				e.printStackTrace();
  			} finally {
  				DB.close(conn);
  				DB.close(ps);
  			}
           // System.out.println("provinceName："+provinceName);
            //System.out.println("confirmedCount："+confirmedCount);
            }
        }

		

	}
	
	


}
