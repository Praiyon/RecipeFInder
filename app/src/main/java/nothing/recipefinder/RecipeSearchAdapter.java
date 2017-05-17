package nothing.recipefinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arsham on 11/29/2016.
 */

public class RecipeSearchAdapter extends ArrayAdapter<RecipeItem> {
    private ArrayList<RecipeItem> listOfItems;
    private Context theContext;



    RecipeSearchAdapter(Context context, ArrayList<RecipeItem> allItems){
        super(context, R.layout.listviewbasis, allItems);
        theContext=context;
        listOfItems=allItems;
    }

    public View getView(final int position, View convertView, ViewGroup parent){


        RecipeItem theRecipe= listOfItems.get(position);
        //set all the text values within the adapter basis//
        convertView = LayoutInflater.from(theContext).inflate(R.layout.listviewbasis, null);
        TextView theTitle= (TextView) convertView.findViewById(R.id.title);
        TextView theDescription= (TextView) convertView.findViewById(R.id.description);
        TextView theCategory= (TextView) convertView.findViewById(R.id.Category);
        TextView theType= (TextView) convertView.findViewById(R.id.Type);
        Button editRecipe= (Button) convertView.findViewById(R.id.EditRecipe);
        Button deleteRecipe= (Button) convertView.findViewById(R.id.DeleteRecipe);
        ImageView spag = (ImageView) convertView.findViewById(R.id.imageView7);
        spag.setImageResource(R.drawable.ab);

        theTitle.setText(theRecipe.getTitle());
        theDescription.setText(theRecipe.getDescription());
        theCategory.setText(theRecipe.getCategory());
        theType.setText(theRecipe.getType());
        //add the onclick functionality so the edit button can work//
        editRecipe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(theContext,RecipeEdit.class);
                intent.putExtra("search",true);
                intent.putExtra("EditRecipe", position);
                notifyData();
                theContext.startActivity(intent);
            }
        });
        //add onclick functionality so you can delete recipes from the listView
        deleteRecipe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                confirmationDialog(position);
            }
        });





        return convertView;

    }
    //creates a confirmation dialog when you want to delete the recipe so there are no accidental delets
    public void confirmationDialog(final int currentPosition){
        AlertDialog.Builder confirmDelete =new AlertDialog.Builder(theContext);
        confirmDelete.setMessage("Are You Sure You Want To Delete This Recipe?");
        confirmDelete.setCancelable(true);
        confirmDelete.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int na) {
                        Delete(currentPosition);

                    }
                });
        confirmDelete.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int na) {


            }
        });
        confirmDelete.create().show();

    }
    //method used to delete the recipe off from the arraylist we are using
    private void Delete(int position){
        RecipeFinderData dataset = RecipeFinderData.getInstance();
        ArrayList<RecipeItem> listOfRecipes= dataset.getListForSearching();
        listOfRecipes.remove(position);
        this.notifyDataSetChanged();
    }
    //couldn't call to update the adapter for some reason so i made it into a method to use
    public void notifyData(){
        this.notifyDataSetChanged();
    }

}
