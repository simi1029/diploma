package hu.ppke.simda.musiclibraryandroidonly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = (EditText)findViewById(R.id.username_text_field);
        final EditText password = (EditText)findViewById(R.id.passwd_text_field);

        username.setText("test");
        Button loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("test") && password.getText().toString().equals("pwd")) {
                    Intent mainActivity = new Intent(getApplicationContext(), SongListActivity.class);
                    startActivity(mainActivity);
                } else { Toast.makeText(getApplicationContext(), "Invalid username/password",Toast.LENGTH_SHORT).show(); }
            }
        });
    }

}
