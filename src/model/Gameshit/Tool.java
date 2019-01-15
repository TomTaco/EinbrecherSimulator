package model.Gameshit;


import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;
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
    private boolean selected;
    private boolean bought;
    private boolean clicked;

    private Connection con;
    private Player player;
    private Shop shop;



    public Tool(int x, int y, int id, Connection con, Player player, Shop shop){
        this.x = x;
        this.y = y;
        this.player = player;
        this.id = id;
        this.con = con;
        this.shop = shop;
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
        if(selected) {
            drawTool.setCurrentColor(200, 150, 30, 255);
            for (int i = 0; i < 5; i++) {
                drawTool.drawRectangle(x - 4 + i, y - 5 + i, 70 - i * 2, 70 - i * 2);
            }
        }

        if(bought){
            drawTool.drawImage(createNewImage("Images/select.png"),x+31,y+85,40,16);
        }else {
            if (!buyable) drawTool.setCurrentColor(150, 50, 50, 255);
            else drawTool.setCurrentColor(100, 255, 100, 255);
            drawTool.drawText(x + 16, y - 2, price + "$");
            if (buyable) drawTool.drawImage(createNewImage("Images/Items/buy.png"), x + 33, y + 86, 36, 14);
        }



    }

    public void setY(int y){
        this.y = y;
    }

    @Override
            public void update(double dt) {
                super.update(dt);
                if(player.getMoney() < price || bought)buyable = false;
                else buyable = true;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(!clicked) {
            if (buyable && e.getX() > x + 33 && e.getX() < x + 33 + 36 && e.getY() > y + 86 && e.getY() < y + 86 + 14) {
                buy();
            }
            if (bought && !selected && e.getX() > x && e.getX() < x + 70 && e.getY() > y && e.getY() < y + 70) {
                select();
            }
        }
        clicked = !clicked;
    }

    public void buy(){
        bought = true;
        player.addMoney(-price);
        select();
    }

    public void select(){
        setSelected(true);
        player.setSelectedTool(this);
        shop.unselectEvTh(this);
    }


    public void setSelected(boolean select){
        this.selected = select;
    }
}

