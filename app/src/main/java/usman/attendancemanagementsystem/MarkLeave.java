package usman.attendancemanagementsystem;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.Date;


public class MarkLeave extends DialogFragment implements View.OnClickListener {

    EditText type;
    Button send,quit;
    String regNo="";
    Firebase url,firebase;
    Date Phone_date=null;
    String hour="",minute="",date="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.markleave,null);
        type = (EditText)view.findViewById(R.id.type);
        send = (Button)view.findViewById(R.id.send);
        quit = (Button)view.findViewById(R.id.quit);

        regNo = getArguments().getString("reg","");

        send.setOnClickListener(this);
        quit.setOnClickListener(this);

        getDialog().setTitle("Send Request for Leave");
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.send){
            if(TextUtils.isEmpty(type.getText().toString())){
                Toast.makeText(getActivity(), "Type reason", Toast.LENGTH_LONG).show();
            }else{
                ReadPhoneDate();
                //date = String.valueOf(Phone_date);
                url = new Firebase("https://userdata-4a398.firebaseio.com/");
                firebase = url.child("Student Leave Requests").child(date);
                firebase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild(regNo)) {
                            firebase = url.child("Student Leave Requests").child(date).child(regNo);
                            Request  leave= new Request(type.getText().toString());
                            firebase.setValue(leave);
                        }else {
                            Toast.makeText(getActivity(), "Leave Request Already sent", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {}
                });
            }
        }else{
            dismiss();
        }
    }

    public class Request {

        public String request;

        public Request(String request) {
            this.request = request;
        }
    }

    public void ReadPhoneDate(){
        String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec" };
        Calendar calendar = Calendar.getInstance();
        String d = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String month = monthName[calendar.get(Calendar.MONTH)];
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        date = d + " " + month + " " + year;
    }
}
