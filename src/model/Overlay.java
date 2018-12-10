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
    private double countYourAssToTheMoon;
    private int hours;
    private int minutes;
    private double seconds;
    private String time;

    public Overlay(UIController ui){
        this.ui = ui;
        countYourAssToTheMoon = 0.01;
        hours = 8;
        minutes = 0;
        seconds = 0;
        time = "0"+hours+":"+"0"+minutes;
    }

    private void buildDistrict() {
        test = new District("Ghetto am Block",ui);
        ui.drawObject(test);

    }

    public void draw(DrawTool drawTool){

        //Rechteck
        drawTool.setCurrentColor(194,194,194,255);
        drawTool.drawFilledRectangle(250,50,300,50);
        drawTool.setCurrentColor(230,230,230,255);
        drawTool.drawFilledRectangle(255,55,290,40);

        drawTool.setCurrentColor(200,5,12,64);
        drawTool.drawFilledRectangle(210,68,20,12);
        drawTool.drawFilledRectangle(570,68,20,12);

        drawTool.setCurrentColor(40,40,150,100);
        drawTool.drawFilledRectangle(50,200,150,150);
        drawTool.drawFilledRectangle(225,200,150,150);
        drawTool.drawFilledRectangle(400,200,150,150);

        drawTool.setFont("Arial",25,false);
        drawTool.drawText(700,50,time);
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if(countYourAssToTheMoon >0){
            countYourAssToTheMoon -=dt;
        }else{
            buildDistrict();
        }
        if(seconds < 60){
            seconds += 60*dt;
        }else{
            seconds = 0;
            if((minutes + 5) < 60) {
                minutes += 5;
            }else{
            minutes = 0;
                if(hours < 24) {
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

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }
}
