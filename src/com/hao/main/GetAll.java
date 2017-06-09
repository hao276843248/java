package com.hao.main;

public class GetAll {

	public static void main(String[] args) {
		int targetType;
		targetType = Integer.parseInt("09");
		System.out.println(targetType);
//		move(5,"A","B","C");
//		def talks_robot(info = '你叫什么名字'):
//		    api_url = 'http://www.tuling123.com/openapi/api'
//		    apikey = 'ff65f5d5c2a14ca4bf495e6f45b83568'
//		    data = {'key': apikey,
//		                'info': info}
//		    req = requests.post(api_url, data=data).text
//		    replys = json.loads(req)['text']
//		    return replys
		int a=38045+83889;
		System.out.println(a);
	}
	
	
	
	
	public static void move(int n,String a,String b,String c){
		if(n<=1){
			System.out.println(a+"-->"+c);
		}else{
			move(n-1, a, c, b);
			move(1, a, b, c);
			move(n-1, b, a, c);
		}
	}
}
