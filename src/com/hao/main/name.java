package com.hao.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 查询匹配到的所有人 
 * 
 * http://www.laoyuegou.com/x/zh-cn/lol/lol/player.html?globalId=23000214879&type=-1
 * 
 * */
public class name {
	
	public static String htmlAll="";
	public static List<String> userList=new ArrayList<String>();
	public static Map<String, Integer> map = new HashMap<String, Integer>();
	public static int i=1;
	public static void main(String[] args) throws IOException  {
		File html =new File("/html.txt");
//		String sctdoc=readFileRow(html);
//		http://www.laoyuegou.com/x/zh-cn/lol/lol/godrank.html?region=foreign&area=kr
		String url="http://www.laoyuegou.com/x/zh-cn/lol/lol/player.html?globalId=23000214879&type=-1&page=52";
		if (!html.exists()) {
			html.createNewFile();
		}
		FileWriter fw = new FileWriter(html.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
//		String srcString=getHtml(url);
//		String flg="";
//		try {
//			System.out.println("flg:"+flg);
//			while (srcString!=null && !flg.equals(srcString)){
//				System.out.println(srcString);
//				flg=srcString;
//				srcString=getHtml(srcString);
//			}
//		} catch (Exception e) {
//			
//		}
//		System.out.println("写入文件");
//		bw.write(htmlAll);
//		bw.close();
		
		readFileRow(html);
		File html2 =new File("/1-52页场匹配玩家.txt");
		readFileRow(html2);
//		System.out.println("写入文件");
		pipeiCount(null);
		bw.write(htmlAll);
		bw.close();
		
	}
	
	/**
	 * 获取HTML信息
	 * 
	 * */
	public static String getHtml(String url){
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println("time out");
		}
		Elements elementsByClass = doc.getElementsByClass("record-item");
		
		for (Element e:elementsByClass) {
			Elements username = e.getElementsByTag("h6");
			for (Element name:username) {
				if(!"CHN丶魑魅丿".equals(name.text())){
					System.out.println(name.text());
					userList.add(name.text());
					htmlAll+=name.text()+"\r\n";
				}
			}
		}
		Elements links = doc.getElementsByTag("a");
		for (Element link : links) {
		  String linkHref = link.attr("href");
		  String linkText = link.text();
		  if("下一页".equals(linkText)){
			  url=linkHref;
		  }
		}
		return url;
	}
	
	
	/**
	 * 获得要读取所有
	 * 
	 * @param file
	 * @return String
	 * **/
	public static String readFileRow(File file) {
		BufferedReader reader = null;
		StringBuffer sbBuffer=new StringBuffer();
		String temp=null;
		try {
			if (file.exists()) {
				reader = new BufferedReader(new FileReader(file));
				while ((temp=reader.readLine())!=null) {
					userList.add(temp);
				}
				reader.close();	
			}
		} catch (IOException e2) {
		}
		return sbBuffer.toString();
	}
	
	
	public static List<String> pipeiCount(File file){
		for(String item: userList){
			if(map.containsKey(item)){
				map.put(item, map.get(item).intValue() + 1);
			}else{
				map.put(item, new Integer(1));
			}
		}
		Iterator<String> keys = map.keySet().iterator();
		htmlAll="";
		while(keys.hasNext()){
			String key = keys.next();
			System.out.print(key + ":" + map.get(key).intValue() + ", ");
			htmlAll+="队友ID:"+key + "\t\t匹配到一块的场次:" + map.get(key).intValue()/2 +"\r\n";
		}
		
		return userList;
	}
	
	
	
}
