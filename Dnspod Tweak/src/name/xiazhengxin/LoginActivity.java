package name.xiazhengxin;

import java.util.HashMap;

import name.xiazhengxin.utils.Common;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author sharl
 * @date 2012-6-25 下午5:32:28
 */
public class LoginActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		final EditText userNameEditText = (EditText) findViewById(R.id.editText_UserName);
		final EditText passwordEditText = (EditText) findViewById(R.id.editText_Password);
		Button loginButton = (Button) findViewById(R.id.button_Submit);
		loginButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String userName = userNameEditText.getEditableText().toString();
				String password = passwordEditText.getEditableText().toString();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("login_email", userName);
				map.put("login_password", password);
				map.put("format", "json");
				map.put("lang", "en");
				map.put("error_on_empty", "no");
				Common common = Common.getInstance();
				String res = common.HTTPS_query(
						"https://dnsapi.cn/User.Detail", map);
				AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this)
						.create();
				dialog.setMessage(res);
				dialog.show();
			}
		});
	}
}