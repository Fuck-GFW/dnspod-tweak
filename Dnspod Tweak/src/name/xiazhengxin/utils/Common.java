/**
 * 
 */
package name.xiazhengxin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

/**
 * @author sharl
 * @date 2012-6-25 下午5:31:48
 * @last_modify 2012-6-28 下午6:14:42
 */
public class Common {

	final static String VERSION = "Dnspod Tweak/1.0 (x@xzx.im)";

	HashMap<String, String> commonParas = null;

	/**
	 * @return the commonParas
	 */
	public HashMap<String, String> getCommonParas() {
		return commonParas;
	}

	/**
	 * @param commonParas
	 *            the commonParas to set
	 */
	public void setCommonParas(String username, String password) {
		if (commonParas == null) {
			commonParas = new HashMap<String, String>();
			commonParas.put("format", "json");
			commonParas.put("lang", "en");
			commonParas.put("error_on_empty", "no");
		} else {

		}
		commonParas.put("login_email", username);
		commonParas.put("login_password", password);
	}

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
	public String HTTPS_query(String path, HashMap<String, String> para) {
		StringBuffer sb = new StringBuffer();
		HttpsURLConnection connection = null;
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
			URL url = new URL(path);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setSSLSocketFactory(context.getSocketFactory());
			// POST only
			connection.setRequestMethod(HttpPost.METHOD_NAME);
			// set User-Agent for identified
			connection.setRequestProperty(HTTP.USER_AGENT, VERSION);
			// set timeout & keep-alive
			connection.setConnectTimeout(10000);
			connection.setRequestProperty(HTTP.CONN_DIRECTIVE,
					HTTP.CONN_KEEP_ALIVE);
			// add parameters to request
			StringBuffer sbPara = new StringBuffer("Test=1");
			for (String s : para.keySet()) {
				sbPara.append("&" + URLEncoder.encode(s, HTTP.UTF_8) + "="
						+ URLEncoder.encode(para.get(s), HTTP.UTF_8));
			}
			connection.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream(), HTTP.UTF_8);
			writer.write(sbPara.toString());
			writer.flush();
			// query
			InputStream stream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream, HTTP.UTF_8));
			// get content from response
			String data = null;
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
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return sb.toString();
	}

	public String getReturnCode(String json) {
		String code = "";
		// {"status":{"code":"1","message":"4.4","created_at":"2012-07-02 17:40:01"}}
		JSONTokener tokener = new JSONTokener(json);
		JSONObject object = null;
		try {
			object = new JSONObject(tokener).getJSONObject("status");
			code = object.getString("code");
		} catch (JSONException e) {
			logException(e);
		}
		return code;
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

	public String Base64Encode(String s) {
		return new String(Base64.encodeBase64(s.getBytes()));
	}

	public String Base64Decode(String s) {
		return new String(Base64.decodeBase64(s.getBytes()));
	}

}
