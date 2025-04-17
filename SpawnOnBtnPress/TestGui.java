package SpawnOnBtnPress;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

class CursorTrackingJPanel extends JPanel implements MouseListener, MouseMotionListener{
    public CustomJ selectedSquare;
    private Point startPoint;

    CursorTrackingJPanel(){
        
    }

    public void assignMoveableToObject(CustomJ component){
        selectedSquare = component;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point location = SwingUtilities.convertPoint(selectedSquare, e.getPoint(), selectedSquare.getParent());
        if (selectedSquare.getParent().getBounds().contains(location)){
            Point newLocation = selectedSquare.getLocation();
            newLocation.translate(location.x - startPoint.x, location.y - startPoint.y);
            newLocation.x = Math.max(newLocation.x, 0);
            newLocation.y = Math.max(newLocation.y, 0);
            newLocation.x = Math.min(newLocation.x, selectedSquare.getParent().getWidth() - selectedSquare.getWidth());
            newLocation.y = Math.min(newLocation.y, selectedSquare.getParent().getWidth() - selectedSquare.getWidth());
            selectedSquare.setLocation(newLocation);
            startPoint = location;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    //    throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    //    throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = SwingUtilities.convertPoint(selectedSquare, e.getPoint(), selectedSquare.getParent());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        startPoint = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
   //     throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
     //   throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }


}


public class TestGui {
    public static JFrame f;
    public static ArrayList<CustomJ> visibleObjectArray = new ArrayList<CustomJ>();
    public static CursorTrackingJPanel roomZone;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGui(); 
            }
        });
    }

    public static void createGui(){
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.setSize(500,500);
        f.setVisible(true);
        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(Color.BLUE);
        p1.setBounds(0, 400, 500, 100);
        p1.setLayout(new FlowLayout(FlowLayout.CENTER));
        f.add(p1);

        roomZone = new CursorTrackingJPanel();
        roomZone.setBackground(Color.GREEN);
        roomZone.setBounds(0, 0, 500, 300);
        roomZone.setVisible(true);
        f.add(roomZone);

        CustomJ p2 = new CustomJ(0, 0, roomZone);
        p2.setVisible(true);
        roomZone.add(p2);
        roomZone.selectedSquare = p2;


        JButton btn1 = new JButton("REMOVE");
        btn1.setBorder(BorderFactory.createCompoundBorder(
                   BorderFactory.createLineBorder(Color.red),
                   btn1.getBorder()));
                   btn1.setPreferredSize(new Dimension(100, 50));
        btn1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                roomZone.remove(visibleObjectArray.get(0));
                visibleObjectArray.remove(0);
                roomZone.repaint();
            }
        });


        p1.add(btn1);

        JButton btn2 = new JButton("ADD");
        btn2.setBorder(BorderFactory.createCompoundBorder(
                   BorderFactory.createLineBorder(Color.red),
                   btn2.getBorder()));
                   btn2.setPreferredSize(new Dimension(70, 50));
        btn2.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            CustomJ t = new CustomJ(0, 0, roomZone);
            visibleObjectArray.add(t);
            roomZone.add(t);
            roomZone.assignMoveableToObject(t);
        }
    });
        p1.add(btn2);
    }   
}


class CustomJ extends JPanel{
    public CustomJ(int startingX, int startingY, CursorTrackingJPanel panel){
        setBounds(startingX, startingY, 50, 50);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.ORANGE);
        setVisible(true);

        this.addMouseListener(panel);
        this.addMouseMotionListener(panel);

        //addMouseListener(new MyMouseListener(panel, this));
    }

    public void move(int x, int y){
        setBounds(x, y, 50, 50);
    }
}

    class MyMouseListener implements MouseListener {
        CursorTrackingJPanel panel;
        CustomJ moveableObj;
        MyMouseListener(CursorTrackingJPanel p, CustomJ obj){
            panel = p;
            moveableObj = obj;
        }
    public void mouseClicked(MouseEvent event) {
    } 
    public void mouseEntered(MouseEvent event) {
    }
    public void mouseExited(MouseEvent event) {
    }
    public void mousePressed(MouseEvent event) {
        panel.assignMoveableToObject(moveableObj);
        System.out.println("pressed");} 
    public void mouseReleased(MouseEvent event) {
    }
    } // inner class clickListener