package name.xiazhengxin;

import java.util.HashMap;

import name.xiazhengxin.utils.Common;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author sharl
 * @date 2012-6-25 下午5:32:28
 * @description Login Form
 */
public class LoginActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		final Common common = Common.getInstance();
		final Context c = this;
		// get controls
		final EditText userNameEditText = (EditText) findViewById(R.id.editText_UserName);
		final EditText passwordEditText = (EditText) findViewById(R.id.editText_Password);
		final CheckBox saveCheckBox = (CheckBox) findViewById(R.id.checkBox_SaveUser);
		Button loginButton = (Button) findViewById(R.id.button_Submit);

		// restore state of controls
		SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
		if (sp.getBoolean("save", false)) {
			userNameEditText.setText(sp.getString("userName", "UserName"));
			passwordEditText.setText(common.Base64Decode(sp.getString(
					"password", "Password")));
			saveCheckBox.setChecked(true);
		} else {

		}

		loginButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String userName = userNameEditText.getEditableText().toString();
				String password = passwordEditText.getEditableText().toString();
				SharedPreferences.Editor editor = getSharedPreferences("login",
						MODE_PRIVATE).edit();
				if (saveCheckBox.isChecked()) {
					editor.putBoolean("save", true);
					editor.putString("userName", userName);
					// encode the password via Base64
					editor.putString("password", common.Base64Encode(password));
				} else {
					editor.clear();
				}
				editor.commit();
				// save user auth data to session
				common.setCommonParas(userName, password);
				HashMap<String, String> map = common.getCommonParas();
				String res = common.HTTPS_query(
						"https://dnsapi.cn/Info.Version", map);
				String code = "";
				try {
					// {"status":{"code":"1","message":"4.4","created_at":"2012-07-02 17:40:01"}}
					JSONTokener tokener = new JSONTokener(res);
					JSONObject object = new JSONObject(tokener)
							.getJSONObject("status");
					code = object.getString("code");
				} catch (JSONException e) {
					common.logException(e);
				}
				// login success or not
				if ("1".equals(code)) {
					// go to main UI
					startActivity(new Intent(c, MainActivity.class));
					finish();
				} else {
					Toast.makeText(c, R.string.login_err, Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}
}