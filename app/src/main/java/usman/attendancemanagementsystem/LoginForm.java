package usman.attendancemanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class LoginForm extends AppCompatActivity {

    EditText reg,password;
    Button login;
    TextView tv;
    Firebase url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        Firebase.setAndroidContext(this);
        reg = (EditText) findViewById(R.id.reg);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        tv = (TextView) findViewById(R.id.tv);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(reg.getText().toString())){

                url = new Firebase("https://userdata-4a398.firebaseio.com/Students");
                url.addValueEventListener(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(reg.getText().toString())) {
                            if(!TextUtils.isEmpty(password.getText().toString())){
                            url.child(reg.getText().toString()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                                    if (data.get("password").toString().equals(password.getText().toString())) {
                                        Intent it = new Intent(LoginForm.this, Menu.class);
                                        it.putExtra("reg",reg.getText().toString());
                                        startActivity(it);
                                    } else {
                                        Toast.makeText(LoginForm.this, "Wrong Password", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                }
                            });
                        }else {
                                Toast.makeText(LoginForm.this, "Enter Password", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(LoginForm.this,"Invalid Registration NO", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {}
                });
            }else {
                    Toast.makeText(LoginForm.this,"Enter Your Registration NO", Toast.LENGTH_LONG).show();
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginForm.this,SignUpForm.class);
                startActivity(it);
            }
        });
    }
}



