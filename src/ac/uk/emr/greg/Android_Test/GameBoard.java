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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * @author deakig
 */

public class GameBoard extends View{

    private Paint p;
    
    //Add starfield variables 
    private List<Point> starField = null;
    private List<Integer> starBright = null;
    private List<Integer> starFadeList = null;
    private List<Integer> starSize = null;
    private int starAlpha = 80;
    private int starFade = 2;
    private static final int NUM_OF_STARS = 25;
    
    //Add private variables to keep up with sprite position and size
    private Rect sprite1Bounds = new Rect(0,0,0,0);
    private Rect sprite2Bounds = new Rect(0,0,0,0);
    private Point sprite1;
    private Point sprite2;
    //Bitmaps that hold the actual sprite images
    private Bitmap bm1 = null;
    private Bitmap bm2 = null;
    private Matrix m = null;
    private int sprite1Rotation = 0;
    //sprite controlers
    synchronized public void setSprite1(int x, int y) {
        sprite1=new Point(x,y);
    }
    synchronized public void setSprite2(int x, int y) {
        sprite2=new Point(x,y);
    }    
    synchronized public int getSprite1X() {
        return sprite1.x;
    }
      synchronized public int getSprite1Y() {
            return sprite1.y;
      }    
    synchronized public int getSprite2X() {
        return sprite2.x;
    }
    synchronized public int getSprite2Y() {
        return sprite2.y;
    }    
    synchronized public int getSprite1Width() {
        return sprite1Bounds.width();
    }
    synchronized public int getSprite1Height() {
        return sprite1Bounds.height();
    }
    synchronized public int getSprite2Width() {
        return sprite2Bounds.width();
    }
    synchronized public int getSprite2Height() {
        return sprite2Bounds.height();
    }
    
    public GameBoard(Context context, AttributeSet aSet) {
        super(context, aSet);
        //it's best not to create any new objects in the on draw
        //initialize them as class variables here
        p = new Paint();
        
        sprite1 = new Point(-1,-1);
        sprite2 = new Point(-1,-1);
        m = new Matrix();
        p = new Paint();
        bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid);
        bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.ufo);

        sprite1Bounds = new Rect(0,0, bm1.getWidth(), bm1.getHeight());
        sprite2Bounds = new Rect(0,0, bm2.getWidth(), bm2.getHeight());
        
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
        
        if (sprite1.x>=0) {
            m.reset();
            m.postTranslate((float)(sprite1.x), (float)(sprite1.y));
            m.postRotate(sprite1Rotation,
                   (float)(sprite1.x+sprite1Bounds.width()/2.0),
                   (float)(sprite1.y+sprite1Bounds.width()/2.0));
            canvas.drawBitmap(bm1, m, null);
            sprite1Rotation+=5;
            if (sprite1Rotation >= 360) sprite1Rotation=0;
        }

        if (sprite2.x>=0) {
            canvas.drawBitmap(bm2, sprite2.x, sprite2.y, null);
        }
    }
    
}
