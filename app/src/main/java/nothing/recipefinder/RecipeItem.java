package nothing.recipefinder;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Arsham on 11/25/2016.
 */

public class RecipeItem {


    private ImageView picOfItem;
    private String calories, fat, protein, carbs, time, description, title, category, type;

    private ArrayList<String> ingredients, steps;

    RecipeItem(){
        calories="0";
        fat="0";
        protein="0";
        carbs="0";
        time="0";
        description="Enter your description here";
        title="Enter title here";
        category="enter Category here";
        type="enter type here";
        ingredients=new ArrayList<String>();
        steps= new ArrayList<String>();
    }

    public String getCalories() {
        return calories;
    }

    public String getFat() {
        return fat;
    }

    public String getCarbs() {
        return carbs;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getProtein(){
        return protein;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void addIngredient(String s){
        ingredients.add(s);
    }

    public void addStep(String s){
        steps.add(s);
    }

    public void deleteIngredient(int p){
        ingredients.remove(p);
    }

    public void deleteStep(int p){
        steps.remove(p);
    }

    public void setProtein(String value){
        protein=value;
    }
    public void setCarbs(String value){
        carbs=value;
    }
    public void setCalories(String value){
        calories=value;
    }
    public void setFat(String value){
        fat=value;
    }
    public void setTime(String value){
        time=value;
    }
    public void setTitle(String changeTo){
        title=changeTo;
    }
    public void setDescription(String changeTo){
        description=changeTo;
    }
    public void setCategory(String changeTo){
        category=changeTo;
    }
    public void setType(String changeTo){
        type=changeTo;
    }
}
