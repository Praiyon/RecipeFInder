package nothing.recipefinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static nothing.recipefinder.R.id.enterlist1;

public class MainActivity extends Activity {
    private RadioButton HelpButton;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToList();
        helpbutton();
        RecipeFinderData listData= RecipeFinderData.getInstance();
        final TextView title = (TextView) findViewById(R.id.textview2);
        final Button enterlist1 = (Button) findViewById(R.id.enterlist1);

        title.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
        enterlist1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));

        ImageView Steak = (ImageView) findViewById(R.id.imageView5);
        Steak.setImageResource(R.drawable.steak);

        ImageView Fork = (ImageView) findViewById(R.id.imageView3);
        Fork.setImageResource(R.drawable.fork);

        ImageView DrinkAndNapkin = (ImageView) findViewById(R.id.imageView2);
        DrinkAndNapkin.setImageResource(R.drawable.drankn);


        Steak.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
        Fork.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
        DrinkAndNapkin.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));

    }


    public void goToList(){
        Button button = (Button) findViewById(R.id.enterlist1);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,RecipeList.class);
                startActivity(intent);
            }

        });
    }

    public void goToRecipe(View view){
        Intent intent= new Intent(getBaseContext(), RecipeList.class);
        startActivity(intent);


    }



    public void helpbutton(){
        HelpButton = (RadioButton) findViewById(R.id.MenuHelp);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);

        HelpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.helppopup,null);

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




