package com.dkrucze.PathifyApp;

public class Complex {
    double re;
    double im;
    double freq;
    double amp;
    double phase;

    Complex(double r, double i){
        re = r;
        im = i;
    }

    Complex(double r, double i, double f, double a, double p){
        re = r;
        im = i;
        freq = f;
        amp = a;
        phase = p;
    }

    Complex add(Complex other){
        return new Complex(re + other.re, im + other.im);
    }

    Complex subtract(Complex other){
        return new Complex(re - other.re, im - other.im);
    }

    Complex multiply(Complex other){
        double rea = re * other.re - im * other.im;
        double ima = re * other.im + im * other.re;
        return new Complex(rea, ima);
    }

    Complex divide(double divisor){
        double r = re / divisor;
        double i = im / divisor;
        return new Complex(r, i);
    }

    double magnitude()
    {
        return Math.sqrt(re * re + im * im);
    }

    double direction()
    {
        return Math.atan2(im, re);
    }

}
