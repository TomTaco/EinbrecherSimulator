package model.Gameshit;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.image.BufferedImage;

public class Background extends GraphicalObject {

    //Referenzen
    private int disID;

    public Background(int disID){
        this.disID = disID;
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("images/district"+disID+"_blur"+".png"),0,0,800,800);
    }

    public void changeBackground(int num){
        this.disID = num;
    }
}