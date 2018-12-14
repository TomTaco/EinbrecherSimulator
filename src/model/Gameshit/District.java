package model.Gameshit;

import control.framework.UIController;
import model.Overlay;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class District extends GraphicalObject {

    //Attribute
    private String disName;

    //Referenzen
    private UIController ui;
    private Overlay over;
    private House[] houses = new House[3];

    public District(String disName,UIController ui, Overlay over){
        this.disName = disName;
        this.ui = ui;
        this.over = over;
        Background back = new Background();
        ui.drawObject(back);
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


        drawTool.drawImage(createNewImage("images/district_sign.png"),250,50,300,50);
        drawTool.setFont("Arial",35,true);
        drawTool.drawText(265,90,disName);

        drawTool.drawImage(createNewImage("images/arrow.png"),210,52,40,40);
        drawTool.drawImage(createNewImage("images/arrow.png"),590,52,-40,40);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(e.getX() > 210 && e.getX() < 250 && e.getY() > 52 && e.getY() < 92){
            over.nextDistrict(-1);
        }else if(e.getX() > 550 && e.getX() < 590 && e.getY() > 52 && e.getY() < 92){
            over.nextDistrict(1);
        }

    }
}
