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

import static nothing.recipefinder.R.id.EditRecipe;
import static nothing.recipefinder.R.id.mainRecipeList;
import static nothing.recipefinder.R.id.textView;

/**
 * Created by Arsham on 11/25/2016.
 */

public class RecipeAdapter extends ArrayAdapter<RecipeItem> {
    private ArrayList<RecipeItem> listOfItems;
    private Context theContext;



    RecipeAdapter(Context context, ArrayList<RecipeItem> allItems){
        super(context, R.layout.listviewbasis, allItems);
        theContext=context;
        listOfItems=allItems;
    }

    public View getView(final int position, View convertView, ViewGroup parent){


        RecipeItem theRecipe= listOfItems.get(position);

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

        editRecipe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(theContext,RecipeEdit.class);
                intent.putExtra("search",false);
                intent.putExtra("EditRecipe", position);
                notifyData();
                theContext.startActivity(intent);
            }
        });

        deleteRecipe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                confirmationDialog(position);
            }
        });





        return convertView;

    }

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

    private void Delete(int position){
        RecipeFinderData dataset = RecipeFinderData.getInstance();
        ArrayList<RecipeItem> listOfRecipes= dataset.getListOfAllRecipes();
        listOfRecipes.remove(position);
        this.notifyDataSetChanged();
    }

    public void notifyData(){
        this.notifyDataSetChanged();
    }

}
