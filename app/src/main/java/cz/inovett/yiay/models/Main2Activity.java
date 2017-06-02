package cz.inovett.yiay.models;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cz.inovett.yiay.MainActivity;
import cz.inovett.yiay.R;

public class Main2Activity extends AppCompatActivity {
    private DatabaseReference databaseReference;

    private ProgressDialog mProgress;
    private EditText name;
    private Button mSubmitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Questions");
        mSubmitBtn = (Button) findViewById(R.id.buttonSubmit);
        name = (EditText) findViewById(R.id.editTextTitle);
        mProgress = new ProgressDialog(this);
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartPosting();
            }
        });
    }

    private void StartPosting() {
        final String strName = name.getText().toString().trim();

        mProgress.setMessage("Posting ....");
        mProgress.show();

        final DatabaseReference newPost = databaseReference.push();

        newPost.child("title").setValue(strName);
        Intent i = new Intent(Main2Activity.this, MainActivity.class);
        Toast.makeText(Main2Activity.this, "Successfully added", Toast.LENGTH_SHORT).show();
        startActivity(i);

        mProgress.dismiss();



    }
}
