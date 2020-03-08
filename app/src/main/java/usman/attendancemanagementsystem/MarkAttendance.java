package usman.attendancemanagementsystem;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.Date;


public class MarkAttendance extends DialogFragment implements View.OnClickListener {

    RadioButton present;
    Button quit;
    Firebase url,firebase;
    String regNo="";
    Date Phone_date=null;
    String hour="",minute="",date="",time="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.markattendance,null);
        present = (RadioButton)view.findViewById(R.id.present);
        quit = (Button)view.findViewById(R.id.quit);

        regNo = getArguments().getString("reg","");

        quit.setOnClickListener(this);

        getDialog().setTitle("Mark Your Attendance");
        setCancelable(false);

        return view;
    }


    @Override
    public void onClick(View v) {
        if(present.isChecked()){
            ReadPhoneDate();
            //date = String.valueOf(Phone_date);
            time = hour + ":" + minute;
            url = new Firebase("https://userdata-4a398.firebaseio.com/");
            firebase = url.child("Attendance marked").child(date);
            firebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(regNo)) {
                        firebase = url.child("Attendance marked").child(date).child(regNo);
                        AttendanceMarked attendanceMarked = new AttendanceMarked("Yes",time);
                        firebase.setValue(attendanceMarked);
                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {}
            });

        }
        dismiss();
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
        public String time;

        public AttendanceMarked(String marked,String time) {
            this.marked = marked;
            this.time = time;
        }
    }

}

