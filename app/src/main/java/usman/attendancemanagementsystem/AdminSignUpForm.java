package usman.attendancemanagementsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class AdminSignUpForm extends AppCompatActivity {

    EditText name,un,password;
    Button submit;
    Firebase url,firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up_form);
        Firebase.setAndroidContext(this);
        name = (EditText) findViewById(R.id.name);
        un = (EditText) findViewById(R.id.un);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);
        url = new Firebase("https://userdata-4a398.firebaseio.com/");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(un.getText().toString())) {
                    firebase = url.child("AdminData").child(un.getText().toString());
                    User user = new User(name.getText().toString(), password.getText().toString());
                    firebase.setValue(user);

                    //Intent it = new Intent(AdminSignUpForm.this, Menu.class);
                    //startActivity(it);
                }else {
                    Toast.makeText(AdminSignUpForm.this, "Fields are empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}




