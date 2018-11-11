package com.clancy.conor.moviequotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MovieQuoteActivity extends AppCompatActivity {

    private TextView mQuoteTextView;
    private TextView mMovieTextView;
    private DocumentReference mDocRef;
    private DocumentSnapshot mDocSnapShot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_quote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mQuoteTextView = findViewById(R.id.detail_quote);
        mMovieTextView = findViewById(R.id.detail_movie);

        Intent receivedIntent = getIntent();
        String docId = receivedIntent.getStringExtra(Constants.EXTRA_DOC_ID);

        // Temporary Test
        // mQuoteTextView.setText(docId);

        mDocRef = FirebaseFirestore.getInstance().
                collection(Constants.COLLECTION_PATH).document(docId);

        mDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(e!=null){
                    Log.w(Constants.TAG, "listen failed");
                }
                if(documentSnapshot.exists()){
                    mDocSnapShot = documentSnapshot; //Save document snapshot
                    mQuoteTextView.setText((String)documentSnapshot.get(Constants.KEY_QUOTE));
                    mMovieTextView.setText((String)documentSnapshot.get(Constants.KEY_MOVIE));
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

       switch(item.getItemId()){
           case R.id.action_delete:
               //TODO: Delete this quote and close this activity
               return true;
       }

        return super.onOptionsItemSelected(item);
    }
}