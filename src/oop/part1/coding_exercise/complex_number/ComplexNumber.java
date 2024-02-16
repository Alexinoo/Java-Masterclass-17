package oop.part1.coding_exercise.complex_number;

public class ComplexNumber {

    private double real;

    private double imaginary;

    public ComplexNumber(double real,double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal(){
        return real;
    }

    public double getImaginary(){
        return imaginary;
    }

    public void add(double x , double y){
       this.real += x;
       this.imaginary += y;
    }

    public void add(ComplexNumber complex_number){
      add(complex_number.real , complex_number.imaginary);
    }

    public void subtract(double x , double y){
        this.real -= x;
        this.imaginary -= y;
    }

    public void subtract(ComplexNumber complex_number){
      subtract(complex_number.real,complex_number.imaginary);
    }
}
