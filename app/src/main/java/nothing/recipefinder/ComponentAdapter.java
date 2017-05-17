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

public class ComponentAdapter extends ArrayAdapter<String> {
    ArrayList<String> listOfItems;
    Context theContext;
    ComponentAdapter(Context context,ArrayList<String> items){
        super(context,R.layout.entrybasis, items);
        theContext=context;
        listOfItems=items;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        //pull the entry we want to place in the list
        String toDisplay= listOfItems.get(position);
        //initialize the view that we are going to return//
        convertView = LayoutInflater.from(theContext).inflate(R.layout.entrybasis, null);
        //set up the textView and delete button
        TextView textToOutput=(TextView) convertView.findViewById(R.id.Entry);
        Button deleteButton= (Button) convertView.findViewById(R.id.deleteEntry);
        //set the actual text
        textToOutput.setText(position+1+". "+toDisplay);
        //set the button functionality
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                confirmationDialog(position);
            }
        });

        return convertView;
    }

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
        listOfItems.remove(position);
        this.notifyDataSetChanged();
    }


}
