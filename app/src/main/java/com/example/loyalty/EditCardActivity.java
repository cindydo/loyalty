package com.example.loyalty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditCardActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Card card;
    private EditText editTextCardName;
    private EditText editTextCardNumber;
    private EditText editTextCardNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        card = (Card)intent.getSerializableExtra("CARD");

        getSupportActionBar().setTitle("Edit " + card.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextCardName = findViewById(R.id.cardName);
        editTextCardNumber = findViewById(R.id.cardNumber);
        editTextCardNotes = findViewById(R.id.cardNotes);

        // Set card text
        editTextCardName.setText(card.getName());
        editTextCardNumber.setText(card.getBarcodeNumber());
        editTextCardNotes.setText(card.getNotes());
    }

    // Create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_card, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Handle actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            // Save card edit
            saveCard();
            navToMain();
        }
        else if (id == R.id.action_cancel) {
            // Cancel card edit
            navToMain();
        }
        return super.onOptionsItemSelected(item);
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

    /*
     * Method Name: saveCard
     * Description: This method saves the card
     * Parameters: none
     * Returns: none
     */
    public void saveCard() {
        card.setName(editTextCardName.getText().toString());
        card.setBarcodeNumber(editTextCardNumber.getText().toString());
        card.setBarcodeFormat(card.getBarcodeFormat());
        card.setNotes(editTextCardNotes.getText().toString());

        // Update card
        db.updateCard(card);
    }
}
