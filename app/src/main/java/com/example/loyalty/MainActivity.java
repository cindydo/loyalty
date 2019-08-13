package com.example.loyalty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static FloatingActionButton fab;
    private static FloatingActionButton fab1;
    private static Chip labelFab1;
    private Boolean isFABOpen = false;

    private LinearLayout listCards;
    private LinearLayout listEmpty;

    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter cardAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Card> cardsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        listEmpty = findViewById(R.id.listEmpty);

        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        labelFab1 = findViewById(R.id.labelFab1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             scanBarcode(view);
            }
        });

        cardsList = new ArrayList<Card>();
        cardsList = db.getAllCards();

        layoutManager = new LinearLayoutManager(this);
        cardAdapter = new CardsAdapter(cardsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardAdapter);

        if (cardAdapter.getItemCount() == 0) {
            listEmpty.setVisibility(View.VISIBLE);
        }
        else {
            listEmpty.setVisibility(View.GONE);
        }
    }

    /*
     * Method Name: showFABMenu
     * Description: This method displays the Fab menu
     * Parameters: none
     * Returns: none
     */
    private void showFABMenu(){
        isFABOpen=true;
        rotateFabForward();
        fab1.show();
        labelFab1.setVisibility(View.VISIBLE);
    }

    /*
     * Method Name: closeFABMenu
     * Description: This method closes the Fab menu
     * Parameters: none
     * Returns: none
     */
    private void closeFABMenu(){
        isFABOpen=false;
        rotateFabBackward();
        fab1.hide();
        labelFab1.setVisibility(View.INVISIBLE);
    }

    /*
     * Method Name: rotateFabForward
     * Description: This method rotates the Fab button forward
     * Parameters: none
     * Returns: none
     */
    public void rotateFabForward() {
        final OvershootInterpolator interpolator = new OvershootInterpolator();
        ViewCompat.animate(fab).
                rotation(135f).
                withLayer().
                setDuration(300).
                setInterpolator(interpolator).
                start();
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccentLight)));
    }

    /*
     * Method Name: rotateFabBackward
     * Description: This method rotates the Fab button backward
     * Parameters: none
     * Returns: none
     */
    public void rotateFabBackward() {
        final OvershootInterpolator interpolator = new OvershootInterpolator();
        ViewCompat.animate(fab).
                rotation(0f).
                withLayer().
                setDuration(300).
                setInterpolator(interpolator).
                start();
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
    }

    /*
     * Method Name: scanBarcode
     * Description: This method scans the barcode
     * Parameters: View view
     * Returns: none
     */
    public void scanBarcode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.initiateScan();
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents() + " type " + result.getFormatName(), Toast.LENGTH_LONG).show();
                // start new activity
                Intent intent = new Intent(this, NewCardActivity.class);
                intent.putExtra("CARD_NUMBER", result.getContents()); //Optional parameters
                intent.putExtra("CARD_FORMAT", result.getFormatName()); //Optional parameters
                this.startActivity(intent);
                // close FAB menu
                closeFABMenu();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
