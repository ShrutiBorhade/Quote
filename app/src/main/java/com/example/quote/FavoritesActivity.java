package com.example.quote;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_quotes);

        displayFavoriteQuotes();
    }

    private void displayFavoriteQuotes() {
        SharedPreferences preferences = getSharedPreferences("Favorites", MODE_PRIVATE);
        String savedFavorites = preferences.getString("quotes", "");

        String[] favoriteQuotes = savedFavorites.split("\n");

        LinearLayout favoritesContainer = findViewById(R.id.favoritesContainer);

        for (String quote : favoriteQuotes) {
            if (!quote.isEmpty()) {
                CardView cardView = new CardView(this);
                cardView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                cardView.setCardElevation(5);
                cardView.setUseCompatPadding(true);

                GradientDrawable shape = new GradientDrawable();
                shape.setCornerRadius(8);
                shape.setColor(Color.parseColor("#535050"));
                cardView.setBackground(shape);

                cardView.setPadding(16, 16, 16, 16);

                TextView textView = new TextView(this);
                textView.setText(quote);
                textView.setTextSize(18);
                textView.setTextColor(Color.WHITE);

                cardView.addView(textView);
                favoritesContainer.addView(cardView);

                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) cardView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 16);
            }
        }
    }
}
