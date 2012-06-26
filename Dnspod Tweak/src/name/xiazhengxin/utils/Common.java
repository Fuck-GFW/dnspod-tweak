/**
 * 
 */
package name.xiazhengxin.utils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * @author sharl
 * @date 2012-6-25 下午5:31:48
 */
public class Common {

	static Common w = null;

	private Common() {
		// make it singleton
	}

	public static Common getInstance() {
		if (w == null) {
			w = new Common();
		} else {
			// do nothing
		}
		return w;
	}

	/**
	 * @author sharl
	 * @date 2012-6-26 下午5:36:33
	 * @param url
	 * @param domain
	 * @return
	 */
	public HttpResponse HTTPS_query(String url, String domain) {
		// register the scheme
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		registry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
		// initiate HTTP client
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpHost host = new HttpHost(domain, 443, "https");
		HttpContext context = new BasicHttpContext();
		try {
			client.execute(host, get, context);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void log() {

	}

}
