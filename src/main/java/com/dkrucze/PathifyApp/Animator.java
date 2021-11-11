package com.dkrucze.PathifyApp;

import com.dkrucze.PathifyCore.PathifiedImage;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


import java.awt.Point;
import java.util.LinkedList;

public class Animator {

    private GraphicsContext gc;
    private LinkedList<Point> path;
    private AnimationTimer animation;
    private double width,height,scalingFactor;
    int n;

    Animator(Canvas c, PathifiedImage result){
        gc=c.getGraphicsContext2D();
        width=c.getWidth();
        height=c.getHeight();
        scalingFactor= result.getImageHeight() > result.getImageWidth() ? result.getImageHeight() : result.getImageWidth();
        int n=1;

        //Scale the path
        path = new LinkedList<>();
        double x,y;
        LinkedList<Point> tmp = result.getPath();
        int step = (int)Math.ceil(tmp.size()/5000.0);
        for(int i=0;i<tmp.size();i+=step){
            x=tmp.get(i).x*width/scalingFactor;
            y=tmp.get(i).y*height/scalingFactor;
            path.add(new Point((int)x,(int)y));
        }
    }

    public void animate(){
        animation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateFrame();
            }
        };
        animation.start();
    }

    private void updateFrame() {
        gc.clearRect(0,0,width,height);
        gc.setLineWidth(2);
        for(int i=1;i<n;i++){
            Point p1=path.get(i-1);
            Point p2=path.get(i);
            gc.strokeLine(p1.x,p1.y,p2.x,p2.y);
        }
        if(n == path.size()){
            animation.stop();
        }else if(n+5 > path.size()){
            n= path.size();
        }else{
            n+=5;
        }
    }

    public void terminate() {
        animation.stop();
    }
}
