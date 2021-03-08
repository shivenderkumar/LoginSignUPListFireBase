package com.shivenderkumar.demologinsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<User> userArrayList = new ArrayList<>();
    private RecyclerView rv;
    private UserAdapter adapter;

    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressbar = findViewById(R.id.progressbar2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/profiles");

        if(haveNetworkConnection()){
            rv=(RecyclerView)findViewById(R.id.recycler1);
            rv.setLayoutManager(new LinearLayoutManager(this));
            getdata();
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "No Network Connection",
                    Toast.LENGTH_LONG)
                    .show();
        }

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void getdata() {

        progressbar.setVisibility(View.VISIBLE);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String name = dataSnapshot.child("name").getValue().toString();
                    String city = dataSnapshot.child("city").getValue().toString();
                    String gender = dataSnapshot.child("gender").getValue().toString();
                    String age = dataSnapshot.child("age").getValue().toString();

                    int age_int = Integer.parseInt(age);
                    User user  = new User(name,city,gender,age_int);

                    userArrayList.add(user);
                   // System.out.println("OOOOOO output database : "+user.toString());
                }

                progressbar.setVisibility(View.GONE);

                adapter=new UserAdapter(userArrayList, HomeActivity.this);
                rv.setAdapter(adapter);
                adapter.setOnItemClickListener(HomeActivity.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                System.out.println("OOOOOO output database Error : "+error.getMessage().toString());
               // Toast.makeText(HomeActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_menu:

                return true;
            case R.id.logout_menu:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void logout() {
        MainActivity.firebaseAuth.signOut();
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, UserDetailActivity.class);
        User clickedItem = userArrayList.get(position);
        detailIntent.putExtra("name", clickedItem.getName());
        detailIntent.putExtra("city", clickedItem.getCity());
        detailIntent.putExtra("gender", clickedItem.getGender());
        detailIntent.putExtra("age", clickedItem.getAge());
        startActivity(detailIntent);
    }

}