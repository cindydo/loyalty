package com.example.loyalty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cardsManager";
    private static final String TABLE_CARDS = "cards";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_BARCODE_NUMBER = "barcode_number";
    private static final String KEY_BARCODE_FORMAT = "barcode_format";
    private static final String KEY_NOTES = "notes";
    private static final String KEY_CARD_FRONT = "card_front";
    private static final String KEY_CARD_BACK = "card_back";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CARDS_TABLE = "CREATE TABLE " + TABLE_CARDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_BARCODE_NUMBER + " TEXT," + KEY_BARCODE_FORMAT + " TEXT,"
                + KEY_NOTES + " TEXT," + KEY_CARD_FRONT + " TEXT," + KEY_CARD_BACK + " TEXT" + ")";
        db.execSQL(CREATE_CARDS_TABLE);
    }

    // Upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);

        // Create tables again
        onCreate(db);
    }

    /*
     * Method Name: addCard
     * Description: This method adds a card
     * Parameters: Card card
     * Returns: none
     */
    public void addCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, card.getName()); // Contact Name
        values.put(KEY_BARCODE_NUMBER, card.getBarcodeNumber()); // Contact Phone
        values.put(KEY_BARCODE_FORMAT, card.getBarcodeFormat()); // Contact Phone
        values.put(KEY_NOTES, card.getNotes()); // Contact Phone
        values.put(KEY_CARD_FRONT, card.getCardFront()); // Contact Phone
        values.put(KEY_CARD_BACK, card.getCardBack()); // Contact Phone

        // Insert row
        db.insert(TABLE_CARDS, null, values);
        // Close database connection
        db.close();
    }

    /*
     * Method Name: getCard
     * Description: This method gets a single card
     * Parameters: int id
     * Returns: Card
     */
    public Card getCard(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CARDS, new String[] { KEY_ID,
                        KEY_NAME, KEY_BARCODE_NUMBER, KEY_BARCODE_FORMAT,
                        KEY_NOTES, KEY_CARD_FRONT, KEY_CARD_BACK}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Card card = new Card(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2)
                , cursor.getString(3), cursor.getString(4)
                , cursor.getString(5), cursor.getString(6));

        // Return card
        return card;
    }

    /*
     * Method Name: getAllCards
     * Description: This method returns a list of cards
     * Parameters: none
     * Returns: List<Card>
     */
    public List<Card> getAllCards() {
        List<Card> cardList = new ArrayList<Card>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CARDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                Card card = new Card();
                card.setId(Integer.parseInt(cursor.getString(0)));
                card.setName(cursor.getString(1));
                card.setBarcodeNumber(cursor.getString(2));
                card.setBarcodeFormat(cursor.getString(3));
                card.setNotes(cursor.getString(4));
                card.setCardFront(cursor.getString(5));
                card.setCardBack(cursor.getString(6));
                // Add card to list
                cardList.add(card);
            } while (cursor.moveToNext());
        }

        // Return cards list
        return cardList;
    }

    /*
     * Method Name: updateCard
     * Description: This method updates a card
     * Parameters: Card card
     * Returns: int
     */
    public int updateCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update card values
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, card.getName());
        values.put(KEY_BARCODE_NUMBER, card.getBarcodeNumber());
        values.put(KEY_BARCODE_FORMAT, card.getBarcodeFormat());
        values.put(KEY_NOTES, card.getNotes());
        values.put(KEY_CARD_FRONT, card.getCardFront());
        values.put(KEY_CARD_BACK, card.getCardBack());

        // Update row
        return db.update(TABLE_CARDS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(card.getId()) });
    }

    /*
     * Method Name: deleteCard
     * Description: This method deletes a card
     * Parameters: Card card
     * Returns: none
     */
    public void deleteCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CARDS, KEY_ID + " = ?",
                new String[] { String.valueOf(card.getId()) });
        db.close();
    }

    /*
     * Method Name: getCardsCount
     * Description: This method gets the card list count
     * Parameters: none
     * Returns: int
     */
    public int getCardsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CARDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // Return count
        return cursor.getCount();
    }
}
