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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Arsham on 11/27/2016.
 */

public class RecipeSearch extends Activity {

    //initialize variables including what type of search to make as well as the searchlist//
    private RadioButton HelpButton;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private LinearLayout relativeLayout;
    int searchType;
    EditText searchKeyValue, searchBlock;
    ArrayList<ArrayList<String>> listOfSearchBlocks, listOfExclusionBlocks;
    SearchEntryAdapter adapterForSearch, adapterForExclusion;
    RecipeFinderData dataBase = RecipeFinderData.getInstance();

    ArrayList<SearchEntry> filteredSearchListWithKeys;

    //the onCreate//
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchpage);
        //initialize variables
        searchKeyValue = (EditText) findViewById(R.id.valueForType);
        searchBlock = (EditText) findViewById(R.id.searchBlockInput);
        //initialize/re-initialize the list of search terms
        listOfSearchBlocks = new ArrayList<ArrayList<String>>();
        listOfExclusionBlocks = new ArrayList<ArrayList<String>>();
        //initialize type
        searchType = 0;
        TextView display = (TextView) findViewById(R.id.whatToSearch);
        display.setText("Type Search Active");
        //methods called for onClick functionality of the buttons
        CheckTypeSearch();
        CancelSearch();
        onClickAddButton();
        onClickExcludeButton();
        InitiateSearchTrigger();
        helpbutton();
        //set up adapter for the listviews
        ListView searchTermList = (ListView) findViewById(R.id.listOfSearchTerms);
        ListView exclusionTerms = (ListView) findViewById(R.id.ExclusionBlocks);

        adapterForSearch = new SearchEntryAdapter(this, listOfSearchBlocks);
        adapterForExclusion = new SearchEntryAdapter(this, listOfExclusionBlocks);
        searchTermList.setAdapter(adapterForSearch);
        exclusionTerms.setAdapter(adapterForExclusion);

    }

    //method for what happens when changing type of search
    public void CheckTypeSearch() {
        Button change = (Button) findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView display = (TextView) findViewById(R.id.whatToSearch);
                if (searchType==2) {
                    display.setText("Type Search Active");
                    searchType = 0;
                } else if(searchType==0) {
                    display.setText("Category Search Active");
                    searchType = 1;
                }else if(searchType==1){
                    display.setText("No Specific Search Methodology");
                    searchType = 2;
                }
            }
        });
    }

    //method for the onclick of Search//
    private void InitiateSearchTrigger() {
        Button searchButton = (Button) findViewById(R.id.DoSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchType == 0) {
                    InitializeTypeList();
                    //if there are no search entries it doesn't search//
                    //our algorithm requires alot of moving around of arrays so it's required to completely avoid using it
                    //when possesing zero search terms or else the results would be empty
                    if(listOfSearchBlocks.size()!=0) {
                        filteredSearchListWithKeys = SearchStarter(listOfSearchBlocks, dataBase.getListForSearching());
                        dataBase.setSearchList(sortFinal(filteredSearchListWithKeys));
                    }
                    DeleteUnwanted();
                    Intent toResults = new Intent(RecipeSearch.this, SearchResultsPage.class);
                    startActivity(toResults);
                } else if (searchType == 1) {
                    InitializeCategoryList();
                    //if there are no search entries it doesn't search//
                    //our algorithm requires alot of moving around of arrays so it's required to completely avoid using it
                    //when possesing zero search terms or else the results would be empty
                    if(listOfSearchBlocks.size()!=0) {
                        filteredSearchListWithKeys = SearchStarter(listOfSearchBlocks, dataBase.getListForSearching());
                        dataBase.setSearchList(sortFinal(filteredSearchListWithKeys));
                    }
                    DeleteUnwanted();
                    Intent toResults = new Intent(RecipeSearch.this, SearchResultsPage.class);
                    startActivity(toResults);
                }else if (searchType ==2){
                    InitializeNonSpecialSearch();
                    //if there are no search entries it doesn't search//
                    //our algorithm requires alot of moving around of arrays so it's required to completely avoid using it
                    //when possesing zero search terms or else the results would be empty
                    if(listOfSearchBlocks.size()!=0) {
                            filteredSearchListWithKeys = SearchStarter(listOfSearchBlocks, dataBase.getListForSearching());
                            dataBase.setSearchList(sortFinal(filteredSearchListWithKeys));
                    }
                    DeleteUnwanted();
                    Intent toResults = new Intent(RecipeSearch.this, SearchResultsPage.class);
                    startActivity(toResults);
                }


            }
        });

    }

