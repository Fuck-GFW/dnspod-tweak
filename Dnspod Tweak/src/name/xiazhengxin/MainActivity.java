/**
 * 
 */
package name.xiazhengxin;

import java.util.HashMap;

import name.xiazhengxin.utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author sharl
 * 
 */
public class MainActivity extends Activity {

	HashMap<String, String> detail = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Context c = this;
		Common common = Common.getInstance();
		String url = "https://dnsapi.cn/User.Detail";
		// query user detail
		String json = common.HTTPS_query(url, common.getCommonParas());
		if ("1".equals(common.getReturnCode(json))) {
			try {
				initUI(json);
			} catch (JSONException e) {
				common.logException(e);
			}
			registerControl();
		}
	}

	public void registerControl() {
		Button exitButton = (Button) findViewById(R.id.button_Exit);
		exitButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				System.exit(0);
			}
		});
	}

	public void initUI(String s) throws JSONException {
		TextView userNameTextView = (TextView) findViewById(R.id.textView_UserName);
		TextView userTypeTextView = (TextView) findViewById(R.id.textView_UserType);
		TextView userStatusTextView = (TextView) findViewById(R.id.textView_User_Status_Value);
		TextView emailStatusTextView = (TextView) findViewById(R.id.textView_Email_Status_Value);
		TextView cellphoneStatusTextView = (TextView) findViewById(R.id.textView_Cellphone_Status_Value);
		JSONObject object = new JSONObject(s).getJSONObject("info")
				.getJSONObject("user");
		detail.put("id", object.getString("id"));
		detail.put("email", object.getString("email"));
		detail.put("status", object.getString("status"));
		detail.put("email_verified", object.getString("email_verified"));
		detail.put("telephone_verified", object.getString("telephone_verified"));
		detail.put("agent_pending", object.getString("agent_pending"));
		detail.put("real_name", object.getString("real_name"));
		detail.put("user_type", object.getString("user_type"));
		detail.put("telephone", object.getString("telephone"));
		detail.put("im", object.getString("im"));
		detail.put("nick", object.getString("nick"));
		detail.put("balance", object.getString("balance"));
		detail.put("smsbalance", object.getString("smsbalance"));
		// draw UI
		userNameTextView.setText(detail.get("real_name"));
		userTypeTextView
				.setText("personal".equals(detail.get("user_type")) ? R.string.main_user_type_personal
						: R.string.main_user_type_company);
		userStatusTextView
				.setText("enabled".equals(detail.get("status")) ? R.string.main_user_status_enable
						: R.string.main_user_status_disable);
		emailStatusTextView
				.setText("yes".equals(detail.get("email_verified")) ? R.string.main_email_status_enable
						: R.string.main_email_status_disable);
		cellphoneStatusTextView
				.setText("yes".equals(detail.get("telephone_verified")) ? R.string.main_cellphone_status_enable
						: R.string.main_cellphone_status_disable);
	}

}
