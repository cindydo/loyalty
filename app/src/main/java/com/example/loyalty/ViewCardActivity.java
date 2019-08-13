package com.example.loyalty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ViewCardActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Card card;
    private ImageView imageViewBarcode;
    private TextView textViewCardNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);

        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        card = (Card)intent.getSerializableExtra("CARD");

        getSupportActionBar().setTitle(card.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageViewBarcode = findViewById(R.id.barcode);
        textViewCardNumber = findViewById(R.id.textViewCardNumber);

        textViewCardNumber.setText(card.getBarcodeNumber());

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        String cardNumber = card.getBarcodeNumber();
        String barcodeFormat = card.getBarcodeFormat();

        float dpWidth = (displayMetrics.widthPixels / displayMetrics.density) - 25;
        int width = Math.round(dpWidth * displayMetrics.density);

        try {
            Toast.makeText(this, String.valueOf(width), Toast.LENGTH_LONG).show();
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(cardNumber, BarcodeFormat.valueOf(barcodeFormat), width, 180);
            imageViewBarcode.setImageBitmap(bitmap);
        } catch(Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    // Create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_card, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Handle actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            // do something here
            navToEdit();
        }
        else if (id == R.id.action_delete) {
            // do something here
            deleteCard();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Method Name: navToEdit
     * Description: This method navigates to the Edit Card Activity
     * Parameters: none
     * Returns: none
     */
    public void navToEdit() {
        Intent intent = new Intent(this, EditCardActivity.class);
        intent.putExtra("CARD", card);
        this.startActivity(intent);
    }

    /*
     * Method Name: deleteCard
     * Description: This method deletes the card
     * Parameters: none
     * Returns: none
     */
    public void deleteCard() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete card?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteCard(card);
                        dialog.cancel();
                        navToMain();
                    }
                });

        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
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
