package model.Gameshit;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class House extends GraphicalObject {
    //Attribute
    private int x, y;
    private int hID;
    private boolean hovering;
    private double pWidth, pHeight, pX;
    private String familyName;

    //Referenzen
    private Statement stmt;


    public House(int x, int y, int hID, Statement stmt){
        this.x = x;
        this.y = y;
        this.hID = hID;
        this.stmt = stmt;
        pHeight = 0;
        pWidth = 0;
        pX = x + 75;
        familyName = "";
        setFamilyName();
    }

    public void setFamilyName(){
        try {
        ResultSet results = stmt.executeQuery("" +
                "SELECT DD_Resident.lastName, DD_House.houseID " +
                "FROM DD_Resident " +
                "INNER JOIN DD_House ON DD_Resident.residentID = DD_House.tenantID " +
                ";");
        while(results.next()){
            if(Integer.parseInt(results.getString(2)) == hID){
                familyName = results.getString(1);
            }
        }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("images/house" + hID + ".png"), x, y, 150, 150);
        if(hovering){
            drawTool.drawImage(createNewImage("images/popup.png"),pX,y+150,(int)pWidth,(int)pHeight);
        }
        drawTool.drawImage(createNewImage("images/namesign.png"),x+25,y+135,100,37);
        drawTool.setFont("Times New Roman",12,true);
        drawTool.drawText(x+62,y+157,familyName);
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
