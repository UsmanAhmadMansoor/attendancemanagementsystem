package usman.attendancemanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminMenu extends AppCompatActivity {

    Button addAttendance,deleteAttendance,viewReport,viewReq;
    TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        addAttendance = (Button) findViewById(R.id.add);
        //deleteAttendance = (Button) findViewById(R.id.del);
        viewReport = (Button) findViewById(R.id.viewReport);
        viewReq = (Button) findViewById(R.id.viewReq);
        logout = (TextView) findViewById(R.id.logout);

        addAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AdminMenu.this,AddAttendance.class);
                startActivity(it);
            }
        });
        /*deleteAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                MarkLeave d = new MarkLeave();
                d.show(manager,"Dialog");
                d.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
            }
        });*/
        viewReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AdminMenu.this,ViewRequest.class);
                startActivity(it);
            }
        });

        viewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AdminMenu.this,ViewAttendance.class);
                startActivity(it);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AdminMenu.this,AdminLoginForm.class);
                startActivity(it);
            }
        });
    }
}


