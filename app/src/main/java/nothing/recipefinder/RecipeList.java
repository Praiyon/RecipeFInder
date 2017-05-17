package nothing.recipefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Praiyon on 2016-11-25.
 */

public class RecipeList extends Activity {
    ArrayList<RecipeItem> listOfRecipes;
    RecipeAdapter adapterToUse;
    private RadioButton HelpButton;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipelist);
        //add the button onclick functionalities
        theCreateButton();
        theSearchButton();
        helpbuttontwo();


        ListView recipeListView = (ListView) findViewById(R.id.mainRecipeList);

        RecipeFinderData listData= RecipeFinderData.getInstance();

        listOfRecipes=listData.getListOfAllRecipes();


        adapterToUse= new RecipeAdapter(this,listOfRecipes);
        recipeListView.setAdapter(adapterToUse);
    }
//the onclick method for the create new recipe button//
    public void theCreateButton(){
        Button button = (Button) findViewById(R.id.CreateNew);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(RecipeList.this,RecipeEdit.class);
                CreateRecipe();
                intent.putExtra("Created",true);
                intent.putExtra("EditRecipe",listOfRecipes.size()-1);
                startActivity(intent);

            }

        });
    }

    public void theSearchButton(){
        Button button = (Button) findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RecipeList.this,RecipeSearch.class);
                startActivity(intent);
            }
        });
    }

    //this method is used to add a new recipe into the storage//
    private void CreateRecipe(){
    RecipeItem createdRecipe=new RecipeItem();
    listOfRecipes.add(createdRecipe);
    adapterToUse.notifyDataSetChanged();

}
    public void helpbuttontwo(){
        HelpButton = (RadioButton) findViewById(R.id.listHelp);
        relativeLayout = (RelativeLayout) findViewById(R.id.recipelist);

        HelpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.helppopup2,null);

                popupWindow = new PopupWindow(container,1450,900,true);
                popupWindow.setAnimationStyle(android.R.style.Animation_Toast);
                popupWindow.showAtLocation(relativeLayout, Gravity.NO_GRAVITY,320,710);

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


}
