package nothing.recipefinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Praiyon on 2016-11-26.
 */

public class RecipeEdit extends Activity {
    //initialize the variables that are going to be used for the buttons and layouts
    private RadioButton HelpButton;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;
    RecipeFinderData data= RecipeFinderData.getInstance();
    ArrayList<RecipeItem> listOfRecipes = data.getListOfAllRecipes();
    RecipeItem recipeToEdit;
    EditText theTitle, theDescription, theCategory,theType,time,calories,protein,fat,carbs;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipeedit);
        ImageView Spag = (ImageView) findViewById(R.id.imageView7);
        Spag.setImageResource(R.drawable.ab);

        RecipeFinderData listOfRecipes= RecipeFinderData.getInstance();
        //recieve the intent
        Intent receivedIntent = getIntent();
        //finds the index//
        final int indexOfRecipe=receivedIntent.getIntExtra("EditRecipe",0);
        //finds out if we're receiving the request to access this page from searching or not
        //used to decide which list to access to get the contents of the recipe
        final boolean search=receivedIntent.getBooleanExtra("search",false);

        //initialize all edit texts that are going to be used
        //they are all usable throughout the activity for the methods to use
        if(!search) {
            recipeToEdit = listOfRecipes.getListOfAllRecipes().get(indexOfRecipe);
        }else{
            recipeToEdit = listOfRecipes.getListForSearching().get(indexOfRecipe);
        }
        //initiating the buttons from the layout so we can change them at will
        theTitle = (EditText) findViewById(R.id.RecipeTitle);
        theDescription = (EditText) findViewById(R.id.RecipeDescription);
        theCategory = (EditText) findViewById(R.id.RecipeCategory);
        theType= (EditText) findViewById(R.id.RecipeType);
        calories = (EditText) findViewById(R.id.calorieValue);
        time = (EditText) findViewById(R.id.timeEdit);
        carbs = (EditText) findViewById(R.id.carbsValue);
        protein = (EditText) findViewById(R.id.proteinValue);
        fat = (EditText) findViewById(R.id.fatValue);

        //place the values of the recipe into the text
        theTitle.setText(recipeToEdit.getTitle());
        theDescription.setText(recipeToEdit.getDescription());
        theCategory.setText(recipeToEdit.getCategory());
        theType.setText(recipeToEdit.getType());
        calories.setText(recipeToEdit.getCalories());
        time.setText(recipeToEdit.getTime());
        carbs.setText(recipeToEdit.getCarbs());
        protein.setText(recipeToEdit.getProtein());
        fat.setText(recipeToEdit.getFat());

        //set up the buttons
        Button recipeDelete=(Button) findViewById(R.id.deleteButton);
        Button cancelOperation=(Button) findViewById(R.id.cancelButton);
        Button recipeSave=(Button) findViewById(R.id.saveButton);
        Button toIngredients=(Button) findViewById(R.id.goToIngredients);
        Button toSteps=(Button) findViewById(R.id.goToSteps);
        recipeDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                confirmationDialog();
            }
        });
        cancelOperation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cancelOperation();
            }
        });
        recipeSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            saveChanges();
            }
        });
        Button HelpButton=(RadioButton) findViewById(R.id.EditHelp);
        relativeLayout = (RelativeLayout) findViewById(R.id.edit_layout);
        toIngredients.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toIngredients= new Intent(RecipeEdit.this,ListOfIngredients.class);
                toIngredients.putExtra("EditIngredients",indexOfRecipe);
                if(search) {
                    toIngredients.putExtra("search", true);
                }else{
                    toIngredients.putExtra("search", false);
                }
                saveChanges();
                startActivity(toIngredients);
            }
        });
        toSteps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toSteps= new Intent(RecipeEdit.this,ListOfSteps.class);
                toSteps.putExtra("EditSteps",indexOfRecipe);
                if(search) {
                    toSteps.putExtra("search", true);
                }else{
                    toSteps.putExtra("search", false);
                }
                saveChanges();
                startActivity(toSteps);
            }
        });


        HelpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.helppopupthree,null);

                popupWindow = new PopupWindow(container,1500,2000,true);
                popupWindow.setAnimationStyle(android.R.style.Animation_Toast);
                popupWindow.showAtLocation(relativeLayout, Gravity.NO_GRAVITY,320,900);

                container.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent){
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });
    }
//method to delete recipes
    private void deleteRecipe(){

        listOfRecipes.remove(recipeToEdit);
        cancelOperation();

    }
//confirmation dialog to counter accidental deletion
    public void confirmationDialog(){
        AlertDialog.Builder confirmDelete =new AlertDialog.Builder(this);
        confirmDelete.setMessage("Are You Sure You Want To Delete This Recipe?");
        confirmDelete.setCancelable(true);
        confirmDelete.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int na) {
                        deleteRecipe();

                    }
                });
        confirmDelete.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int na) {


            }
        });
        confirmDelete.create().show();

    }
//to return to the list without saving
    private void cancelOperation(){
        Intent returnToList= new Intent(this,RecipeList.class);
        startActivity(returnToList);
    }
    //onclick functionality for the boxes to clear upon being clicked//

//a method to save changes done to the program (used when pressing save and when going into steps/ingredients so
    //you don't lose progress
    private void saveChanges(){
        recipeToEdit.setTitle(theTitle.getText().toString());
        recipeToEdit.setDescription(theDescription.getText().toString());
        recipeToEdit.setCategory(theCategory.getText().toString());
        recipeToEdit.setType(theType.getText().toString());
        recipeToEdit.setCalories(calories.getText().toString());
        recipeToEdit.setTime(time.getText().toString());
        recipeToEdit.setCarbs(carbs.getText().toString());
        recipeToEdit.setProtein(protein.getText().toString());
        recipeToEdit.setFat(fat.getText().toString());
        cancelOperation();
    }
}
