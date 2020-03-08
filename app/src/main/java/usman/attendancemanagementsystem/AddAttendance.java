package usman.attendancemanagementsystem;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class AddAttendance extends AppCompatActivity {

    Spinner spinner;
    Firebase url,firebase,studentFirebase,studentUrl;
    String name = "";
    String date="",hour="",minute="";
    Date Phone_date=null;

    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    TextView text;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);
        Firebase.setAndroidContext(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        text = (TextView) findViewById(R.id.text);
        submit = (Button) findViewById(R.id.submit);

        list = new ArrayList<>();
        list.add("");
        ReadPhoneDate();

        url = new Firebase("https://userdata-4a398.firebaseio.com/Attendance marked/" + date);
        url.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                for(com.firebase.client.DataSnapshot snapshot : dataSnapshot.getChildren()){
                    list.add(snapshot.getKey().toString());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {

                if(!parent.getSelectedItem().toString().equals("")){
                    url = new Firebase("https://userdata-4a398.firebaseio.com/Attendance marked/" + date + "/" + parent.getSelectedItem().toString());
                    url.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                            if(dataSnapshot.hasChild("time")){
                                text.setText("Attendance Marked at " + date + " " + data.get("time"));
                            }else if(data.get("marked").toString().equals("Yes")){
                                text.setText("Leave accepted");
                            }else if(data.get("marked").toString().equals("No")){
                                text.setText("Leave rejected");
                            }
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {}
                    });
                    submit.setBackgroundColor(Color.GRAY);
                    submit.setText("Submit Attendance");
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(text.getText().toString().equals("Leave accepted")){
                                url = new Firebase("https://userdata-4a398.firebaseio.com/");
                                firebase = url.child("Admin Attendance Submission").child(date).child(parent.getSelectedItem().toString());
                                studentUrl = new Firebase("https://userdata-4a398.firebaseio.com/Students");
                                studentFirebase = studentUrl.child(parent.getSelectedItem().toString());
                                studentFirebase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                                        name = data.get("name").toString();
                                        AttendanceStatus attendanceStatus = new AttendanceStatus(name,"Present");
                                        firebase.setValue(attendanceStatus);
                                    }
                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {}
                                });

                            }else if(text.getText().toString().equals("Leave rejected")){
                                url = new Firebase("https://userdata-4a398.firebaseio.com/");
                                firebase = url.child("Admin Attendance Submission").child(date).child(parent.getSelectedItem().toString());
                                studentUrl = new Firebase("https://userdata-4a398.firebaseio.com/Students");
                                studentFirebase = studentUrl.child(parent.getSelectedItem().toString());
                                studentFirebase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                                        name = data.get("name").toString();
                                        AttendanceStatus attendanceStatus = new AttendanceStatus(name,"Absent");
                                        firebase.setValue(attendanceStatus);
                                    }
                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {}
                                });

                            }else {
                                url = new Firebase("https://userdata-4a398.firebaseio.com/");
                                firebase = url.child("Admin Attendance Submission").child(date).child(parent.getSelectedItem().toString());
                                studentUrl = new Firebase("https://userdata-4a398.firebaseio.com/Students");
                                studentFirebase = studentUrl.child(parent.getSelectedItem().toString());
                                studentFirebase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                                        name = data.get("name").toString();
                                        AttendanceStatus attendanceStatus = new AttendanceStatus(name,"Present");
                                        firebase.setValue(attendanceStatus);
                                    }
                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {}
                                });

                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public class AttendanceStatus {
        public String name;
        public String status;

        public AttendanceStatus(String name,String status) {
            this.name = name;
            this.status = status;
        }
    }

    public void ReadPhoneDate(){

        String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec" };

        Calendar calendar = Calendar.getInstance();
        String d = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String month = monthName[calendar.get(Calendar.MONTH)];
        //String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        hour = String.valueOf(calendar.get(Calendar.HOUR));
        minute = String.valueOf(calendar.get(Calendar.MINUTE));
        date = d + " " + month + " " + year;

        /*SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Phone_date = sdf.parse(d + "-" + month + "-" + year);

        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

}
