package cz.inovett.yiay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cz.inovett.yiay.models.Main2Activity;
import cz.inovett.yiay.models.Questions;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    private DatabaseReference databaseReference;
    private RecyclerView blogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blogList = (RecyclerView) findViewById(R.id.blog_list);
        blogList.setHasFixedSize(true);
        blogList.setLayoutManager( new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Questions");
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });

    }


    @Override
    protected void onStart() {
    FirebaseRecyclerAdapter<Questions, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Questions, BlogViewHolder>(
            Questions.class,
            R.layout.blog_row,
            BlogViewHolder.class,
            databaseReference

    ) {
        @Override
        protected void populateViewHolder(BlogViewHolder viewHolder, Questions model, int position) {
            viewHolder.setTitle(model.getTitle());
            final String post_key = getRef(position).getKey();

            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent singleBlogInetnt = new Intent(MainActivity.this, BlogSingleActivity.class);
                    singleBlogInetnt.putExtra("blog_id", post_key);
                    startActivity(singleBlogInetnt);
                }
            });


        }

    };
        blogList.setAdapter(firebaseRecyclerAdapter);
        super.onStart();
    FirebaseUserActions.getInstance().start(getIndexApiAction());
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


public  static  class BlogViewHolder extends RecyclerView.ViewHolder{

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
