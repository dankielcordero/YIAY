package cz.inovett.yiay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentActivity extends AppCompatActivity {
    String mPost_key = null;
    private EditText comment;
    private Button mSubmitBtn;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mPost_key = getIntent().getExtras().getString("blog_id_single");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Coments");
        mSubmitBtn = (Button) findViewById(R.id.buttonInsertComment);
        comment = (EditText) findViewById(R.id.editTextComment);

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strName = comment.getText().toString().trim();
                final DatabaseReference newPost = databaseReference.push();
                newPost.child("title").setValue(strName);
                newPost.child("comment_id").setValue(mPost_key);
                Intent i = new Intent(CommentActivity.this, BlogSingleActivity.class);
                i.putExtra("blog_id", mPost_key);
                Toast.makeText(CommentActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                startActivity(i);

            }
        });
    }
}
