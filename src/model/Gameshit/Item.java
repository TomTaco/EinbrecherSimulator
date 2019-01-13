package model.Gameshit;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Item extends GraphicalObject {
    //Attribute
    private int x,y;
    private int price;
    private String name;
    private int iD;

    private Statement stmt;

    public Item(int x, int y, int price, String name){
        this.x = x;
        this.y = y;
        this.price = price;
        this.name = name;
    }

    public Item(int iD, Statement stmt){
        this.iD = iD;
        this.stmt = stmt;
        setData();
        System.out.println("du hast geklaut: "+ name);
    }

    private void setData() {
        try{
            ResultSet results = stmt.executeQuery("SELECT term, price " +
                    "FROM DD_Loot " +
                    "WHERE lootID = "+iD+";");
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
        drawTool.drawImage(createNewImage("images/Items"+name+".png"),x,y);
    }
}