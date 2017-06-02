package cz.inovett.yiay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cz.inovett.yiay.models.Questions;

public class BlogSingleActivity extends AppCompatActivity {
    String mPost_key = null;
    private DatabaseReference mDatabase;
    private DatabaseReference databaseReference;
    private TextView mBlogSingleTitle;
    private Button mSingleRomeBtnComments;
    private RecyclerView blogList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);
        mPost_key = getIntent().getExtras().getString("blog_id");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Questions");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Coments");
        mBlogSingleTitle = (TextView) findViewById(R.id.textViewTitleBlogSimple);
        mSingleRomeBtnComments = (Button) findViewById(R.id.buttonComent);
        mSingleRomeBtnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BlogSingleActivity.this, CommentActivity.class);
                i.putExtra("blog_id_single", mPost_key);
                startActivity(i);
            }
        });
        blogList = (RecyclerView) findViewById(R.id.blog_list_comments);
        blogList.setHasFixedSize(true);
        blogList.setLayoutManager(new LinearLayoutManager(this));

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();

                mBlogSingleTitle.setText(post_title);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    protected void onStart() {

        FirebaseRecyclerAdapter<Questions, MainActivity.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Questions, MainActivity.BlogViewHolder>(
                Questions.class,
                R.layout.blog_row,
                MainActivity.BlogViewHolder.class,
                databaseReference

        ) {
            @Override
            protected void populateViewHolder(MainActivity.BlogViewHolder viewHolder, Questions model, int position) {
                viewHolder.setTitle(model.getTitle());
                        

            }

        };
        blogList.setAdapter(firebaseRecyclerAdapter);

        FirebaseUserActions.getInstance().start(getIndexApiAction());
        super.onStart();
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        return Actions.newView("MainMenu", "http://[ENTER-YOUR-URL-HERE]");
    }

    @Override
    public void onStop() {

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().end(getIndexApiAction());
        super.onStop();
    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View view;

        public BlogViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) view.findViewById(R.id.post_Name);
            post_title.setText(title);
        }
    }


}
