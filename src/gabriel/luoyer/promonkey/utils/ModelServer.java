package gabriel.luoyer.promonkey.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import android.util.Log;

public class ModelServer {
	
	private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
	
	/**
	 * 通用型 http访问网址
	 * @param params 参数
	 * @param urlStr 需要访问的url
	 * @return	  是否正常,返回的结果
	 */
	public  Map<Boolean,String> Model_server(List<NameValuePair> params,String urlStr)     
    {
		Map<Boolean,String> reponseMessage =new HashMap<Boolean,String>();
        HttpPost request = new HttpPost("http://192.168.0.155:8080/NFC_Library/NFC_Library/"+urlStr);
        try
        {
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpClient client = getHttpClient();
            HttpResponse response = client.execute(request);
            if(response.getStatusLine().getStatusCode()==200)
            {
            	reponseMessage.put(true,  EntityUtils.toString(response.getEntity()));
                Log.i("12345"," ModelServer 返回"+EntityUtils.toString(response.getEntity()));
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return reponseMessage;
    }
	
	
    //初始化HttpClient，并设置超时
    public  HttpClient getHttpClient()
    {
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
        HttpClient client = new DefaultHttpClient(httpParams);
        return client;
    }
	
}
