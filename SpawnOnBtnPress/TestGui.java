package SpawnOnBtnPress;

import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.Toolkit;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.util.ArrayList;

class CursorTrackingJPanel extends JPanel implements MouseListener, MouseMotionListener{
    public CustomJ selectedSquare;
    public Point startPoint;

    public void assignMoveableToObject(CustomJ square){
        selectedSquare = square;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        //Converts the point at the cursor within 'selectedSquare' system into the parents system
        Point location = SwingUtilities.convertPoint(selectedSquare, e.getPoint(), selectedSquare.getParent()); 
        //System.out.println(location + " " + e.getPoint());
        
        //Border checking doesn't work
        Point newLocation = selectedSquare.getLocation();
        newLocation.translate(location.x - startPoint.x, location.y - startPoint.y);

        //COLLISION LOGIC
        if (TestGui.visibleObjectArray.size() > 1){ //2 squares test
            CustomJ o2 = TestGui.visibleObjectArray.get(1);
            if (TestGui.visibleObjectArray.get(0).getBounds().intersects(TestGui.visibleObjectArray.get(1).getBounds())){ // Checks for intersection
                if (((newLocation.y >= o2.getY()) && (newLocation.y <= (o2.getY()+o2.getHeight()))) ||
                ((newLocation.y+selectedSquare.getHeight() >= o2.getY()) && (newLocation.y+selectedSquare.getHeight() <= (o2.getY()+o2.getHeight())))){ 
                    if (newLocation.x > o2.getX()+(o2.getWidth()/2)){ //checks if on right side
                        newLocation.x = Math.max(newLocation.x, o2.getX()+o2.getWidth());
                    } else{ // left side
                        newLocation.x = Math.min(newLocation.x, o2.getX()-selectedSquare.getWidth());
                    }
                } else{
                    if (newLocation.y > o2.getY()+(o2.getHeight()/2)){ 
                        newLocation.y = Math.min(newLocation.y, o2.getY()+1);
                    } else{ 
                        newLocation.x = Math.min(newLocation.x, o2.getX()-selectedSquare.getWidth());
                    }
                }
                //System.out.println(TestGui.visibleObjectArray.get(1).getX()+TestGui.visibleObjectArray.get(1).getWidth()+" "+newLocation.x);
                //newLocation.y = Math.max(newLocation.y, 0);
                //newLocation.x = Math.min(newLocation.x, selectedSquare.getParent().getWidth() - selectedSquare.getWidth());
                //newLocation.y = Math.min(newLocation.y, selectedSquare.getParent().getHeight() - selectedSquare.getHeight());

                selectedSquare.setLocation(newLocation);
                startPoint = location;
            }else{

                selectedSquare.setLocation(newLocation);
                startPoint = location;
            }
        }else{
            //Makes sure selectedSquare doesn't leave its parent's boundary
            newLocation.x = Math.max(newLocation.x, 0);
            newLocation.y = Math.max(newLocation.y, 0);
            newLocation.x = Math.min(newLocation.x, selectedSquare.getParent().getWidth() - selectedSquare.getWidth());
            newLocation.y = Math.min(newLocation.y, selectedSquare.getParent().getHeight() - selectedSquare.getHeight());

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
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double width = screenSize.getWidth();
                double height = screenSize.getHeight();
                System.out.println(Double.toString(width) + " " + Double.toString(height));
                createGui(); 
            }
        });
    }

    public static void createGui(){
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.setResizable(false);
        f.setSize(1600,939);

        //Fullscreen code:
        //GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //GraphicsDevice device = graphics.getDefaultScreenDevice();
        //device.setFullScreenWindow(f);  

        f.setVisible(true);
        f.setLayout(null);

        roomZone = new CursorTrackingJPanel();
        roomZone.setBackground(Color.GRAY);
        roomZone.setBounds(0, 70, 1300, 830);
        roomZone.setVisible(true);
        f.add(roomZone);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(102, 153, 153, 255));
        topPanel.setBounds(0, 0, 1300, 70);
        f.add(topPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(153, 204, 255, 255));
        rightPanel.setBounds(1300, 0, 300, 900);
        rightPanel.setLayout(null);
        f.add(rightPanel);

        JPanel innerScrollPanel = new JPanel();
        innerScrollPanel.setLayout(new BoxLayout(innerScrollPanel, BoxLayout.PAGE_AXIS));


        JScrollPane scrollItemsPanel = new JScrollPane(innerScrollPanel);
        rightPanel.add(scrollItemsPanel);
        scrollItemsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollItemsPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollItemsPanel.setBounds(0, 0, 285, 700);

        JLabel icon1 = new JLabel("SQUARE");
        icon1.setAlignmentX(icon1.CENTER_ALIGNMENT);
        icon1.setBorder(new EmptyBorder(30, 0, 0, 30));
        innerScrollPanel.add(icon1);

        JLabel icon2 = new JLabel("Shape 2");
        icon2.setAlignmentX(icon2.CENTER_ALIGNMENT);
        icon2.setBorder(new EmptyBorder(30, 0, 0, 30));
        innerScrollPanel.add(icon2);

        JLabel icon3 = new JLabel("Shape 3");
        icon3.setAlignmentX(icon3.CENTER_ALIGNMENT);
        icon3.setBorder(new EmptyBorder(30, 0, 0, 30));
        innerScrollPanel.add(icon3);

        


        JButton btn1 = new JButton("REMOVE");
        btn1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (!visibleObjectArray.isEmpty()){
                    roomZone.remove(visibleObjectArray.get(0));
                    visibleObjectArray.remove(0);
                    roomZone.repaint();
                }
            }
        });


        rightPanel.add(btn1);
        btn1.setBounds(0, 700, 300, 200);

        JButton btn2 = new JButton("ADD");
        btn2.setBorder(BorderFactory.createCompoundBorder(
                   BorderFactory.createLineBorder(Color.red),
                   btn2.getBorder()));
                   btn2.setPreferredSize(new Dimension(70, 50));
        btn2.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            CustomJ t = new CustomJ(10, 10, roomZone);
            visibleObjectArray.add(t);
            roomZone.add(t);
            roomZone.assignMoveableToObject(t);
            roomZone.repaint();
        }
    });

    innerScrollPanel.add(btn2);
    btn2.setAlignmentX(btn2.CENTER_ALIGNMENT);
    }   
}


class CustomJ extends JPanel implements MouseListener{
    private CursorTrackingJPanel panel;
    public CustomJ(int startingX, int startingY, CursorTrackingJPanel p){
        panel = p;
        this.setBounds(startingX, startingY, 50, 50);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.ORANGE);
        setVisible(true);

        this.addMouseListener(panel);
        this.addMouseMotionListener(panel);

        addMouseListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        panel.startPoint = SwingUtilities.convertPoint(this, e.getPoint(), this.getParent());
        panel.assignMoveableToObject(this);
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

}
