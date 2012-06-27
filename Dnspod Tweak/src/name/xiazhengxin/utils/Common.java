/**
 * 
 */
package name.xiazhengxin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;

import android.util.Log;

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
	 * @last_modify 2012-6-27 下午6:49:06
	 * @param url
	 * @return string
	 * @description Use HttpsConnection class to perform HTTPS request,more
	 *              details see:
	 *              http://developer.android.com/reference/javax/net
	 *              /ssl/HttpsURLConnection.html
	 */
	public HttpResponse HTTPS_query(String url) {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { new X509TrustManager() {

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}
			} }, null);
			URL path = new URL(url);
			HttpsURLConnection connection = (HttpsURLConnection) path
					.openConnection();
			connection.setSSLSocketFactory(context.getSocketFactory());
			InputStream stream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream, "utf-8"));
			String data;
			StringBuffer sb = new StringBuffer();
			while ((data = reader.readLine()) != null) {
				sb.append(data);
			}

		} catch (NoSuchAlgorithmException e) {
			logException(e);
		} catch (KeyManagementException e) {
			logException(e);
		} catch (MalformedURLException e) {
			logException(e);
		} catch (IOException e) {
			logException(e);
		}
		return null;
	}

	public void log(int level, String tag, String msg) {
		if (level == Log.DEBUG) {
			Log.d(tag, msg);
		} else {
			Log.e(tag, msg);
		}
	}

	public void logException(Exception e) {
		log(Log.ERROR, "", e.getMessage());
		for (StackTraceElement element : e.getStackTrace()) {
			log(Log.ERROR, "", element.toString());
		}
	}

}
