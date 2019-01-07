package model.Gameshit;

import control.framework.UIController;
import model.Overlay;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class District extends GraphicalObject {

    //Attribute
    private String disName;
    private int disID;

    //Referenzen
    private UIController ui;
    private Overlay over;
    private House[] houses = new House[3];

    public District(String disName, int disID, UIController ui, Overlay over){
        this.disName = disName;
        this.disID = disID;
        this.ui = ui;
        this.over = over;
        setBackground();
        houses[0] = new House("d1h1",50, 200);
        houses[1] = new House("d1h2",225, 200);
        houses[2] = new House("d1h3",400,200);
        ui.drawObject(houses[0]);
        ui.drawObject(houses[1]);
        ui.drawObject(houses[2]);

    }

    private void setBackground() {
        Background background = new Background(createNewImage("images/white.png"));
        switch (disID){
            case 1:
                background = new Background(createNewImage("images/district1.png"));
                break;
            case 2:
                background = new Background(createNewImage("images/district2.png"));
                break;
            case 3:
                //background = new Background(createNewImage("images/district3.png"));
                break;
            case 4:
                //background = new Background(createNewImage("images/district2.png"));
                break;

        }
        ui.drawObject(background);
    }


    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);

        drawTool.drawImage(createNewImage("images/district_sign.png"),250,50,300,50);
        drawTool.drawImage(createNewImage("images/arrow.png"),210,52,40,40);
        drawTool.drawImage(createNewImage("images/arrow.png"),590,52,-40,40);
        drawTool.setFont("Arial",35,true);
        drawTool.drawText(265,90,disName);

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
