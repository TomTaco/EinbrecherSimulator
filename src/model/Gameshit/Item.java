package model.Gameshit;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Item extends GraphicalObject {
    //Attribute
    private int x,y;
    private int price;
    private String name;

    public Item(int x, int y, int price, String name){
        this.x = x;
        this.y = y;
        this.price = price;
        this.name = name;
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("images/"+name+".png"),x,y);
    }
}