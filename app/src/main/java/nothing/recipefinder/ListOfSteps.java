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
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arsham on 11/27/2016.
 */

public class ListOfSteps extends Activity {
    private RadioButton HelpButton;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private LinearLayout relativeLayout;
    ComponentAdapter adapter;
    ArrayList<String> steps;
    RecipeFinderData dataBase= RecipeFinderData.getInstance();
    Button addButton;
    int indexOfRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generallistformat);
        //forgot to add onclick
        //initialize button
        addButton = (Button) findViewById(R.id.addToList);
        onClickAddButton();
        goBack();
        helpbutton();
        //setText
        TextView whatType=(TextView) findViewById(R.id.displayType);
        whatType.setText("Steps");
        //receive intent
        Intent receivedIntent = getIntent();
        indexOfRecipe=receivedIntent.getIntExtra("EditSteps",0);
        final boolean search=receivedIntent.getBooleanExtra("search",false);
        //set view
        //initialize the steps to be used for the adapter
        if(!search) {
            steps = dataBase.getListOfAllRecipes().get(indexOfRecipe).getSteps();
        }else{
            steps=dataBase.getListForSearching().get(indexOfRecipe).getSteps();
        }
        //initialize  listview
        ListView componentList= (ListView) findViewById(R.id.listOfEntries);
        //initialize adapter
        adapter=new ComponentAdapter(this,steps);
        //set the adapter
        componentList.setAdapter(adapter);

    }

    public void onClickAddButton(){
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
        //display in a nice format//
        steps.add(toAdd);
        componentBlock.setText("");
        adapter.notifyDataSetChanged();
    }

    public void goBack(){
        Button back=(Button) findViewById(R.id.goBack);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toSteps= new Intent(ListOfSteps.this,RecipeEdit.class);
                toSteps.putExtra("EditRecipe",indexOfRecipe );
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
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.helppopupsteps,null);

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
