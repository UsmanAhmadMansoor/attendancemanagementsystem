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

public class ViewRequest extends AppCompatActivity {

    Spinner spinner;
    Firebase url,firebase;
    String date="",hour="",minute="";
    Date Phone_date=null;

    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    TextView text;
    Button approve,reject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        Firebase.setAndroidContext(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        text = (TextView) findViewById(R.id.text);
        approve = (Button) findViewById(R.id.approve);
        reject = (Button) findViewById(R.id.reject);

        list = new ArrayList<>();
        list.add("");
        ReadPhoneDate();

        url = new Firebase("https://userdata-4a398.firebaseio.com/Student Leave Requests/" + date);
        url.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
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
                    url = new Firebase("https://userdata-4a398.firebaseio.com/Student Leave Requests/" + date + "/" + parent.getSelectedItem().toString());
                    url.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                            text.setText(data.get("request").toString());
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {}
                    });
                    approve.setBackgroundColor(Color.GRAY);
                    approve.setText("Approve Leave");
                    reject.setBackgroundColor(Color.GRAY);
                    reject.setText("Reject Leave");
                    approve.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            url = new Firebase("https://userdata-4a398.firebaseio.com/");
                            firebase = url.child("Attendance marked").child(date).child(parent.getSelectedItem().toString());
                            firebase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //if (!dataSnapshot.hasChild(parent.getSelectedItem().toString())) {
                                        AttendanceMarked attendanceMarked = new AttendanceMarked("Yes");
                                        firebase.setValue(attendanceMarked);
                                    //}
                                }
                                @Override
                                public void onCancelled(FirebaseError firebaseError) {}
                            });
                        }
                    });
                    reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            url = new Firebase("https://userdata-4a398.firebaseio.com/");
                            firebase = url.child("Attendance marked").child(date).child(parent.getSelectedItem().toString());
                            firebase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    AttendanceMarked attendanceMarked = new AttendanceMarked("No");
                                    firebase.setValue(attendanceMarked);
                                }
                                @Override
                                public void onCancelled(FirebaseError firebaseError) {}
                            });
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void ReadPhoneDate(){
        String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec" };
        Calendar calendar = Calendar.getInstance();
        String d = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String month = monthName[calendar.get(Calendar.MONTH)];
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        hour = String.valueOf(calendar.get(Calendar.HOUR));
        minute = String.valueOf(calendar.get(Calendar.MINUTE));
        date = d + " " + month + " " + year;

    }

    public class AttendanceMarked {
        public String marked;

        public AttendanceMarked(String marked) {
            this.marked = marked;
        }
    }

}
