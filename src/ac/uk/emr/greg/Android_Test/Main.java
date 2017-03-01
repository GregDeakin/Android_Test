package ac.uk.emr.greg.Android_Test;

//import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener
{
    
    private Handler frame = new Handler();
    
    //Divide the frame by 1000 to calculate how many times per second the screen will update.
    private static final int FRAME_RATE = 20; //50 frames per second
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Handler h = new Handler();
        ((Button)findViewById(R.id.the_button)).setOnClickListener(this);
        
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                initGfx();
            }
         }, 1000);
    }

    synchronized public void initGfx() {
        ((GameBoard)findViewById(R.id.the_canvas)).resetStarField();
        ((Button)findViewById(R.id.the_button)).setEnabled(true);
        //It's a good idea to remove any existing callbacks to keep
        //them from inadvertently stacking up.
        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, FRAME_RATE);
    }
    
    public void onClick(View v) {
        initGfx();
    }
    
    private Runnable frameUpdate = new Runnable() {
          @Override
          synchronized public void run() {
                  frame.removeCallbacks(frameUpdate);
                  //make any updates to on screen objects here
                  //then invoke the on draw by invalidating the canvas
                  ((GameBoard)findViewById(R.id.the_canvas)).invalidate();
                  frame.postDelayed(frameUpdate, FRAME_RATE);
          }
     };
    

}
