package com.mapbox.storelocator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

//public class addingItems extends AppCompatActivity

public class addingItems extends AppCompatActivity {

    EditText userInput; //get user input of item name
    EditText priceInput; //get the price input

    Button sumbitButton; //the button used to hit the sumbit

    ArrayList<itemInfo> groceryList = new ArrayList<itemInfo>(); //list to keep track of the current grocery list
    TextView outputStr;// this is string is the output string
    TextView priceOutputStr;//price output string

    String strOfItems ="";//sting to be concated everytime a new item is insert
    itemInfo product;


    List<itemInfo> productList;//list to keep track of the current grocery list

    //private TextInputLayout userTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_items);

        //userInput is the var to pinpoint what section in xml to lookat by is id


        userInput = findViewById(R.id.editTextItem);
        priceInput = findViewById(R.id.priceUserInput);

        //outputStr is the var to pinpoint what section in the xml by id is the string going to show up
        outputStr = (TextView) findViewById(R.id.currentList);

    }
    //check to see if the user input is not an empty string
    public boolean validInput(){
        boolean isValid = false;

        String textInput = userInput.getText().toString().trim();

        if(!textInput.isEmpty()){
            isValid = true;
        }

        return isValid;
    }

    public void itemSubmit(View view) {

        //read the input from user and trim any trailing spaces
        String textInput = userInput.getText().toString().trim();
        String userPriceInput = priceInput.getText().toString().trim();


        //make sure that both inpuuts are  not empty
        if(!textInput.isEmpty() && (!userPriceInput.isEmpty())){

            //at this point we must now check if the price is a dobule

            if(priceInputTypeIsValid(userPriceInput))
            {
                //at this stage we have a valid double. We must still check that we only have two
                //numbers after that decimal
                if(validUserDecimalFormat(userPriceInput)){

                    //at this point inside the if-statement, the format is good go to go and we accept both inputs

                    Double itemPrice = Double.parseDouble(userPriceInput);

                    //now check it the existing product exist, if yes just update the product count, else add a new produce
                    if(itemAlreadyOnList(groceryList, textInput)){

                        int itemPosition = findItemPosition(groceryList, textInput);

                        //now update the count on that item
                        groceryList.get(itemPosition).updateItemCount(1);
                    }
                    else{// at this point we create a new item and update the list

                        itemInfo item = new itemInfo(textInput, itemPrice);

                        groceryList.add(item);

                    }
                    //now display the updated list base on if a new item was created or just updated
                    displayGroceryList(groceryList);

                }
                else{//at this point we have more than two values after the decimal,report the error
                    EditText errorMessage = (EditText)findViewById(R.id.priceUserInput);
                    errorMessage.setError("Please Enter A Valid Decimal Price");
                }

            }
            else //
            {//at this point we cannot convert the price input into a decimal, report the error
                EditText errorMessage = (EditText)findViewById(R.id.priceUserInput);
                errorMessage.setError("Please Enter A Valid Decimal Price");
            }

        }

        //at this point one or more fields are empty, check if both are empty
        else if((textInput.isEmpty()) && (userPriceInput.isEmpty())){//checking to see if both fields are empty

            //at this stage both entries have an input but we must still check if the price is an acutal number
            // that is a double


            EditText nameErrorMessage = (EditText)findViewById(R.id.editTextItem);
            nameErrorMessage.setError("Please Enter an Item");

            EditText priceErrorMessage = (EditText)findViewById(R.id.priceUserInput);
            priceErrorMessage.setError("Please Enter The Price");

        }//check to see if the name of the item is empty
        else if(textInput.isEmpty()){//only the item field is empty

            EditText errorMessage = (EditText)findViewById(R.id.editTextItem);
            errorMessage.setError("Please Enter an Item");

        }//at this point the price field is  empty
        else if(userPriceInput.isEmpty()){//checking to see the price field is empty

            EditText errorMessage = (EditText)findViewById(R.id.priceUserInput);
            errorMessage.setError("Please Enter The Price");
        }


    }

    //on click start route
    public void startRoute(View view) {

        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);

    }

    //on click empty the whole list
    public void emptyWholeList(View view) {

        this.groceryList.clear();

        displayGroceryList(this.groceryList);

    }

    //on click remove item
    public void removeItem(View view) {

        int itemPositionToDelete;

        String textInput = userInput.getText().toString().trim();

        //make sure there is a value in the text field after hitting the remove item button
        if(!textInput.isEmpty()){

            //at this point the item's name field is not empty but now we have to check that requested item is in the list
            //if yes, then just delete and display the updated list. If no, then display an error

            if(itemAlreadyOnList(groceryList,textInput)){

                itemPositionToDelete = findItemPosition(groceryList,textInput);
                groceryList.remove(itemPositionToDelete);

                //now display the update list
                displayGroceryList(groceryList);

            }//else display the not in the list error
            else{

                EditText errorMessage = (EditText)findViewById(R.id.editTextItem);
                errorMessage.setError("Item Doesn't Exist.");

            }

        }

        else//display error that an item's name must be provided before deleting it
        {
            EditText errorMessage = (EditText)findViewById(R.id.editTextItem);
            errorMessage.setError("Please Provide An Item Name to Delete");
        }


    }

    //Method to check if the price's input is a double
    private boolean priceInputTypeIsValid(String priceFormat)
    {
        //user might have enter mix characters with numbers so we could possible run into trouble
        //thus we have a try/catch
        try{
            Double.parseDouble(priceFormat);
            return true;
        }
        catch (NumberFormatException e)
        {

            return false;
        }
    }

    //Method to check that the double is a decimal with 2 numbers after the decimal
    public boolean validUserDecimalFormat(String userPrice){


        //find the postion of the decimal
        int decimalPosition = userPrice.indexOf(".");
        int valuesAfterDecimalCounter =0;

        //every time we have a value after the decimal add one to the counter
        for(int i = decimalPosition + 1; i < userPrice.length(); i++){
            valuesAfterDecimalCounter +=1;
        }

        //if the counter is greater than 2, then we have an invalid decimal price, return false
        if( valuesAfterDecimalCounter >2){
            return false;
        }
        return true;

    }

    //check to see if the item of interest  is on the list
    private boolean itemAlreadyOnList(ArrayList<itemInfo> groceryList, String textInput) {

        //loop thru the grocery list if we find a match
        for(int i =0; i < groceryList.size(); i++){

            if(groceryList.get(i).itemsAreEqual(groceryList.get(i),textInput)){
                //as soon as we find a math return true;
                return true;

            }
        }
        //at this point unable to find a match
        return false;
    }

    //find the item's position in the list
    private int findItemPosition(ArrayList<itemInfo> groceryList, String textInput) {

        int position =0;

        for(int i =0; i < groceryList.size(); i++){

            if(groceryList.get(i).itemsAreEqual(groceryList.get(i),textInput)){

                position =i;
                break;
            }
        }

        return position;
    }

    //Method to display the grocery List
    private void displayGroceryList(ArrayList<itemInfo> groceryList) {

        String items = "";

        //combine all the strings togther
        for(int i =0; i < groceryList.size(); i++){

            items+= groceryList.get(i).getAllInfo();
            items+= "\n";
        }

        //display the list to the use
        this.outputStr.setText(items);

    }

}

