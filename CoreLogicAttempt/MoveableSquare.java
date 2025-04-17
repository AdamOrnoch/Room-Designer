package CoreLogicAttempt;

import java.awt.Graphics; 
import java.awt.Color;

class MoveableSquare{
    private int xPos;
    private int yPos;
    private int width = 20;
    private int height = 20;

    MoveableSquare(int startingX, int startingY){
        xPos = startingX;
        yPos = startingY;
    }

    public void setX(int xPos){ 
        this.xPos = xPos;
    }

    public int getX(){
        return xPos;
    }

    public void setY(int yPos){
        this.yPos = yPos;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    } 

    public int getHeight(){
        return height;
    }   
    
    public void paintSquare(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(xPos,yPos,width,height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos,yPos,width,height);  
    }
}