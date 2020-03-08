package usman.attendancemanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class SignUpForm extends AppCompatActivity {

    EditText name,reg,password;
    Button submit;
    Firebase url,firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);
        Firebase.setAndroidContext(this);
        name = (EditText) findViewById(R.id.name);
        reg = (EditText) findViewById(R.id.reg);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);
        url = new Firebase("https://userdata-4a398.firebaseio.com/");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(reg.getText().toString())) {
                    firebase = url.child("Students").child(reg.getText().toString());
                    User user = new User(name.getText().toString(), password.getText().toString());
                    firebase.setValue(user);

                    Intent it = new Intent(SignUpForm.this, Menu.class);
                    startActivity(it);
                }else {
                    Toast.makeText(SignUpForm.this, "Fields are empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


