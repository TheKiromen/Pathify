package com.dkrucze.PathifyApp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Point;
import java.util.LinkedList;

public class Animator {

    private GraphicsContext gc;
    private LinkedList<Point> path;
    private double width,height;

    Animator(Canvas c, LinkedList<Point> path){
        gc=c.getGraphicsContext2D();
        width=c.getWidth();
        height=c.getHeight();
        this.path=path;
    }

    public void animate(){
        gc.clearRect(0,0,width,height);
        gc.beginPath();
        for(Point p : path){
            gc.lineTo(p.x,p.y);
        }
        gc.closePath();
        gc.stroke();
        //TODO
        // Scale the path
        // Implement DFT
        // Display scaled path after transform
    }

}
