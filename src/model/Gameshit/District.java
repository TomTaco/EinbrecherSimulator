package model.Gameshit;

import control.framework.UIController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class District extends GraphicalObject {

    //Attribute
    private String disName;

    //Referenzen
    private UIController ui;
    private House[] houses = new House[3];

    public District(String disName,UIController ui){
        this.disName = disName;
        this.ui = ui;
        houses[0] = new House("House1",50, 200);
        houses[1] = new House("House2",225, 200);
        houses[2] = new House("House3",400,200);
        ui.drawObject(houses[0]);
        ui.drawObject(houses[1]);
        ui.drawObject(houses[2]);
    }


    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.setFont("Arial",35,true);
        drawTool.drawText(270,90,disName);
    }
}
