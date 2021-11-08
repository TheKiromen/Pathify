package com.dkrucze.PathifyApp;

import java.awt.*;
import java.util.LinkedList;

public class Fourier {

    public static LinkedList<Complex> DFT(LinkedList<Point> input){
        LinkedList<Complex> result = new LinkedList<>();
        int N = input.size();
        Complex tmp;

        Complex sum = new Complex(0,0);
        for(int k=0;k<N;k++){
            for(int n=0;n<N;n++){
                double phi = (Math.PI * 2.0 * k * n) / N;
                Complex c = new Complex(Math.cos(phi),-Math.sin(phi));
                tmp=new Complex(input.get(n).x,input.get(n).y);
                sum=sum.add(tmp.multiply(c));
            }
            sum=sum.divide(N);
            double freq = k;
            double amp = sum.magnitude();
            double phase = sum.direction();
            result.add(new Complex(sum.re,sum.im,freq,amp,phase));
        }

        return result;
    }
}
