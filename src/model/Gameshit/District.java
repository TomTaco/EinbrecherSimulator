package model.Gameshit;

import control.framework.UIController;
import model.Overlay;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import javax.swing.plaf.nimbus.State;
import java.awt.event.MouseEvent;
import java.sql.Statement;

public class District extends GraphicalObject {

    //Attribute
    private String disName;
    private int disID;
    private double delay;

    //Referenzen
    private UIController ui;
    private House[] houses = new House[3];
    private Statement stmt;
    private Player player;

    public District(String disName, int disID, UIController ui, Statement stmt, Player player){
        this.disName = disName;
        this.disID = disID;
        this.ui = ui;
        this.stmt = stmt;
        this.player = player;
        delay = 0.01;

    }

    private void setHouses() {
        houses[0] = new House(50, 200, 3*disID-2, stmt,  player);
        houses[1] = new House(225, 200,3*disID-1, stmt, player);
        houses[2] = new House(400,200,3*disID, stmt,player);
        ui.drawObject(houses[0]);
        ui.drawObject(houses[1]);
        ui.drawObject(houses[2]);
    }

    public void removeHouses(){
        for (int i = 0; i < 3; i++) {
            ui.removeObject(houses[i]);
            houses[i] = null;
        }
    }


    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);

        drawTool.drawImage(createNewImage("images/district_sign.png"),250,50,300,50);
        drawTool.drawImage(createNewImage("images/arrow.png"),210,52,40,40);
        drawTool.drawImage(createNewImage("images/arrow.png"),590,52,-40,40);
        drawTool.drawImage(createNewImage("images/board.png"),37,195,526,160);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.setFont("Arial",35,true);
        drawTool.drawText(265,90,disName);

    }

    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if(delay > 0){
            delay-= 1;
        }else if(delay < 0){
            delay = 0;
            setHouses();
        }
    }
}
