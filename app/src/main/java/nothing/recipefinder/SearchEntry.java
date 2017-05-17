package nothing.recipefinder;

/**
 * Created by Arsham on 11/27/2016.
 */

public class SearchEntry{
    int value;
    RecipeItem recipe;
    public SearchEntry(RecipeItem recipe, int value){
        this.recipe=recipe;
        this.value=value;
    }


    public void addValue(int k){
        value+=k;
    }


    public int getValue() {
        return value;
    }
    public RecipeItem getRecipe(){
        return recipe;
    }
}
