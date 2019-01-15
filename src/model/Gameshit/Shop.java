package model.Gameshit;

import control.framework.UIController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Shop extends GraphicalObject {

    private int x,y,sy;
    private boolean clicked;
    private boolean open;

    private Connection con;
    private Tool[] tools;
    private UIController ui;
    private Player player;
    private double delay = 0.01;

    public Shop(Connection con, UIController ui, Player player){
        this.con = con;
        this.ui = ui;
        this.player = player;
        x = 10;
        y = 1100;
        sy = 300;

    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("Images/salesman.png"), x+70,sy+270, 250,300 );
        drawTool.drawImage(createNewImage("Images/Shop.png"),x, y);

    }


    @Override
    public void update(double dt) {
        super.update(dt);
        if(delay > 0){
            delay -= 1000*dt;
        }else if(delay <0){
            delay = 0;
            setToolData();
        }

        if(open) {
            if (sy < 900) sy += 500 * dt;
             else sy = 900;
            if(y > 445){
                y -= 650*dt;
                if(tools != null) {
                    for (int i = 0; i < tools.length; i++) {
                        if (i < 6) tools[i].setY( y+50 + 128);
                        else tools[i].setY( y+50 + 128 + 112);
                    }
                }
            }
            else{
                y = 420;
                if(tools != null) {
                    for (int i = 0; i < tools.length; i++) {
                        if (i < 6) tools[i].setY( y + 128);
                        else tools[i].setY( y + 128 + 112);
                    }
                }
            }
        }else{
            if (sy > 320) sy -= 500 * dt;
            else sy = 300;
            if(y < 1100) {
                y += 550 * dt;
                if(tools != null) {
                    for (int i = 0; i < tools.length; i++) {
                        if (i < 6) tools[i].setY( y-50 + 128);
                        else tools[i].setY( y-50 + 128 + 112);
                    }
                }
            }
            else {
                y = 1100;
            }
            }


    }

    public void setToolData(){

            try{
                Statement stmt = con.createStatement();
                ResultSet results = stmt.executeQuery("" +
                        "SELECT COUNT(toolID) " +
                        "FROM DD_Tool ;");
                results.next();
                tools = new Tool[results.getInt(1)];

                results = stmt.executeQuery("" +
                        "SELECT toolID " +
                        "FROM DD_Tool;" );
                int i = 0;
                while(results.next()){
                    if(i < 6){
                        Tool tool = new Tool(x+23+i*81,y+128,results.getInt("toolID"),con, player,this);
                        tools[i] = tool;

                    }else{
                        Tool tool = new Tool(x+23+(i-6)*81,y+128+112,results.getInt("toolID"),con,player,this);
                        tools[i] = tool;
                    }
                    ui.drawObject(tools[i]);
                    i++;
                }

            }catch(SQLException e){
                System.out.println(e);
                e.printStackTrace();
            }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(!clicked){
            if(e.getX() > x+190 && e.getX() < x+310 && e.getY() > sy+300 && e.getY() < sy+370){
                open = true;
            }
            if(open){
                if(e.getX() > x +479 && e.getX() < x+508 && e.getY() > y+89 && e.getY() < y+116 ){
                    open = false;
                }
            }

        }
        clicked = !clicked;
    }

    public void unselectEvTh(Tool notUnselect){
        for (int i = 0; i < tools.length; i++) {
            if(notUnselect != tools[i]){
                tools[i].setSelected(false);
            }
        }
    }
}