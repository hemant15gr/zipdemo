package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class test implements RequestHandler<String, String>{

	public String handleRequest(String arg0, Context arg1) {
		// System.out.println("hi");
		/*String empNo="";
		String urlPrefix = "jdbc:db2:";
		String url="jdbc:db2://63.35.201.137:50000/testdb";
		String user="db2inst1";
		String password="gslab123";
		Connection con=null;
		Statement stmt;
		ResultSet rs;
		try{
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			con = DriverManager.getConnection (url, user, password);
			stmt = con.createStatement(); 
		      rs = stmt.executeQuery("select * from ONELOGIN.EMPLOYEE"); 
		      while (rs.next()) {
		           empNo = rs.getString(2);
		        }

		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(con!=null){
				System.out.println("Connected successfully.");
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
		String accessToken="";
		CloseableHttpClient client = HttpClientBuilder.create().build();

		HttpPost request = new HttpPost("https://api.us.onelogin.com/auth/oauth2/v2/token");

		String credentials = String.format("%s:%s", "9c165c70477a7f5bca94752568d4ea08163f3e783b08ab4fc9275dbc0ad32f99" , "e6d3a9d955d9f201d91975716f586b0755d0207cbbbb64f0a9668cda6a5a5f27");
		byte[] encodedAuth = Base64.getEncoder().encode(credentials.getBytes());
		String authHeader = "Basic " + new String(encodedAuth);

		request.setHeader("Authorization", authHeader);
		request.addHeader("Content-Type", "application/json");
		request.setEntity(new StringEntity("{ \"grant_type\": \"client_credentials\" }", "UTF-8"));

		try {
		  CloseableHttpResponse reponse = client.execute(request);

		  String content = EntityUtils.toString(reponse.getEntity());

		  JSONObject json = new JSONObject(content);

		  accessToken = json.getString("access_token");

		  System.out.println(accessToken);

		} catch (IOException e) {
		    e.printStackTrace();
		}
		return accessToken;
		      
	}
}
