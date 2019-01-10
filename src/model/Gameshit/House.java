package model.Gameshit;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class House extends GraphicalObject {

    private int x, y;
    private int hID;


    public House(int x, int y, int hID){
        this.x = x;
        this.y = y;
        this.hID = hID;
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("images/house" + hID + ".png"), x, y, 150, 150);
        drawTool.drawImage(createNewImage("images/namesign.png"),x+25,y+145,100,37);

    }

    @Override
    public void update(double dt) {
        super.update(dt);
    }
}
