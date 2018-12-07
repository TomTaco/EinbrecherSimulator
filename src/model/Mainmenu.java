package model;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;


public class Mainmenu extends GraphicalObject {

    //Attribute

    //Referenzen
    private ProgramController pc;

    public Mainmenu(ProgramController pc){
        this.pc = pc;
    }


    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(200,200,200,100);
        drawTool.drawFilledRectangle(300,300,200,75);
        drawTool.setFont("Arial",20,true);
        drawTool.setCurrentColor(255,0,0,255);
        drawTool.drawText(350,350,"START  oder so");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(e.getX()>300 && e.getX() < 300 + 200 && e.getY() > 300 && e.getY()< 300+75){
            pc.startGame();
        }
    }
}
