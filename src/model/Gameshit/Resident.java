package model.Gameshit;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Resident extends GraphicalObject {

    //Referenzen

    //Attribute
    private int x,y;
    private String name;
    private int comesHome;
    private int goesAway;

    public Resident(int x, int y, String name, int comesHome, int goesAway){
        this.x = x;
        this.y = y;
        this.name = name;
        this.comesHome = comesHome;
        this.goesAway = goesAway;
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("images/woodpile.png"),x-5,y+217,name.length()*3,14);
        drawTool.drawText(x,y+230,name);
    }

    public int getComesHomes(){
        return comesHome;
    }

    public int getGoesAway(){
        return goesAway;
    }
}