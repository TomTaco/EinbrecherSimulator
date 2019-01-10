package model.Gameshit;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class House extends GraphicalObject {
    //Attribute
    private int x, y;
    private int hID;
    private boolean hovering;
    private double pWidth, pHeight, pX;


    public House(int x, int y, int hID){
        this.x = x;
        this.y = y;
        this.hID = hID;
        pHeight = 0;
        pWidth = 0;
        pX = x + 75;
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("images/house" + hID + ".png"), x, y, 150, 150);
        drawTool.drawImage(createNewImage("images/namesign.png"),x+25,y+145,100,37);
        if(hovering){
            drawTool.drawImage(createNewImage("images/popup.png"),pX,y+150,(int)pWidth,(int)pHeight);
        }
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if(pHeight < 200){
            pHeight += 650*dt;
        }else{
            pHeight = 200;
        }
        if(pWidth < 150){
            pWidth += 600*dt;
        }else{
            pWidth = 150;
        }
        if(pX > x){
            pX -= 300*dt;
        }else{
            pX = x;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        if(e.getX() > x && e.getX() < x+150 && e.getY() > y && e.getY()< y+150) {
            hovering = true;
        }else if(hovering && e.getX() > x && e.getX() < x+150 && e.getY() > y && e.getY() < y+350){
        }else{
            hovering = false;
            pHeight = 0;
            pWidth = 0;
            pX = x + 75;
        }
    }
}
