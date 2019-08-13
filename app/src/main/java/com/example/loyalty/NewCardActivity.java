package com.example.loyalty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class NewCardActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private String cardName = "";
    private String cardNumber = "";
    private String cardFormat = "";
    private EditText editTextCardName;
    private EditText editTextCardNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        getSupportActionBar().setTitle("Add Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        cardNumber = intent.getStringExtra("CARD_NUMBER");
        cardFormat = intent.getStringExtra("CARD_FORMAT");

        editTextCardName = findViewById(R.id.cardName);
        editTextCardNumber = findViewById(R.id.cardNumber);

        // Set card name and number
        editTextCardNumber.setText(cardNumber);
        editTextCardName.setText(cardName);
    }

    // Create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_card, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Handle actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            // Save card
            saveCard();
        }
        else if (id == R.id.action_cancel) {
            // Cancel new card
            navToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Method Name: saveCard
     * Description: This method saves a card
     * Parameters: none
     * Returns: none
     */
    private void saveCard() {
        cardName = editTextCardName.getText().toString();
        cardNumber = editTextCardNumber.getText().toString();
        db.addCard(new Card(cardName, cardNumber, cardFormat, "", "", ""));
    }

    /*
     * Method Name: navToMain
     * Description: This method navigates to the Main Activity
     * Parameters: none
     * Returns: none
     */
    public void navToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
