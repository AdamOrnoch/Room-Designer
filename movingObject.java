import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

class movingObject extends JPanel{
    int xPos;
    int yPos;

    public movingObject(){
        xPos = 0;
        yPos = 0;
        repaint();
    }

    public void repaint(Graphics g){
        g.setColor(Color.RED);
        g.fillOval(xPos,yPos,50,50);
    }

    public void move(){
        this.repaint(xPos, yPos, 5, 5);
    }
}
