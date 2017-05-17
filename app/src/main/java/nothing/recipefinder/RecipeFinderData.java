package nothing.recipefinder;

import java.util.ArrayList;

/**
 * Created by Arsham on 11/26/2016.
 */
public class RecipeFinderData {
    private static RecipeFinderData ourInstance = new RecipeFinderData();
    private ArrayList<RecipeItem> listOfAllRecipes, listForSearching;

    public static RecipeFinderData getInstance() {
        return ourInstance;
    }

    private RecipeFinderData() {
        RecipeItem initialEntry= new RecipeItem();
        listOfAllRecipes=new ArrayList<RecipeItem>();
        listForSearching=new ArrayList<RecipeItem>();
    }
    //getters and setters that we will use throughout the entire project
    public ArrayList<RecipeItem> getListForSearching() {
        return listForSearching;
    }

    public void clearSearchList(){
        listForSearching=new ArrayList<RecipeItem>();
    }

    public void setSearchList(ArrayList<RecipeItem> value){
        listForSearching=value;
    }


    public void addToSearch(RecipeItem add){
        listForSearching.add(add);
    }


    public ArrayList<RecipeItem> getListOfAllRecipes() {
        return listOfAllRecipes;
    }

}
