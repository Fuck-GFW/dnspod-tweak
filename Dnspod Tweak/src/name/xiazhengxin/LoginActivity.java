package name.xiazhengxin;

import name.xiazhengxin.utils.Common;
import android.app.Activity;
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
				Common common = Common.getInstance();
				common.HTTPS_query("");
			}
		});
	}
}