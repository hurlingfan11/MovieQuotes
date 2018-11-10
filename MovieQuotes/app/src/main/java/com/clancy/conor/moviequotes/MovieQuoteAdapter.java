package com.clancy.conor.moviequotes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieQuoteAdapter extends RecyclerView.Adapter<MovieQuoteAdapter.MovieQuoteViewHolder> {

    private List<DocumentSnapshot> mMovieQuoteSnapShots = new ArrayList<>();

    public MovieQuoteAdapter(){

        CollectionReference movieQuoteRef = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PATH);
        movieQuoteRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if(e!=null){
                    Log.w(Constants.TAG, "Listening Failed");
                    return;
                }

                mMovieQuoteSnapShots = queryDocumentSnapshots.getDocuments();
                notifyDataSetChanged();// Whenever it changes will notify the adapater

            }
        });
    }


    @NonNull
    @Override
    public MovieQuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.moviequote_itemview, parent, false);
        return new MovieQuoteViewHolder(itemView);
       }

    @Override
    public void onBindViewHolder(@NonNull MovieQuoteViewHolder movieQuoteViewHolder, int i) {
        DocumentSnapshot ds = mMovieQuoteSnapShots.get(i);
        String quote = (String) ds.get(Constants.KEY_QUOTE);
        String movie = (String) ds.get(Constants.KEY_MOVIE);
        movieQuoteViewHolder.mMovieTextView.setText(movie);
        movieQuoteViewHolder.mQuoteTextView.setText(quote);
    }

    @Override
    public int getItemCount() {

        return mMovieQuoteSnapShots.size();
    }

    class MovieQuoteViewHolder extends RecyclerView.ViewHolder{

        private TextView mQuoteTextView;
        private TextView mMovieTextView;

        public MovieQuoteViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuoteTextView = itemView.findViewById(R.id.itemview_quote);
            mMovieTextView = itemView.findViewById(R.id.itemview_movie);
        }
    }

}
