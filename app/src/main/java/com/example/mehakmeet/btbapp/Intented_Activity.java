package com.example.mehakmeet.btbapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class Intented_Activity extends AppCompatActivity {

    TextView agency;
    TextView aval_seats;
    TextView seat_no;
    TextView bus_no;
    TextView total;
    TextView places;
    Button ok;
    Button cancel;
    int seat_available_1=30;
    int seat_number=1;
    RelativeLayout dialog_box;
    TextView amount_paid;
    Button ok2;
    Button cancel2;

    int c=1;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intented);
        Intent i1=getIntent();
        String position_s=i1.getStringExtra(clicked_bus_search.Extra);
        String departure=i1.getStringExtra(clicked_bus_search.Extra_1);
        String destination=i1.getStringExtra(clicked_bus_search.Extra_2);
        int pos=Integer.parseInt(position_s);

        agency=(TextView)findViewById(R.id.bus_ag);
        aval_seats=(TextView)findViewById(R.id.seats_available);
        seat_no=(TextView)findViewById(R.id.seat_number);
        total=(TextView)findViewById(R.id.total);
        places=(TextView)findViewById(R.id.places);
        bus_no=(TextView)findViewById(R.id.bus_numb);
        ok=(Button)findViewById(R.id.ok);
        cancel=(Button)findViewById(R.id.cancel);

        ok2=(Button)findViewById(R.id.ok2);
        cancel2=(Button)findViewById(R.id.cancel2);


        dialog_box=(RelativeLayout)findViewById(R.id.dialog_box);
        amount_paid=(TextView)findViewById(R.id.amount_paid);
        places.setText(departure+" to "+destination);

        Random rand=new Random();
        int busno_01=rand.nextInt(10);
        int busno=rand.nextInt(1432)+1000;
        final int amount=rand.nextInt(150)+200;

        final String bus_number="TN0"+String.valueOf(busno_01)+"AB"+String.valueOf(busno);
        final String bus_agency="BUS AGENCY "+String.valueOf(pos+1);
        agency.setText(bus_agency);
        bus_no.setText("BUS PLATE NUMBER: "+bus_number);
        aval_seats.setText("Seats Available: "+String.valueOf(seat_available_1));
        seat_no.setText("Seat Number: "+String.valueOf(seat_number));

        total.setText("TOTAL AMOUNT: Rs."+String.valueOf(amount));

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_box.animate().alpha(1f);
                amount_paid.setText("Amount to be paid : Rs."+String.valueOf(amount));

                ok2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                      mDatabase= FirebaseDatabase.getInstance().getReference().child("User"+String.valueOf(c));

                        HashMap<String,String> dataMap=new HashMap<String, String>();
                        dataMap.put("Agency",bus_agency);
                        dataMap.put("Bus plate number",bus_number);
                        dataMap.put("Seat Number",String.valueOf(seat_number));
                        dataMap.put("Amount","Rs."+String.valueOf(amount));
                        mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Intented_Activity.this,"Ticket confirmed Successfully",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        c++;
                        dialog_box.animate().alpha(0f);
                        seat_available_1--;
                        seat_number++;
                        aval_seats.setText("Seats Available: "+String.valueOf(seat_available_1));
                        seat_no.setText("Seat Number: "+String.valueOf(seat_number));


                    }
                });

                cancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog_box.animate().alpha(0f);
                    }
                });
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
