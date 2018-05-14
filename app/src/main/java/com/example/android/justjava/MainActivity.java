package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    public static final int COFFEE_CUP_PRICE = 5;
    public static final int WHIPPED_CREAM_PRICE = 1;
    public static final int CHOCOLATE_PRICE = 2;

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.name);
        String name = nameEditText.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCreamCheckBox);
        boolean whippedCreamIsChecked = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolateCheckBox);
        boolean chocolateIsChecked = chocolateCheckBox.isChecked();

        int price = calculatePrice(whippedCreamIsChecked, chocolateIsChecked);

        String summary = createOrderSummary(price, whippedCreamIsChecked, chocolateIsChecked, name);
        String emailSubject = "JustJava order for " + name;
        emailOrderSummary(summary, emailSubject);
    }

    private void emailOrderSummary(String orderSummary, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Creates an order summary.
     *
     * @return order summary
     */
    private String createOrderSummary(int price, boolean whippedCreamIsChecked, boolean chocolateIsChecked, String name) {
        String message = "Name: " + name;
        message += "\nAdd whipped cream? " + whippedCreamIsChecked;
        message += "\nAdd chocolate? " + chocolateIsChecked;
        message += "\nQuantity: " + quantity;
        message += "\nTotal: $" + price;
        message += "\nThank you!";
        return message;
    }

    /**
     * Calculates the price of an order.
     *
     * @return total price
     * @param whippedCreamIsChecked
     * @param chocolateIsChecked
     */
    private int calculatePrice(boolean whippedCreamIsChecked, boolean chocolateIsChecked) {
        int basePrice = COFFEE_CUP_PRICE;

        if (whippedCreamIsChecked) {
            basePrice += WHIPPED_CREAM_PRICE;
        }

        if (chocolateIsChecked) {
            basePrice += CHOCOLATE_PRICE;
        }

        return quantity * basePrice;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
            displayQuantity(quantity);
        } else {
            CharSequence text = "You cannot order more than 100 coffees";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(this, text, duration).show();
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
            displayQuantity(quantity);
        } else {
            CharSequence text = "You cannot order less than 1 coffee";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(this, text, duration).show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}