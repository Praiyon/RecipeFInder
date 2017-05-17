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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arsham on 11/27/2016.
 */

public class ListOfIngredients extends Activity {
    private RadioButton HelpButton;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private LinearLayout relativeLayout;
    ComponentAdapter adapter;
    ArrayList<String> Ingredients;
    RecipeFinderData dataBase= RecipeFinderData.getInstance();
    int indexOfRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generallistformat);
        //adding the onclick for add
        onClickAddButton();
        goBack();
        helpbutton();
        //setText
        TextView whatType=(TextView) findViewById(R.id.displayType);
        whatType.setText("Ingredients");
        //receive intent
        Intent receivedIntent = getIntent();
        //gets the index of the ingredients
        indexOfRecipe=receivedIntent.getIntExtra("EditIngredients",0);
        //finds out which list to receive it from (either the searchlist or the display list//
        final boolean search=receivedIntent.getBooleanExtra("search",false);
        //initialize list of Ingredients
        if (!search) {
            Ingredients = dataBase.getListOfAllRecipes().get(indexOfRecipe).getIngredients();
        }else{
            Ingredients=dataBase.getListForSearching().get(indexOfRecipe).getIngredients();
        }
        //initialize adapter
        adapter=new ComponentAdapter(this,Ingredients);
        //set listview with the list of ingredients
        ListView componentList= (ListView) findViewById(R.id.listOfEntries);
        componentList.setAdapter(adapter);
    }

    public void onClickAddButton(){
        final Button addButton = (Button) findViewById(R.id.addToList);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AddToSearch();
            }
        });
    }

    public void AddToSearch(){
        final EditText componentBlock= (EditText) findViewById(R.id.stringToAdd);
        String toAdd= componentBlock.getText().toString();
        Ingredients.add(toAdd);
        componentBlock.setText("");
        adapter.notifyDataSetChanged();
    }

    public void goBack(){
        Button back=(Button) findViewById(R.id.goBack);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toSteps= new Intent(ListOfIngredients.this,RecipeEdit.class);
                toSteps.putExtra("EditRecipe",indexOfRecipe);
                startActivity(toSteps);
            }
        });
    }

    public void helpbutton(){
        HelpButton = (RadioButton) findViewById(R.id.SIHelp);
        relativeLayout = (LinearLayout) findViewById(R.id.SIlayout);

        HelpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.helppopupingred,null);

                popupWindow = new PopupWindow(container,800,800,true);
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
