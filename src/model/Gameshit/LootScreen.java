package model.Gameshit;

import control.framework.UIController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;
import java.sql.Statement;

public class LootScreen extends GraphicalObject {

    private int x,y;

    private Item[] items;
    private UIController ui;
    private Player player;

    public LootScreen(int x, int y, UIController ui, Player player){
        this.x = x;
        this.y = y;
        this.ui = ui;
        this.player = player;
        items = new Item[1];
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("images/beute.png"),x,y);
        for (int i = 1; i < items.length; i++) {
            //sieht komisch aus, ich weiß. Hat grafische Gründe!
            if(i <= 3) {
                drawTool.drawImage(items[i].getMyImage(), x + 35, y + 25 + i * 55, 40, 40);
                drawTool.setFont("Impact", 24, false);
                drawTool.drawText(x + 85, y + 55 + i * 55, "x " + items[i].getAmount());
                drawTool.setFont("Arial", 12, false);
                drawTool.drawText(x + 20, y + 75 + i * 55, items[i].getName());
            }else if(i <= 6){
                drawTool.drawImage(items[i].getMyImage(), x + 35 + 100, y + 25 + (i-3) * 55, 40, 40);
                drawTool.setFont("Impact", 24, false);
                drawTool.drawText(x + 85+100, y + 55 + (i-3) * 55, "x " + items[i].getAmount());
                drawTool.setFont("Arial", 12, false);
                drawTool.drawText(x + 20+100, y + 75 + (i-3) * 55, items[i].getName());
            }
            else if(i <= 9){
                drawTool.drawImage(items[i].getMyImage(), x + 35 + 200, y + 25 + (i-6) * 55, 40, 40);
                drawTool.setFont("Impact", 24, false);
                drawTool.drawText(x + 85+200, y + 55 + (i-6) * 55, "x " + items[i].getAmount());
                drawTool.setFont("Arial", 12, false);
                drawTool.drawText(x + 20+200, y + 75 + (i-6) * 55, items[i].getName());
            }else if(i <= 12){
                drawTool.drawImage(items[i].getMyImage(), x + 35 + 300, y + 25 + (i-9) * 55, 40, 40);
                drawTool.setFont("Impact", 24, false);
                drawTool.drawText(x + 85+300, y + 55 + (i-9) * 55, "x " + items[i].getAmount());
                drawTool.setFont("Arial", 12, false);
                drawTool.drawText(x + 20+300, y + 75 + (i-9) * 55, items[i].getName());
            }else if(i <= 15){
                drawTool.drawImage(items[i].getMyImage(), x + 35 + 400, y + 25 + (i-12) * 55, 40, 40);
                drawTool.setFont("Impact", 24, false);
                drawTool.drawText(x + 85+400, y + 55 + (i-12) * 55, "x " + items[i].getAmount());
                drawTool.setFont("Arial", 12, false);
                drawTool.drawText(x + 20+400, y + 75 + (i-12) * 55, items[i].getName());
            }




        }
        drawTool.drawRectangle(x+210,445,85,45);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(e.getX()> x+210 && e.getX() < x+295 && e.getY() > 445 && e.getY() < 490){
            player.closeLootScreen(items);
        }
    }

    public void addItem(int id, Statement stmt){
        boolean isThere = false;

        for (int i = 0; i < items.length && !isThere; i++) {
            if(items[i] != null){
                if(id == items[i].getID()){
                    items[i].addOne();
                    isThere = true;
                }
            }
        }
        if(!isThere){
            Item[] temp = new Item[items.length+1];
            for (int i = 0; i < items.length; i++) {
                temp[i] = items[i];
            }
            temp[temp.length-1] = new Item(id,stmt);
            items = temp;

        }

    }






}