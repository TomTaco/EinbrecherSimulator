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

    public Overlay(UIController ui){
        this.ui = ui;
        countYourAssToTheMoon = 0.01;
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
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if(countYourAssToTheMoon >0) countYourAssToTheMoon -=dt;
        else buildDistrict();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }
}
