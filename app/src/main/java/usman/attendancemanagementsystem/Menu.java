package usman.attendancemanagementsystem;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    Button markAttendance,markLeave,viewAttendance;
    TextView logout;
    String regNo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        markAttendance = (Button) findViewById(R.id.markAttendance);
        markLeave = (Button) findViewById(R.id.markLeave);
        //viewAttendance = (Button) findViewById(R.id.view);
        logout = (TextView) findViewById(R.id.logout);



        Intent intent = getIntent();
        regNo = intent.getStringExtra("reg");

        markAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                MarkAttendance d = new MarkAttendance();
                Bundle bundle = new Bundle();
                bundle.putString("reg",regNo);
                d.setArguments(bundle);
                d.show(manager,"Dialog");
                d.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
            }
        });
        markLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                MarkLeave d = new MarkLeave();
                Bundle bundle = new Bundle();
                bundle.putString("reg",regNo);
                d.setArguments(bundle);
                d.show(manager,"Dialog");
                d.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
            }
        });
        /*viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Menu.this,ViewAttendance.class);
                startActivity(it);
            }
        });*/
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Menu.this,LoginForm.class);
                startActivity(it);
            }
        });
    }
}

