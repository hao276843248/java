package com.hao.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 查询国服，韩服前200名王者；
 * 
 * */
public class Start {

	
	public static String htmlAll="";
	public static int i=1;
	public static void main(String[] args) throws IOException  {
		File html =new File("/html.txt");
//		String sctdoc=readFileRow(html);
//		http://www.laoyuegou.com/x/zh-cn/lol/lol/godrank.html?region=foreign&area=kr
		String url="http://www.laoyuegou.com/x/zh-cn/lol/lol/godrank.html?region=foreign&area=kr";
		if (!html.exists()) {
			html.createNewFile();
		}
		FileWriter fw = new FileWriter(html.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String srcString=getHtml(url);
		String flg="";
		try {
			System.out.println("flg:"+flg);
			while (srcString!=null && !flg.equals(srcString)){
				System.out.println(srcString);
				flg=srcString;
				srcString=getHtml(srcString);
			}
		} catch (Exception e) {
			
		}
		System.out.println("写入文件");
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
		Elements elementsByClass = doc.getElementsByClass("row");
		System.out.println(elementsByClass);
		String index="";
		String name="";
		String val="";
	
		for (Element link : elementsByClass) {
			Elements itme1s=link.getElementsByClass("item1");
			for (Element e:itme1s) {
				index="国服第"+e.text()+":";
			}
			Elements itme2s=link.getElementsByClass("subStrTitle");
			for (Element e:itme2s) {
				name=e.text();
			}
			Elements itme3s=link.getElementsByTag("em");
			val="\t\t\t胜点"+itme3s.get(0).text();
			
			Elements itme7s=link.getElementsByClass("item7");
			String yx="\t\t\t擅长英雄";
			for (Element e:itme7s) {
				Elements imgs=e.getElementsByTag("img");
				for (Element img:imgs) {
					yx+="--"+img.attr("alt");
				}
			}
			htmlAll+=index+name+val+yx+"\r\n";
			System.out.println(htmlAll);
		}
//		System.out.println(elementsByClass);
//		for (Element link : elementsByClass) {
//			 String linkText = link.text();
//			 System.out.println("国服第"+i+":"+linkText);
//			 htmlAll+="国服第"+i+":"+linkText+"\r\n";
//			 i++;
//		}
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
					sbBuffer.append(temp);
				}
				reader.close();	
			}
		} catch (IOException e2) {
		}
		return sbBuffer.toString();
	}
	
	
}
