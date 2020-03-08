package usman.attendancemanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class choosePanel extends AppCompatActivity {

    Button std_login,admin_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_panel);
        std_login = (Button) findViewById(R.id.sl);
        admin_login = (Button) findViewById(R.id.al);

        std_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(choosePanel.this,LoginForm.class);
                startActivity(it);
            }
        });
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(choosePanel.this,AdminLoginForm.class);
                startActivity(it);
            }
        });

    }

}
