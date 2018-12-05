package model;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Overlay extends GraphicalObject {

    public Overlay(){

    }

    public void draw(DrawTool drawTool){
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
}
