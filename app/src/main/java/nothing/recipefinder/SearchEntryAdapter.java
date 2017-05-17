package nothing.recipefinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arsham on 11/27/2016.
 */

public class SearchEntryAdapter extends ArrayAdapter<ArrayList<String>> {
    ArrayList<ArrayList<String>> listOfEntries;
    Context theContext;
    SearchEntryAdapter(Context context, ArrayList<ArrayList<String>> allItems){
        super(context, R.layout.entrybasis, allItems);
        theContext=context;
        listOfEntries=allItems;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        //pull the entry we want to place in the list
        ArrayList<String> entryBlock= listOfEntries.get(position);
        //put the list of entries (ingredients) in a nice format
        String toDisplay=entryBlock.get(0);
        for(int i=1;i<entryBlock.size();i++){
            toDisplay=toDisplay+" AND "+entryBlock.get(i);
        }
        //initialize the view that we are going to return//
        convertView = LayoutInflater.from(theContext).inflate(R.layout.entrybasis, null);
        //set up the textView and delete button
        TextView textToOutput=(TextView) convertView.findViewById(R.id.Entry);
        Button deleteButton= (Button) convertView.findViewById(R.id.deleteEntry);
        //set the actual text
        textToOutput.setText(toDisplay);
        //set the button functionality
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                confirmationDialog(position);
            }
        });

        return convertView;
    }
    //delete confirmation to counter accidental deletion
    public void confirmationDialog(final int currentPosition){
        AlertDialog.Builder confirmDelete =new AlertDialog.Builder(theContext);
        confirmDelete.setMessage("Are You Sure You Want To Delete This Entry?");
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
        listOfEntries.remove(position);
        this.notifyDataSetChanged();
    }

}
