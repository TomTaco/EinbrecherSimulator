package model.Gameshit;


import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tool extends GraphicalObject {

    private int x,y;
    private int id;
    private String name;
    private int price;
    private boolean buyable;

    private Connection con;
    private Player player;

    public Tool(int x, int y, int id, Connection con, Player player){
        this.x = x;
        this.y = y;
        this.player = player;
        this.id = id;
        this.con = con;
        setData();
    }

    private void setData() {
        try{
            ResultSet results = con.createStatement().executeQuery("" +
                    "SELECT term, price " +
                    "FROM DD_Tool " +
                    "WHERE toolID = "+id+";");
            results.next();
            name = results.getString("term");
            price = results.getInt("price");

        }catch(SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("Images/Items/"+name+".png"),x,y);
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.setFont("Arial",10,false);
        drawTool.drawText(x+1,y+79,name);
        drawTool.setFont("Impact",12,false);
        if(!buyable) drawTool.setCurrentColor(150,50,50,255);
        else drawTool.setCurrentColor(100,255,100,255);
        drawTool.drawText(x+16,y-2,price+"$");
        if(buyable)drawTool.drawImage(createNewImage("Images/Items/buy.png"),x+33,y+86,36,14);







    }

    public void setY(int y){
        this.y = y;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if(player.getMoney() < price)buyable = false;
        else buyable = true;

    }
}