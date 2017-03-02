/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.uk.emr.greg.Android_Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * @author deakig
 */

public class GameBoard extends View{

    private Paint p;
    private List<Point> starField = null;
    private List<Integer> starBright = null;
    private List<Integer> starFadeList = null;
    private List<Integer> starSize = null;
    private int starAlpha = 80;
    
    private int starFade = 2;
    private static final int NUM_OF_STARS = 25;
                
    public GameBoard(Context context, AttributeSet aSet) {
        super(context, aSet);
        //it's best not to create any new objects in the on draw
        //initialize them as class variables here
        p = new Paint();
    }
    
    private void initializeStars(int maxX, int maxY) {
        starField = new ArrayList<Point>();
        starBright = new ArrayList<Integer>();
        starFadeList = new ArrayList<Integer>();
        starSize = new ArrayList<Integer>();
        for (int i=0; i<NUM_OF_STARS; i++) {
                Random r = new Random();
                int x = r.nextInt(maxX-5+1)+5;
                int y = r.nextInt(maxY-5+1)+5;
                starField.add(new Point (x,y));
                int rn = r.nextInt(172) + 80;
                starBright.add(rn);
                int rs = r.nextInt(3) - 6;
                starFadeList.add(rs);
                starSize.add(r.nextInt(6)+2);
         }
    }
    
    synchronized public void resetStarField() {
        starField = null;
    }
    
    @Override
    synchronized public void onDraw(Canvas canvas) {
        //create a black canvas
        p.setColor(Color.BLACK);
        p.setAlpha(255);
        p.setStrokeWidth(1);
        canvas.drawRect(0, 0, getWidth(), getHeight(), p);
        //initialize the starfield if needed
        if (starField==null) {
                initializeStars(canvas.getWidth(), canvas.getHeight());
        }
        //draw the stars
        p.setColor(Color.CYAN);
        int count=0;
        for (int i:starBright ){
            //System.out.println(i);
            starFade=starFadeList.get(count);
            if (i>=252 || i <=80) starFade=starFade*-1;
            starAlpha=i+starFade;
            starBright.set(count,starAlpha);
            starFadeList.set(count,starFade);
           
            p.setAlpha(starAlpha);
            p.setStrokeWidth(starSize.get(count));
           
            canvas.drawPoint(starField.get(count).x, starField.get(count).y, p);
           
            count++;
        }
        //p.setAlpha(starAlpha+=starFade);
        //fade them in and out

    }
    
}