//starts up the search array for type searching
    public void InitializeTypeList() {
        //sets up the list that will be used to display the search results
        ArrayList<RecipeItem> listToFilter = dataBase.getListOfAllRecipes();
        //finds out what type we're going to search for
        String filter = searchKeyValue.getText().toString();
        //clears the list to make sure the previous search does not collide
        dataBase.clearSearchList();
        for (int i = 0; i < listToFilter.size(); i++) {
            if (listToFilter.get(i).getType().equals(filter)) {
                dataBase.addToSearch(listToFilter.get(i));
            }
        }
    }
//starts up the search array for category searching
    public void InitializeCategoryList() {
        //sets up the list that will be used to display the search results
        ArrayList<RecipeItem> listToFilter = dataBase.getListOfAllRecipes();
        //finds out what category we're going to search for
        String filter = searchKeyValue.getText().toString();
        //clears the list to make sure the previous search does not collide
        dataBase.clearSearchList();
        for (int i = 0; i < listToFilter.size(); i++) {
            if (listToFilter.get(i).getCategory().equals(filter)) {
                dataBase.addToSearch(listToFilter.get(i));
            }
        }
    }

    public void InitializeNonSpecialSearch(){
        ArrayList<RecipeItem> listToFilter = dataBase.getListOfAllRecipes();
        dataBase.clearSearchList();
        // no search term specifically set for this one//
        //initialize the raw list with no filters
        for (int i=0;i<listToFilter.size();i++){
            dataBase.addToSearch(listToFilter.get(i));
        }
    }

    //method for cancelling the search
    public void CancelSearch() {
        Button cancelButton = (Button) findViewById(R.id.cancelSearch);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToList();
            }
        });
    }

    //method for returning back to the list of recipes
    public void BackToList() {
        Intent intent = new Intent(RecipeSearch.this, RecipeList.class);
        startActivity(intent);
    }

    public void helpbutton(){
        HelpButton = (RadioButton) findViewById(R.id.searchHelp);
        relativeLayout = (LinearLayout) findViewById(R.id.search_layout);

        HelpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.helppopupsearch,null);

                popupWindow = new PopupWindow(container,1500,900,true);
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

    public void onClickAddButton() {
        Button addButton = (Button) findViewById(R.id.addIngredient);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToSearch();
            }
        });
    }

    public void onClickExcludeButton() {
        Button addButton = (Button) findViewById(R.id.exclusionAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToExclusion();
            }
        });
    }

    public void AddToSearch() {
        final EditText searchBlock = (EditText) findViewById(R.id.searchBlockInput);
        String entries = searchBlock.getText().toString();
        ArrayList<String> block = new ArrayList<String>(Arrays.asList(entries.split(" ")));
        listOfSearchBlocks.add(block);
        searchBlock.setText("");
        adapterForSearch.notifyDataSetChanged();
    }

    public void AddToExclusion(){
        final EditText searchBlock = (EditText) findViewById(R.id.searchBlockInput);
        String entries = searchBlock.getText().toString();
        ArrayList<String> block = new ArrayList<String>(Arrays.asList(entries.split(" ")));
        listOfExclusionBlocks.add(block);
        searchBlock.setText("");
        adapterForExclusion.notifyDataSetChanged();
    }

    //will do one run of the search for one recipe item and set it's key(the level of relevance
    public int AndOrSearch(ArrayList<ArrayList<String>> array, ArrayList<String> ingredients) {
        int value = 0;
        for (int i = 0; i < array.size(); i++) {
            //star the algorithm with the marker assuming that the search will be positive
            boolean x = true;
            for (int j = 0; j < array.get(i).size(); j++) {//goes through each sub list
                //System.out.println(array.get(i).get(j));
                if (ingredients.contains(array.get(i).get(j)) == false) {//makes sure blocks are in recipe
                    //if recipe does not contain item in block the marker will be set false//
                    x = false;
                }
            }
            if (x == true) value += array.get(i).size();
        }
        return value;
    }
