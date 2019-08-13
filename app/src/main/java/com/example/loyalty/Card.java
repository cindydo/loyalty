package com.example.loyalty;

import java.io.Serializable;

public class Card implements Serializable {
    int id;
    String name, barcodeNumber, barcodeFormat, notes, cardFront, cardBack;

    public Card() {}

    public Card(int id, String name, String barcodeNumber, String barcodeFormat, String notes, String cardFront, String cardBack){
        this.id = id;
        this.name = name;
        this.barcodeNumber = barcodeNumber;
        this.barcodeFormat = barcodeFormat;
        this.notes = notes;
        this.cardFront = cardFront;
        this.cardBack = cardBack;
    }

    public Card(String name, String barcodeNumber, String barcodeFormat, String notes, String cardFront, String cardBack){
        this.name = name;
        this.barcodeNumber = barcodeNumber;
        this.barcodeFormat = barcodeFormat;
        this.notes = notes;
        this.cardFront = cardFront;
        this.cardBack = cardBack;
    }

    /*
     * Method Name: getId
     * Description: This method gets the card's ID
     * Parameters: none
     * Returns: int
     */
    public int getId() {
        return this.id;
    }

    /*
     * Method Name: setId
     * Description: This method sets the card's ID
     * Parameters: int id
     * Returns: none
     */
    public void setId(int id) {
        this.id = id;
    }

    /*
     * Method Name: getName
     * Description: This method gets the card's name
     * Parameters: none
     * Returns: String
     */
    public String getName() {
        return this.name;
    }

    /*
     * Method Name: setName
     * Description: This method sets the card's name
     * Parameters: String name
     * Returns: none
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Method Name: getBarcodeNumber
     * Description: This method gets the card's number
     * Parameters: none
     * Returns: String
     */
    public String getBarcodeNumber() {
        return this.barcodeNumber;
    }

    /*
     * Method Name: setBarcodeNumber
     * Description: This method sets the card's number
     * Parameters: String barcodeNumber
     * Returns: none
     */
    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    /*
     * Method Name: getBarcodeFormat
     * Description: This method gets the card's format name
     * Parameters: none
     * Returns: String
     */
    public String getBarcodeFormat() {
        return this.barcodeFormat;
    }

    /*
     * Method Name: setBarcodeFormat
     * Description: This method sets the card's format name
     * Parameters: String barcodeFormat
     * Returns: none
     */
    public void setBarcodeFormat(String barcodeFormat) {
        this.barcodeFormat = barcodeFormat;
    }

    /*
     * Method Name: getNotes
     * Description: This method gets the card's notes
     * Parameters: none
     * Returns: String
     */
    public String getNotes() {
        return this.notes;
    }

    /*
     * Method Name: setNotes
     * Description: This method sets the card's notes
     * Parameters: String notes
     * Returns: none
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /*
     * Method Name: getCardFront
     * Description: This method gets the image source for the photo on the front of the card
     * Parameters: none
     * Returns: String
     */
    public String getCardFront() {
        return this.cardFront;
    }

    /*
     * Method Name: setCardFront
     * Description: This method sets the image source for the photo on the front of the card
     * Parameters: String cardFront
     * Returns: none
     */
    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    /*
     * Method Name: getCardBack
     * Description: This method gets the image source for the photo on the back of the card
     * Parameters: none
     * Returns: String
     */
    public String getCardBack() {
        return this.cardBack;
    }

    /*
    * Method Name: setCardBack
    * Description: This method sets the image source for the photo on the back of the card
    * Parameters: String cardBack
    * Returns: none
    */
    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }
}

