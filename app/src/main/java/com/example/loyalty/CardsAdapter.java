package com.example.loyalty;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {
    private List<Card> cardsList;
    private Context context;

    public CardsAdapter(List<Card> cardsList) {
        this.cardsList = cardsList;
    }

    /*
     * Method Name: onCreateViewHolder
     * Description: This method creates the view holder
     * Parameters: ViewGroup parent, int viewType
     * Returns: CardViewHolder
     */
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(itemView);
        return viewHolder;
    }

    /*
     * Method Name: onBindViewHolder
     * Description: This method binds the view holder
     * Parameters: final CardViewHolder holder, final int position
     * Returns: none
     */
    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        final Card cardDetails = cardsList.get(position);

        String barcodeFormat = cardDetails.getBarcodeFormat();
        String cardNumber = cardDetails.getBarcodeNumber();

        holder.cardName.setText(cardDetails.getName());
        holder.cardFormat.setText(cardNumber);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.list_item, null, false);
        contentview.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width = contentview.getMeasuredWidth() * Math.round(displayMetrics.density);

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(cardNumber, BarcodeFormat.valueOf(barcodeFormat), width, 200);
            holder.imageViewBarcode.setImageBitmap(bitmap);
        } catch(Exception e) {
            Log.e("Error", e.getMessage());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToViewCard(cardDetails);
            }
        });
    }

    /*
     * Method Name: navToViewCard
     * Description: This method navigates to the View Card Activity
     * Parameters: Card card
     * Returns: none
     */
    public void navToViewCard(Card card) {
        Intent intent = new Intent(context, ViewCardActivity.class);
        intent.putExtra("CARD", card);
        context.startActivity(intent);
    }

    /*
     * Method Name: getItemCount
     * Description: This method returns the size of the card list
     * Parameters: none
     * Returns: int
     */
    @Override
    public int getItemCount() {
        return cardsList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView cardName, cardFormat;
        ImageView imageViewBarcode;

        public CardViewHolder(View itemView) {
            super(itemView);
            cardName = (TextView) itemView.findViewById(R.id.textViewCardName);
            cardFormat = (TextView) itemView.findViewById(R.id.textViewCardNumber);
            imageViewBarcode = (ImageView) itemView.findViewById(R.id.barcode);
        }
    }
}
