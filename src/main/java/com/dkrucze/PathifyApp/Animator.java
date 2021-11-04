package com.dkrucze.PathifyApp;

import com.dkrucze.PathifyCore.PathifiedImage;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


import java.awt.Point;
import java.util.LinkedList;

public class Animator {

    private GraphicsContext gc;
    private LinkedList<Point> path;
    private AnimationTimer animation;
    private double width,height,scalingFactor,time=0;
    int n=1;

    Animator(Canvas c, PathifiedImage result){
        gc=c.getGraphicsContext2D();
        width=c.getWidth();
        height=c.getHeight();
        scalingFactor= result.getImageHeight() > result.getImageWidth() ? result.getImageHeight() : result.getImageWidth();
        //Scale the path
        path = new LinkedList<>();
        double x,y;
        //FIXME Figure out how many points in path to skip to get smooth animation
        // Math.ceil(scalingFactor/720)?
        // some function based on result.getPath.size()?
        for(Point p : result.getPath()){
            x=p.x*width/scalingFactor;
            y=p.y*height/scalingFactor;
            path.add(new Point((int)x,(int)y));
        }
    }

    public void animate(){
        animation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //updateFrame();
                test();
            }
        };
        animation.start();
        //TODO
        // Implement DFT
        // Display scaled path after transform
    }

    private void updateFrame() {
        gc.clearRect(0,0,width,height);
        double x=360,y=360;
        for(int i=0; i<10;i++){
            double prevx=x,prevy=y;
            int n=i*2+1;
            double radius = 100.0*(4/(n*Math.PI));
            x+=radius*Math.cos(n*time);
            y+=radius*Math.sin(n*time);

            //gc.strokeOval(prevx-radius,prevy-radius,radius*2,radius*2);
            gc.strokeLine(prevx,prevy,x,y);
        }
        time+=0.02;
    }

    private void test(){
        gc.clearRect(0,0,width,height);
        gc.beginPath();
        for(int i=0;i<n;i++){
            gc.lineTo(path.get(i).x,path.get(i).y);
        }
        if(n+5>path.size()){
            animation.stop();
        }else{
            n+=5;
        }
        gc.closePath();
        gc.stroke();
    }

    public void terminate() {
        animation.stop();
    }
}
