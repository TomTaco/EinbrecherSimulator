package model;


import control.framework.UIController;
import model.Gameshit.Background;
import model.Gameshit.District;
import model.Gameshit.Player;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static javax.swing.text.StyleConstants.setBackground;

public class Overlay extends GraphicalObject {


    //Referenzen
    private District dis;
    private UIController ui;
    private Statement stmt;
    private Background back;
    private Player player;

    //Attribute
    private int hours;
    private int minutes;
    private double seconds;
    private String time;
    private int currentDistrict;
    private boolean clicked;

    public Overlay(UIController ui, Statement stmt, Player player){
        this.ui = ui;
        this.stmt = stmt;
        this.player = player;
        hours = 8;
        minutes = 0;
        seconds = 0;
        time = "0"+hours+":"+"0"+minutes;
        currentDistrict = 1;
        clicked = false;
        buildGame();
    }




    public void draw(DrawTool drawTool){
        drawTool.drawImage(createNewImage("images/timesign.png"),625,20,150,40);
        drawTool.setCurrentColor(230,230,230,255);
        drawTool.setFont("Arial",25,false);
        drawTool.drawText(700,50,time);

    }

    public void buildGame(){
        try {
            back = new Background(1);
            ui.drawObject(back);
            ResultSet results = stmt.executeQuery("SELECT * FROM DD_District WHERE districtID = 1;");
            results.next();
            dis = new District(results.getString("name"),Integer.parseInt(results.getString("districtID")),ui,stmt, player);
            ui.drawObject(dis);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if(seconds < 60){
            seconds += 60*dt;
        }else{
            seconds = 0;
            if((minutes + 5) < 60) {
                minutes += 5;
            }else{
            minutes = 0;
                if(hours < 23) {
                  hours += 1;
                }else{
                    hours = 0;
                }
            }
        }


        if(hours < 10 && minutes < 10) {
            time = "0" + hours + ":" + "0" + minutes;
        }else if(hours < 10){
            time = "0" + hours + ":" + minutes;
        }else if(minutes < 10){
            time = hours + ":" + "0" + minutes;
        }else{
            time =  hours + ":" +  minutes;
        }



    }

    public void nextDistrict(int num){
        dis.removeHouses();
        ui.removeObject(dis);
        dis = null;
        currentDistrict += num;
        if(currentDistrict < 1) currentDistrict = 4;
        else if(currentDistrict > 4) currentDistrict = 1;
        ResultSet results = null;
        try {
            switch(currentDistrict){
                case 1:
                    results = stmt.executeQuery("SELECT * FROM DD_District WHERE districtID = 1;");
                    back.changeBackground(1);
                    break;
                case 2:
                    results = stmt.executeQuery("SELECT * FROM DD_District WHERE districtID = 2;");
                    back.changeBackground(2);
                    break;
                case 3:
                    results = stmt.executeQuery("SELECT * FROM DD_District WHERE districtID = 3;");
                    back.changeBackground(3);
                    break;
                case 4:
                    results = stmt.executeQuery("SELECT * FROM DD_District WHERE districtID = 4;");
                    back.changeBackground(4);
                    break;
            }
            results.next();
            dis = new District(results.getString("name"),Integer.parseInt(results.getString("districtID")),ui, stmt, player);
            ui.drawObject(dis);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(!clicked) {
            if (e.getX() > 210 && e.getX() < 250 && e.getY() > 52 && e.getY() < 92) {
                nextDistrict(-1);
            } else if (e.getX() > 550 && e.getX() < 590 && e.getY() > 52 && e.getY() < 92) {
                nextDistrict(1);
            }
        }
        clicked = !clicked;
    }

    public int getHours(){
        return hours;
    }
    public int getMinutes(){
        return minutes;
    }
}
