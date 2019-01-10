package model.Gameshit;

import com.mysql.cj.util.StringUtils;
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
        securityLvl = "";
        residentData = new String[0];
        setHouseData();
    }

    public void setHouseData(){
        try {
        ResultSet results = stmt.executeQuery("" +
                "SELECT DD_Resident.lastName, DD_House.houseID, DD_House.security " +
                "FROM DD_Resident " +
                "INNER JOIN DD_House ON DD_Resident.residentID = DD_House.tenantID " +
                ";");
        while(results.next()){
            if(Integer.parseInt(results.getString(2)) == hID){
                familyName = results.getString(1);
                securityLvl = "Security: "+results.getString(3);
            }
        }

        results = stmt.executeQuery("" +
                "SELECT firstName, lastName, comeHome, goesAway, houseID " +
                "FROM DD_Resident" +
                "");
        while(results.next()){
            if(Integer.parseInt(results.getString("houseID")) == hID){
                String[] split = results.getString("goesAway").split(":");
                String goesAway = split[0]+":"+split[1];
                split = results.getString("comeHome").split(":");
                String comeHome = split[0]+":"+split[1];
                String[] tempArray = new String[residentData.length+1];
                for (int i = 0; i < residentData.length; i++) {
                    tempArray[i] = residentData[i];
                }
                tempArray[tempArray.length-1] = ""+results.getString("firstName")+" "+results.getString("lastName")+" "+goesAway+"-"+comeHome;
                residentData = tempArray;
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
            if(hoverCrow)drawTool.drawImage(createNewImage("images/breakin-but.png"),pX-6,y+294,(int)pWidth-10,(int)pHeight-144);
            else drawTool.drawImage(createNewImage("images/breakin-but.png"),pX,y+300,(int)pWidth-22,(int)pHeight-156);
            drawTool.setFont("Arial",(int)pWidth-136,true);
            drawTool.drawText(x+15,y+190,securityLvl);
            drawTool.drawText(x+15,y+210,"Anwohner:");
            //
            for (int i = 0; i < residentData.length; i++) {
                drawTool.drawImage(createNewImage("images/woodpile.png"),x-5,y+217+i*20,164+residentData[i].length()*3,14);
                drawTool.drawText(x+15,y+230+i*20,residentData[i]);
            }
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
            if(e.getX()> x && e.getX() < x+120 && e.getY() > y+310 && e.getY() < y+350){
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
}
