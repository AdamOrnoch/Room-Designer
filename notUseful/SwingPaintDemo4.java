package notUseful;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;

public class SwingPaintDemo4 {
    private static JFrame f;
    
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.add(new MyPanel(10));

        f.setSize(250,250);
        f.setVisible(true);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.BLUE);
        btnPanel.setBounds(200, 0, 200, 200);
        f.add(btnPanel);

        JButton b = new JButton("Spawn square");
        b.setPreferredSize(new Dimension(100, 100));
        b.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                placeShape();
            }
        });
        btnPanel.add(b);
    }

    public static void placeShape(){
        f.add(new MyPanel(1));
    }
} 


class MyPanel extends JPanel {

    RedSquare redSquare;
    RedSquare redSquare2;

    public MyPanel(int x) {

        redSquare = new RedSquare(x);
        redSquare2 = new RedSquare(x+20);


        setBorder(BorderFactory.createLineBorder(Color.black));

        //addMouseListener(new MouseAdapter(){
         //   public void mousePressed(MouseEvent e){
       //         moveSquare(e.getX(),e.getY());
        //    }
      //  });

        addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent e){
                moveSquare(e.getX(),e.getY());
            }
        });

    }

    private void moveSquare(int x, int y){

        // Current square state, stored as final variables 
        // to avoid repeat invocations of the same methods.
        final int CURR_X = redSquare.getX();
        final int CURR_Y = redSquare.getY();
        final int CURR_W = redSquare.getWidth();
        final int CURR_H = redSquare.getHeight();
        final int OFFSET = 50;

        if ((CURR_X!=x) || (CURR_Y!=y)) {

            // The square is moving, repaint background 
            // over the old square location. 
            repaint(CURR_X,CURR_Y,CURR_W+OFFSET,CURR_H+OFFSET);

            // Update coordinates.
            redSquare.setX(x);
            redSquare.setY(y);

            // Repaint the square at the new location.
            repaint(redSquare.getX(), redSquare.getY(), 
                    redSquare.getWidth()+OFFSET, 
                    redSquare.getHeight()+OFFSET);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        g.drawString("This is my custom Panel!",10,20);

        redSquare.paintSquare(g);
        redSquare2.paintSquare(g);
    }  
}

class RedSquare{

    private int xPos;
    private int yPos = 50;
    private int width = 20;
    private int height = 20;

    public RedSquare(int x){
        xPos = x;
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