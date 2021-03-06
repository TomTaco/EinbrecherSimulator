package model;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;


public class Mainmenu extends GraphicalObject {

    //Attribute
    private boolean clicked;

    //Referenzen
    private ProgramController pc;

    public Mainmenu(ProgramController pc){
        this.pc = pc;
        clicked = false;
    }


    public void draw(DrawTool drawTool) {
        drawTool.drawImage(createNewImage("images/main.png"),0,0);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(!clicked) {
            if (e.getX() > 360 && e.getX() < 360 + 110 && e.getY() > 400 && e.getY() < 400 + 170) {
                pc.startGame();
            }
        }
        clicked = !clicked;

    }
}
