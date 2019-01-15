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
    private boolean hovering, hoverCrow;
    private double pWidth, pHeight, pX;
    private String familyName;
    private String securityLvl;
    private String [] residentData;
    private boolean clicked;
    private boolean breakingIn;
    private int stealingTime;


    //Referenzen
    private Statement stmt;
    private Player player;

    public House(int x, int y, int hID, Statement stmt, Player player){
        this.x = x;
        this.y = y;
        this.hID = hID;
        this.stmt = stmt;
        this.player = player;
        pHeight = 0;
        pWidth = 0;
        pX = x + 75;
        familyName = "";
        securityLvl = "";
        residentData = new String[0];
        stealingTime = 1;
        setHouseData();
    }

    public void setHouseData(){
        try {
        ResultSet results = stmt.executeQuery("" +
                "SELECT DD_Resident.lastName, DD_House.houseID, DD_House.security " +
                "FROM DD_Resident " +
                "INNER JOIN DD_House ON DD_Resident.residentID = DD_House.tenantID " +
                "WHERE DD_House.houseID = "+hID +
                ";");
        while(results.next()){
            familyName = results.getString(1);
            securityLvl = "Security: "+results.getString(3);
        }

        results = stmt.executeQuery("" +
                "SELECT firstName, lastName, comeHome, goesAway, houseID " +
                "FROM DD_Resident " +
                "WHERE houseID = "+hID +
                "");
        while(results.next()) {
            String[] split = results.getString("goesAway").split(":");
            String goesAway = split[0] + ":" + split[1];
            split = results.getString("comeHome").split(":");
            String comeHome = split[0] + ":" + split[1];
            String[] tempArray = new String[residentData.length + 1];
            for (int i = 0; i < residentData.length; i++) {
                tempArray[i] = residentData[i];
            }
            tempArray[tempArray.length - 1] = "" + results.getString("firstName") + " " + results.getString("lastName") + " " + goesAway + "-" + comeHome;
            residentData = tempArray;

        }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.drawImage(createNewImage("images/house" + hID + ".png"), x, y, 150, 150);
        if(hovering){
            drawTool.drawImage(createNewImage("images/popup.png"),pX,y+150,(int)pWidth,(int)pHeight);
            if(hoverCrow)drawTool.drawImage(createNewImage("images/breakin-but.png"),pX+6,y+294,(int)pWidth-10,(int)pHeight-144);
            else drawTool.drawImage(createNewImage("images/breakin-but.png"),pX+12,y+300,(int)pWidth-22,(int)pHeight-156);
            drawTool.setFont("Arial",(int)pWidth-136,true);
            drawTool.drawText(x+15,y+190,securityLvl);
            drawTool.drawText(x+15,y+210,"Anwohner:");
            for (int i = 0; i < residentData.length; i++) {
                drawTool.drawImage(createNewImage("images/woodpile.png"),x-5,y+217+i*20,164+residentData[i].length()*3,14);
                drawTool.drawText(x+15,y+230+i*20,residentData[i]);
            }
        }
        drawTool.drawImage(createNewImage("images/namesign.png"),x+25,y+135,100,37);
        drawTool.setFont("Times New Roman",12,true);
        drawTool.drawText(x+62,y+157,familyName);
        if(breakingIn){
            drawTool.drawImage(createNewImage("images/timeselect.png"),150,400);
            if(stealingTime < 10)drawTool.setFont("Impact",50,true);
            else drawTool.setFont("Impact",45,false);
            drawTool.setCurrentColor(0,0,0,255);
            drawTool.drawText(375,580, ""+stealingTime+"h");
            drawTool.drawRectangle(350,597,98,31);
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
            if(e.getX()> x+12 && e.getX() < x+132 && e.getY() > y+310 && e.getY() < y+350){
                hoverCrow = true;
            }else{
                hoverCrow = false;
            }
        }else{
            hovering = false;
            pHeight = 0;
            pWidth = 0;
            pX = x + 75;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(!clicked) {
            if (hovering && e.getX() > x + 6 && e.getX() < x + 126 && e.getY() > y + 308 && e.getY() < y + 344) {

                breakingIn = true;
            }
            if(breakingIn){
                if(stealingTime > 1 && e.getX() >265 && e.getX() < 328 && e.getY() > 530 && e.getY() < 585){
                    stealingTime -= 1;
                }else if(stealingTime < 10 && e.getX() >470 && e.getX() < 533 && e.getY() > 530 && e.getY() < 585){
                    stealingTime += 1;
                }else if(e.getX() >605 && e.getX() < 636 && e.getY() > 413 && e.getY() < 444){
                    breakingIn = false;
                    stealingTime = 1;
                }else if(e.getX() >350 && e.getX() < 448 && e.getY() > 597 && e.getY() < 628){
                    breakingIn = false;
                    player.breakIn(hID, stealingTime);
                }
            }
        }
        clicked = !clicked;
    }
}
