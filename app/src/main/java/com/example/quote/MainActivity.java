package com.example.quote;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView quoteTextView;
    private ImageButton addToFavoritesButton;
    private Button viewFavoritesButton;
    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteTextView = findViewById(R.id.quoteTextView);
        addToFavoritesButton = findViewById(R.id.addToFavoritesButton);
        viewFavoritesButton = findViewById(R.id.viewFavouritePage);
        shareButton = findViewById(R.id.shareButton);

        displayRandomQuote();

        addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavorites();
            }
        });

        viewFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAllFavorites();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                shareQuote();
            }
        });
    }

    private void displayRandomQuote() {
        String[] quotes = getResources().getStringArray(R.array.inspiring_quotes);
        int randomIndex = (int) (Math.random() * quotes.length);
        String randomQuote = quotes[randomIndex];
        quoteTextView.setText(randomQuote);
    }

    private void shareQuote() {
        String currentQuote = quoteTextView.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.setType("text/plain");

        shareIntent.putExtra(Intent.EXTRA_TEXT, currentQuote);

        shareIntent.putExtra(Intent.EXTRA_EXCLUDE_COMPONENTS, new String[]{"com.whatsapp", "com.facebook.katana", "com.instagram.android"});

        Intent chooserIntent = Intent.createChooser(shareIntent, "Share using");

        if (shareIntent.resolveActivity(getPackageManager()) != null) {

            startActivity(chooserIntent);
        } else {
        }
    }



    private void addToFavorites() {
        String currentQuote = quoteTextView.getText().toString();

        // Save the quote to SharedPreferences (for simplicity)
        saveToFavorites(currentQuote);

        // Show a Toast to inform the user that the quote has been added to favorites
        showToast("Added to Favorites");
    }

    private void showToast(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }



    private void saveToFavorites(String quote) {
        SharedPreferences preferences = getSharedPreferences("Favorites", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String currentFavorites = preferences.getString("quotes", "");

        Log.d("Favorites", "Current Favorites: " + currentFavorites);

        currentFavorites = currentFavorites + quote + "\n";

        editor.putString("quotes", currentFavorites);
        editor.apply();

        Log.d("Favorites", "Updated Favorites: " + currentFavorites);
    }

    private void navigateToAllFavorites() {
        Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
        startActivity(intent);
    }
}
