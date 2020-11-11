package com.mapbox.storelocator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

//public class addingItems extends AppCompatActivity

public class addingItems extends AppCompatActivity {

    EditText userInput; //get user input
    Button sumbitButton; //the button used to hit the sumbit

    List<String> groceryList = new ArrayList<String>(); //list to keep track of the current grocery list
    TextView outputStr;// string to be output in the mobile page

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
        System.out.println("******************");

        //make sure string is not empty
        if(!textInput.isEmpty()){

            //create new item
            product = new itemInfo(textInput);

            groceryList.add(textInput);
            //productList.add(product);

            strOfItems+="\n";
            //strOfItems+= textInput;
            strOfItems+= product.getAllInfo();


            //Toast.makeText(this,strOfItems, Toast.LENGTH_SHORT).show();
            outputStr.setText(strOfItems);
        }


    }
    //on click start route
    public void startRoute(View view) {

        System.out.println("about to create a new intent");
           Intent intent = new Intent(this, MapActivity.class);
           startActivity(intent);
        System.out.println("exiting method....");

    }

    //on click empty the whole list
    public void emptyWholeList(View view) {

    }

    //on click remove item
    public void removeItem(View view) {

    }
}

/*

    public void itemSubmit(View view) {

        //read the input from user and trim any trailing spaces
        String textInput = userInput.getText().toString().trim();

        //make sure string is not empty
        if(!textInput.isEmpty()){

            //create new item
            item = new itemInfo(textInput);

            groceryList.add(textInput);
            strOfItems+="\n";
            strOfItems+= textInput;


            //Toast.makeText(this,strOfItems, Toast.LENGTH_SHORT).show();
            outputStr.setText(strOfItems);

        }



    }


 */