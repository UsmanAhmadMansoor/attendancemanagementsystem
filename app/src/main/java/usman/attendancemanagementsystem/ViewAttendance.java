package usman.attendancemanagementsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Map;

public class ViewAttendance extends AppCompatActivity {

    LinearLayout ll2,ll3,ll4;
    Spinner spinner;
    TextView std,name,date,status,d,s;
    Firebase url,url2,firebase,studentUrl,studentFirebase;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    String n = "";

    Map<String, Object> data1;
    LinearLayout.LayoutParams lparams;

    String dd= "25 Apr 2019";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        Firebase.setAndroidContext(this);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        spinner = (Spinner) findViewById(R.id.spinner);
        std = (TextView) findViewById(R.id.std);
        name = (TextView) findViewById(R.id.name);
        date = (TextView) ll2.findViewById(R.id.date);
        status = (TextView) ll2.findViewById(R.id.status);

        //d = (TextView) findViewById(R.id.d);
        //s = (TextView) findViewById(R.id.s);

        list = new ArrayList<>();
        list.add("");

        url = new Firebase("https://userdata-4a398.firebaseio.com/Students");
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

                for(int i = 0;i<2;i++){

                                LinearLayout.LayoutParams linear = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                ll4 = new LinearLayout(ViewAttendance.this);
                                ll4.setLayoutParams(linear);
                                ll4.setOrientation(LinearLayout.HORIZONTAL);
                                ll3.addView(ll4);
                                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                                        230, LinearLayout.LayoutParams.WRAP_CONTENT);
                                d = new TextView(ViewAttendance.this);
                                d.setLayoutParams(lparams);
                                d.setTextSize(28);

                                s = new TextView(ViewAttendance.this);
                                s.setLayoutParams(lparams);
                                s.setTextSize(28);

                                ll4.addView(d);
                                ll4.addView(s);


                                d.setText("4 May 2018");
                    s.setText(getStatus());
                    dd = "5 May 2019";

                            }
                    //s.setText("Present");

                if (!parent.getSelectedItem().toString().equals("")) {
                    ll3.removeAllViews();
                    studentUrl = new Firebase("https://userdata-4a398.firebaseio.com/Students/" + parent.getSelectedItem().toString());
                    studentUrl.addValueEventListener(new com.firebase.client.ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                            Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                            std.setText("Student name");
                            name.setText(data.get("name").toString());
                            date.setText("Date");
                            status.setText("Status");
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                        }
                    });

                    url = new Firebase("https://userdata-4a398.firebaseio.com/Admin Attendance Submission/");
                    url.addValueEventListener(new com.firebase.client.ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (final com.firebase.client.DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                LinearLayout.LayoutParams linear = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                ll4 = new LinearLayout(ViewAttendance.this);
                                ll4.setLayoutParams(linear);
                                ll4.setOrientation(LinearLayout.HORIZONTAL);
                                ll3.addView(ll4);

                                lparams = new LinearLayout.LayoutParams(
                                  230, LinearLayout.LayoutParams.WRAP_CONTENT);

                                d = new TextView(ViewAttendance.this);
                                d.setLayoutParams(lparams);
                                d.setTextSize(28);
                                d.setText(snapshot.getKey().toString());
                                ll4.addView(d);

                                s = new TextView(ViewAttendance.this);
                                s.setLayoutParams(lparams);
                                s.setTextSize(28);

                                url2 = new Firebase("https://userdata-4a398.firebaseio.com/Admin Attendance Submission/" + snapshot.getKey().toString() + "/" + parent.getSelectedItem().toString());
                                url2.addValueEventListener(new com.firebase.client.ValueEventListener() {
                                    @Override
                                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                        data1 = (Map<String, Object>) dataSnapshot.getValue();
                                        s.setText(data1.get("status").toString());
                                    }
                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {}
                                });

                                ll4.addView(s);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                        }
                    });
                }
            }
                @Override
                public void onNothingSelected (AdapterView < ? > parent){
                }
        });

    }

    public String getStatus(){
        url2 = new Firebase("https://userdata-4a398.firebaseio.com/Admin Attendance Submission/" + dd + "/1314");
        url2.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                data1 = (Map<String, Object>) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        return data1.get("status").toString();
    }


    //list.add(snapshot.getKey().toString())


                                /*ll3 = new LinearLayout(ViewAttendance.this);
                                ll3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                ll3.setOrientation(LinearLayout.HORIZONTAL);
                                ll2.addView(ll3);
                                ll3.setBackgroundColor(Color.BLUE);*/
}
