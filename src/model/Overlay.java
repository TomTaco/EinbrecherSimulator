package model;

import control.framework.UIController;
import model.Gameshit.District;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class Overlay extends GraphicalObject {


    //Referenzen
    private District test;
    private UIController ui;

    //Attribute
    private int hours;
    private int minutes;
    private double seconds;
    private String time;

    public Overlay(UIController ui){
        this.ui = ui;
        hours = 8;
        minutes = 0;
        seconds = 0;
        time = "0"+hours+":"+"0"+minutes;
        test = new District("Ghetto am Block",ui, this);
        ui.drawObject(test);
    }


    public void draw(DrawTool drawTool){

        //Rechteck

        drawTool.setCurrentColor(230,230,230,255);
        drawTool.setFont("Arial",25,false);
        drawTool.drawText(700,50,time);
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if(seconds < 60){
            seconds += 10000*dt;
        }else{
            seconds = 0;
            if((minutes + 5) < 60) {
                minutes += 5;
            }else{
            minutes = 0;
                if(hours < 23) {
                  hours += 1;
                }else{
                    hours = 0;
                }
            }
        }


        if(hours < 10 && minutes < 10) {
            time = "0" + hours + ":" + "0" + minutes;
        }else if(hours < 10){
            time = "0" + hours + ":" + minutes;
        }else if(minutes < 10){
            time = hours + ":" + "0" + minutes;
        }else{
            time =  hours + ":" +  minutes;
        }


    }

    public void nextDistrict(int num){

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }
}
