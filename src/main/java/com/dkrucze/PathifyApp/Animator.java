package com.dkrucze.PathifyApp;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


import java.awt.Point;
import java.util.LinkedList;

public class Animator {

    private GraphicsContext gc;
    private LinkedList<Point> path;
    private AnimationTimer animation;
    private double width,height;

    int n=1;
    Animator(Canvas c, LinkedList<Point> path){
        gc=c.getGraphicsContext2D();
        width=c.getWidth();
        height=c.getHeight();
        this.path=path;
    }

    public void animate(){
        animation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                test();
            }
        };
        animation.start();
        //TODO
        // Scale the path
        // Implement DFT
        // Display scaled path after transform
    }

    private void updateFrame() {
        gc.clearRect(0,0,width,height);
        gc.beginPath();
        for(int i=0;i<3;i++){
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

    double time=0;
    private void test(){
        gc.clearRect(0,0,width,height);
        double x=300,y=300;
        for(int i=0; i<5;i++){
            double prevx=x,prevy=y;
            int n=i*2+1;
            double radius = 100.0*(4/(n*Math.PI));
            x+=radius*Math.cos(n*time);
            y+=radius*Math.sin(n*time);

            //gc.strokeOval(prevx-radius,prevy-radius,radius*2,radius*2);
            gc.strokeLine(prevx,prevy,x,y);
        }
        time+=0.01;
    }

    public void terminate() {
        animation.stop();
    }
}
