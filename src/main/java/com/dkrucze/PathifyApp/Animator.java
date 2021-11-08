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
    private LinkedList<Point> path,shape;
    private LinkedList<Complex> fourier;
    private AnimationTimer animation;
    private double width,height,scalingFactor,time,dt;
    int n;

    Animator(Canvas c, PathifiedImage result){
        gc=c.getGraphicsContext2D();
        width=c.getWidth();
        height=c.getHeight();
        scalingFactor= result.getImageHeight() > result.getImageWidth() ? result.getImageHeight() : result.getImageWidth();

        //Scale the path
        path = new LinkedList<>();
        shape = new LinkedList<>();
        double x,y;
        LinkedList<Point> tmp = result.getPath();
        int step = (int)Math.ceil(tmp.size()/5000.0);
        for(int i=0;i<tmp.size();i+=step){
            x=tmp.get(i).x*width/scalingFactor;
            y=tmp.get(i).y*height/scalingFactor;
            path.add(new Point((int)x,(int)y));
        }

        fourier=Fourier.DFT(path);
        //Variables for animation
        n=1;
        time=0;
        dt=Math.PI*2/fourier.size();
    }

    public void animate(){
        animation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateFrame();
                //test();
            }
        };
        animation.start();
    }

    private void updateFrame() {
        gc.clearRect(0,0,width,height);
        double x=0,y=0;
        //FIXME REFACTOR THIS
        for(Complex c : fourier){
            double prevx=x,prevy=y;
            double theta = c.freq*time+c.phase;
            x+=c.amp*Math.cos(theta);
            y+=c.amp*Math.sin(theta);
            gc.setStroke(Color.LIGHTGREY);
            gc.strokeOval(prevx-c.amp,prevy-c.amp,c.amp*2,c.amp*2);
            gc.setStroke(Color.GREY);
            gc.strokeLine(prevx,prevy,x,y);
        }
        shape.add(new Point((int)x,(int)y));
        gc.setStroke(Color.RED);
        gc.beginPath();
        for(Point p : shape){
            gc.lineTo(p.x,p.y);
        }
        gc.closePath();
        gc.stroke();

//        for(int i=0; i<10;i++){
//            double prevx=x,prevy=y;
//            int n=i*2+1;
//            double radius = 100.0*(4/(n*Math.PI));
//            x+=radius*Math.cos(n*time);
//            y+=radius*Math.sin(n*time);
//
//            //gc.strokeOval(prevx-radius,prevy-radius,radius*2,radius*2);
//            gc.strokeLine(prevx,prevy,x,y);
//        }
        time+=dt;
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
