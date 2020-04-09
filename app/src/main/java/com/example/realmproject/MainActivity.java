package com.example.realmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.PeriodicSync;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.realmproject.Model.Person;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {
    private  EditText edtName , edtAge;
    private  Button btnSave,btnDelete,btnShow;
    private  TextView textView;
    private Realm realm;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         realm = Realm.getDefaultInstance();

        edtName = findViewById(R.id.edtName);
        edtAge  = findViewById(R.id.edtAge);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btndelete);
        btnShow = findViewById(R.id.btnView);
        textView = findViewById(R.id.textView);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(edtName.getText().toString().trim(),Integer.parseInt(edtAge.getText().toString().trim()));


            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdata();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                

            }
        });



    }



    private  void saveData(final String name, final int age){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Person person = bgRealm.createObject(Person.class);
                person.setName(name);
                person.setAge(age);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.v("success","okkkkkkk");

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e("error",error.getMessage());

            }
        });


    }
    private void showdata() {
        RealmResults<Person> result = realm.where(Person.class).findAllAsync();
        result.load();
        String output = "";
        for(Person person:result){
            output = output+person.toString();
        }
        textView.setText(output);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
