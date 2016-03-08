/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automat;

/**
 *
 * @author Family
 */
public class BilletTyper {
    private String type;
    private double pris; 
    private int antalS;
    
    BilletTyper(String s, double p){
        type = s;
        pris = p;
        antalS = 0;
    }
    public String getString(){
        return type;
    }
    public double getDouble(){
        return pris;
    }
    public int getAntalS(){
        return antalS;
    }
    
    public void addAntal(int i){
        antalS = i;
    }
}