//This method will be used to remove entries that we wish to exclue//
    public boolean AndOrExclude(ArrayList<ArrayList<String>> excludeTerms, ArrayList<String> ingredients,RecipeItem ToDelete) {
        int value = 0;
        for (int i = 0; i < excludeTerms.size(); i++) {
            //star the algorithm with the marker assuming that the search will be positive
            int sizeOfMatches = 0;
            //this is the flag which will be started as false
            boolean doesItMatch=false;
            for (int j = 0; j < excludeTerms.get(i).size(); j++) {//goes through each sub list
                //if every single ingredient in the exclude subterm match with what the recipe has it will be deleted
                if (ingredients.contains(excludeTerms.get(i).get(j)) == true) {//makes sure blocks are in recipe
                    //if recipe does not contain item in block the marker will be set false//
                    sizeOfMatches = sizeOfMatches+1;
                }
            }
            //if the amount of matches are equal to the size of the block the flag is set to true
            if(sizeOfMatches==excludeTerms.get(i).size()){
                doesItMatch=true;
            }
            //if the flag is positive it deletes the entry and ends the code
            if (doesItMatch == true){
                dataBase.getListForSearching().remove(ToDelete);
                return true;
            }
        }
        //if no match is found in the exclusion there is no delete
        return false;
    }
//to filter out unwanted ingredients, this method automatically filters through the search list.
    public void DeleteUnwanted(){
        //initializeList
        ArrayList<RecipeItem> listToFilter=dataBase.getListForSearching();
        //use and or exclude for each recipe entry deleting as it finds matches
        for(int i=0;i< listToFilter.size();i++){
            AndOrExclude(listOfExclusionBlocks,listToFilter.get(i).getIngredients(), listToFilter.get(i));
        }
    }

    //will do the whole run of the search//
    public ArrayList<SearchEntry> SearchStarter(ArrayList<ArrayList<String>> wantedIngredients, ArrayList<RecipeItem> recipe) {
        ArrayList<SearchEntry> list = new ArrayList<SearchEntry>();
        for (int i = 0; i < recipe.size(); i++) {
            //check how many relevant hits the Recipe Gets//
            int value = AndOrSearch(wantedIngredients, recipe.get(i).getIngredients());
            //check if the searchEntry has any relevant items and if it does, add it to the entrylist
            if (value != 0) {
                //places the entry in a data structu
                SearchEntry searchTerm = new SearchEntry(recipe.get(i), value);
                list.add(searchTerm);
            }


        }
        //return the list
        return list;

    }

    //method to sort by the keys
    public ArrayList<RecipeItem> sortFinal(ArrayList<SearchEntry> search) {

        for (int i = 0; i < search.size() - 1; i++) {//sort vertices
            SearchEntry x = search.get(i);
            int index = 0;

            for (int j = i + 1; j < search.size(); j++) {

                if (search.get(j).getValue() < x.getValue()) {
                    x = search.get(j);
                    index = j;
                }
            }
            if (x != search.get(i)) {
                SearchEntry temp = x;
                SearchEntry y = search.get(i);
                search.set(i, x);
                search.set(index, y);

            }


        }
        ArrayList<RecipeItem> x = new ArrayList<RecipeItem>();
        for (int i = search.size()-1; i >=0; i--) {
            x.add(search.get(i).getRecipe());
            System.out.println(search.get(i).getValue());
        }
        return x;
        //for (int i=0;i<s)

    }
}
