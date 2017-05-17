package nothing.recipefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Arsham on 11/27/2016.
 */

public class SearchResultsPage extends Activity {
    RecipeSearchAdapter adapter;
    RecipeFinderData dataBase = RecipeFinderData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresults);
        Intent receivedIntent = getIntent();
        int resultType = receivedIntent.getIntExtra("Result", 0);
        if (resultType == 0) {
            RecipeItem temp = new RecipeItem();
            adapter = new RecipeSearchAdapter(this, dataBase.getListForSearching());
            ListView resultView = (ListView) findViewById(R.id.searchResults);

            resultView.setAdapter(adapter);

        }
        //initialize the buttons//
        BackButton();
        newSearch();
    }

    public void BackButton(){
        Button back=(Button) findViewById(R.id.backToList);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchResultsPage.this,RecipeList.class);
                startActivity(intent);
            }
        });
    }

    public void newSearch(){
        Button newSearch=(Button) findViewById(R.id.searchButton);
        newSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchResultsPage.this,RecipeSearch.class);
                startActivity(intent);
            }
        });
    }

}
