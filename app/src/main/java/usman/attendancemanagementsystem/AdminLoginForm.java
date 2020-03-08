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

public class AdminLoginForm extends AppCompatActivity {

    EditText un,password;
    Button login;
    TextView tv;
    Firebase url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_form);
        Firebase.setAndroidContext(this);
        un = (EditText) findViewById(R.id.un);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        tv = (TextView) findViewById(R.id.tv);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(un.getText().toString())){

                url = new Firebase("https://userdata-4a398.firebaseio.com/AdminData");
                url.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(un.getText().toString())) {
                            if(!TextUtils.isEmpty(password.getText().toString())){
                               url.child(un.getText().toString()).addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                       Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                                       if (data.get("password").toString().equals(password.getText().toString())) {
                                           Intent it = new Intent(AdminLoginForm.this, AdminMenu.class);
                                           startActivity(it);
                                       } else {
                                           Toast.makeText(AdminLoginForm.this, "Wrong Password", Toast.LENGTH_LONG).show();
                                       }
                                   }
                                   @Override
                                   public void onCancelled(FirebaseError firebaseError) {}
                               });
                            }else {
                                Toast.makeText(AdminLoginForm.this, "Enter Password", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(AdminLoginForm.this, "Invalid User Name", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {}
                });
            }else {
                    Toast.makeText(AdminLoginForm.this,"Enter Your User Name", Toast.LENGTH_LONG).show();
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AdminLoginForm.this,AdminSignUpForm.class);
                startActivity(it);
            }
        });
    }
}



