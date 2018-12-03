package model;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;


public class Mainmenu extends GraphicalObject {

    //Attribute
    private boolean tot;

    //Referenzen

    //Konstruktor: Diese Methode wird bei Erzeugung eines Hauses aufgerufen
    public Mainmenu(){

    }


    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(200,200,200,100);
        drawTool.drawFilledRectangle(300,300,200,75);
        drawTool.setFont("Arial",30,true);
        drawTool.setCurrentColor(255,0,0,255);
        drawTool.drawText(350,350,"START  oder so");
        if(tot){
            drawTool.setCurrentColor(150,200,150,225);
            drawTool.drawFilledCircle(300,100,75);
            drawTool.drawFilledRectangle(325,165,25,30);
            drawTool.setCurrentColor(175,225,175,255);
            drawTool.drawFilledRectangle(330,170,3,10);
            drawTool.drawFilledRectangle(335,170,3,10);
            drawTool.drawFilledRectangle(340,170,3,10);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(e.getX()>300 && e.getX() < 300 + 200 && e.getY() > 300 && e.getY()< 300+75){
            System.out.println("hello");
            if(!tot){
                tot = true;
            }
        }
    }
}
