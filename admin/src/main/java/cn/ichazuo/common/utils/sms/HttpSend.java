package cn.ichazuo.common.utils.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpSend {
	String urlString;

	public HttpSend(String urlString) {
		this.urlString = urlString;
	}

	public void send() throws Exception {
		// 生成一个URL对象
		URL url = new URL(urlString);
		// 打开URL
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		// 得到输入流，即获得返回值
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String line;
		// 读取返回值，进行判断
		while ((line = reader.readLine()) != null) {
			int result = Integer.valueOf(line);
			if (result == 0) {
				System.out.println("发送成功");
			}
		}
	}
	
	public void sendPost(String content) throws Exception {
		HttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpost = new HttpPost(urlString);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("c", content)); // 用户名称
			httpost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			System.out.println(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}