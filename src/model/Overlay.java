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
    private double hours;
    private double minutes;
    private String time;

    public Overlay(UIController ui){
        this.ui = ui;
        countYourAssToTheMoon = 0.01;
        hours = 8;
        minutes = 0;
        time = "0"+(int)hours+":"+"0"+(int)minutes;
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


        if(minutes < 60){
            minutes += dt;
        }else{
            minutes = 0;
            if(hours < 24) {
                hours += 1;
            }else{
                hours = 0;
            }
        }
        if(hours < 10 && minutes < 10) {
            time = "0" + (int)hours + ":" + "0" + (int)minutes;
        }else if(hours < 10){
            time = "0" + (int)hours + ":" +  (int)minutes;
        }else if(minutes < 10){
            time = (int)hours + ":" + "0" + (int)minutes;
        }else{
            time =  (int)hours + ":" +  (int)minutes;
        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }
}
