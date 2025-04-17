package CoreLogicAttempt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class RoomJObject extends JPanel{
    MoveableSquare ms;

    public RoomJObject(){
        ms = new MoveableSquare(10, 10);

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                moveJObject(e.getX(),e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent e){
                moveJObject(e.getX(), e.getY());
            }
        });
    }

    private void moveJObject(int x, int y){
        final int currentX = ms.getX();
        final int currentY = ms.getY();
        final int currentWidth = ms.getWidth();
        final int currentHeight = ms.getHeight();

        if ((currentX!=x) || (currentY != y)){
            repaint(currentX, currentY, currentWidth, currentHeight);

            ms.setX(x);
            ms.setY(y);

            repaint(ms.getX(), ms.getY(), ms.getWidth(), ms.getHeight());
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        ms.paintSquare(g);
    }  
}
