package com.waterdiary.drinkreminder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waterdiary.drinkreminder.base.MasterBaseActivity;
import com.waterdiary.drinkreminder.worker.handbook_hospitaldata;

import java.util.ArrayList;
import java.util.Arrays;

public class handbook_healthtips extends MasterBaseActivity {
    ArrayList<handbook_hospitaldata> hosp_list = new ArrayList<>();
    DatabaseReference nDatabase;
    hospadapter adap;
    ListView nListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handbook_healthtips);
        TextView Tips = findViewById(R.id.healthTips);
        TextView News = findViewById(R.id.news);
        nDatabase = FirebaseDatabase.getInstance().getReference();
        nDatabase.keepSynced(true);

        //buyco.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //public void onClick(View view) {
        //  Intent intent = new Intent(getApplicationContext(), Screen_Dashboard.class);
        //startActivity(intent);
        //}
        //});
        nListView = (ListView) findViewById(R.id.hosp_list);
        adap = new hospadapter(this, R.layout.handbook_hosp, hosp_list);
        System.out.println(hosp_list);
        nListView.setAdapter(adap);
        getFirebase();
        Tips.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getApplicationContext(), handbook_tips.class);
                                        startActivity(intent);
                                    }
                                }
        );
        News.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View view) {
                                        Intent intent2 = new Intent(getApplicationContext(), handbook_news.class);
                                        startActivity(intent2);
                                    }
                                }
        );
    }
    public void getFirebase() {
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hosp_list.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    handbook_hospitaldata hosp = childDataSnapshot.getValue(handbook_hospitaldata.class);
                    hosp_list.add(hosp);
                    hosp_list.add(new handbook_hospitaldata("ff","ff","ff","ff","ff","ff"));
                    adap.notifyDataSetChanged();
                    System.out.print("meow");
                    System.out.println(hosp_list);
                    assert hosp != null;
                    Log.d("hosp", hosp.name);
                    Log.d("doctor",hosp.doctor);
                }
            }

            @Override
            public void onCancelled (DatabaseError databaseError){
            }
        };
        nDatabase.child("/hospitals").addValueEventListener(postListener);
    }

}
