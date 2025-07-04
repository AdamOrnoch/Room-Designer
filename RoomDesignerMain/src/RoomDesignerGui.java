package RoomDesignerMain.src;

import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.Toolkit;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.util.ArrayList;

class CursorTrackingJPanel extends JPanel implements MouseListener, MouseMotionListener{
    public CustomJ selectedSquare;
    public Point startPoint;

    public void assignMoveableToObject(CustomJ square){
        if (selectedSquare != null){
            selectedSquare.changeColorDefault();
        }
        this.selectedSquare = square;
        this.selectedSquare.changeColorSelected();
    }

    public CustomJ getSelectedSquare(){
        return selectedSquare;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //Converts the point at the cursor within 'selectedSquare' system into the parents system
        Point location = SwingUtilities.convertPoint(selectedSquare, e.getPoint(), selectedSquare.getParent()); 
        
        //Border checking doesn't work
        Point newLocation = selectedSquare.getLocation();
        newLocation.translate(location.x - startPoint.x, location.y - startPoint.y);

        //COLLISION LOGIC
        Boolean isCollision = false;
        for (CustomJ otherSquare:RoomDesignerGui.visibleObjectArray){
            if (otherSquare != selectedSquare){
                if (selectedSquare.checkForCollision(otherSquare)){ // Checks for intersection
                    newLocation = resolveCollision(selectedSquare.getBounds(), otherSquare.getBounds());
                    selectedSquare.translateTo(newLocation);
                    startPoint = location;
                    isCollision = true;
                }      
            }
        }
        if (isCollision == false){
            //Makes sure selectedSquare doesn't leave its parent's boundary
            newLocation.x = Math.max(newLocation.x, 0);
            newLocation.y = Math.max(newLocation.y, 0);
            newLocation.x = Math.min(newLocation.x, selectedSquare.getParent().getWidth() - selectedSquare.getWidth());
            newLocation.y = Math.min(newLocation.y, selectedSquare.getParent().getHeight() - selectedSquare.getHeight());

            selectedSquare.translateTo(newLocation);
            startPoint = location;
        }  
    }
    
    public Point resolveCollision(Rectangle s1, Rectangle s2) {
        // Calculate overlap on both axes
        Integer overlapX = Math.min(s1.x + s1.width, s2.x + s2.width) - Math.max(s1.x, s2.x);
        Integer overlapY = Math.min(s1.y + s1.height, s2.y + s2.height) - Math.max(s1.y, s2.y);

        Point newLocation = s1.getLocation();
    
        // Move in the direction of the least penetration
        if (overlapX < overlapY) {
            if (s1.x < s2.x) {
                newLocation.x -= overlapX;
            } else {
                newLocation.x += overlapX;
            }
        } else {
            if (s1.y < s2.y) {
                newLocation.y -= overlapY;
            } else {
                newLocation.y += overlapY;
            }
        }

        return newLocation;
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
        if (selectedSquare != null){
            startPoint = SwingUtilities.convertPoint(selectedSquare, e.getPoint(), selectedSquare.getParent());
        }
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


public class RoomDesignerGui {
    private static JFrame f;
    public static ArrayList<CustomJ> visibleObjectArray = new ArrayList<CustomJ>();
    public static CursorTrackingJPanel roomZone;
    public static JPanel roomHolder;
    public static JPanel innerScrollPanel;
    public static JPanel fp;

    public static void main(String[] args) {
        RoomDesignerGui main = new RoomDesignerGui();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                main.frameSetup();
                main.startWindow();
            }
        });
    }

    private void frameSetup(){
        f = new JFrame("Room Designer");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.setResizable(false);
        f.setSize(1600,939);
        f.setLayout(null);

        fp = new JPanel();
        fp.setLayout(null); 
        fp.setBackground(Color.BLACK);
        f.setContentPane(fp);
        f.setVisible(true);
    }

    private void createItemWindow(CursorTrackingJPanel parsedPanel){
        final CursorTrackingJPanel p = parsedPanel; // Fixes error of in 

        JFrame popUpFrame = new JFrame();
        popUpFrame.setAlwaysOnTop(true);
        popUpFrame.setResizable(false);
        popUpFrame.setSize(400, 400);
        popUpFrame.setLocation(f.getX()+(f.getWidth()/2)-(popUpFrame.getWidth()/2), f.getY()+(f.getHeight()/2)-(popUpFrame.getHeight()/2));
        popUpFrame.setVisible(true); 

        JPanel popUpPanel = new JPanel();
        popUpPanel.setSize(popUpFrame.getSize());
        popUpPanel.setBackground(Color.GREEN);
        popUpPanel.setLayout(new GridBagLayout());
        popUpFrame.add(popUpPanel);

        GridBagConstraints c = new GridBagConstraints();

        JLabel title = new JLabel("Title");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        popUpPanel.add(title, c);

        JTextField widthTextBox = new JTextField();
        widthTextBox.setColumns(10);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        popUpPanel.add(widthTextBox, c);

        JTextField heightTextBox = new JTextField();
        heightTextBox.setColumns(10);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        popUpPanel.add(heightTextBox, c);

        JTextField nameTextBox = new JTextField();
        nameTextBox.setColumns(10);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        popUpPanel.add(nameTextBox, c);

        JLabel nameLabel = new JLabel("Name:");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        popUpPanel.add(nameLabel, c);

        JLabel widthLabel = new JLabel("Width:");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        popUpPanel.add(widthLabel, c);

        JLabel heightLabel = new JLabel("Height:");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        popUpPanel.add(heightLabel, c);

        JButton createBtn = new JButton("Create");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        popUpPanel.add(createBtn, c);

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            int width = Integer.parseInt(widthTextBox.getText());
            int height = Integer.parseInt(heightTextBox.getText());
            String name = nameTextBox.getText();
            CustomJ createdItem = new CustomJ(name, 0, 0, width, height, p);
            displayItem(createdItem);
            addItemToScrollable(name, width, height);
            }
        });
    }

    private static void addItemToScrollable(String name, int width, int height){
        JLabel icon1 = new JLabel("<html>" + name + "<br>Width: " + width + "<br>Height: " + height + "</html>");
        icon1.setAlignmentX(icon1.CENTER_ALIGNMENT);
        icon1.setBorder(new EmptyBorder(30, 0, 0, 30));
        innerScrollPanel.add(icon1);
        innerScrollPanel.revalidate();
        innerScrollPanel.repaint();
    }

    private static void createRoomZoneWindow(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.ORANGE);
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBounds(0, 0, 500, 500);
        roomHolder.add(panel);

        JLabel widthLabel = new JLabel("Width:");
        JTextField widthField = new JTextField();
        widthField.setPreferredSize(new Dimension(100, 25));

        JLabel heightLabel = new JLabel("Height:");
        JTextField heightField = new JTextField();
        heightField.setPreferredSize(new Dimension(100, 25));

        panel.add(widthLabel);
        panel.add(widthField);
        panel.add(heightLabel);
        panel.add(heightField);

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            roomHolder.remove(panel);

            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            roomZone = new CursorTrackingJPanel();
            roomZone.setBackground(Color.BLUE);
            roomZone.setBounds(110, 110, width, height);
            roomHolder.add(roomZone);
            
            roomHolder.revalidate();
            roomHolder.repaint();
        });

        panel.add(submitButton);
    }
    
    private void startWindow(){
        JPanel backgroundPanel = new JPanel() {
            Image backgroundImage = new ImageIcon("RoomDesignerMain\\images\\frontWindow.png").getImage();
        
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null); 
        backgroundPanel.setBounds(0,0,fp.getWidth(), fp.getHeight());

        ImageIcon startBtnImage = new ImageIcon("RoomDesignerMain\\images\\startBtn.png");
        JButton startBtn = new JButton(startBtnImage);
        Border buttonBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 3),  // Outer border: blue, 3px
            BorderFactory.createEmptyBorder(10, 20, 10, 20) // Inner padding: 10px top and bottom, 20px left and right
        );
        startBtn.setBorder(buttonBorder);
        startBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                fp.remove(backgroundPanel);
                mainWindow();
                fp.revalidate();
                fp.repaint();
            }
        });

        backgroundPanel.add(startBtn);
        startBtn.setBounds(230, 520, 300, 150);

        fp.add(backgroundPanel);
    }

    private void mainWindow(){

        roomHolder = new JPanel();
        roomHolder.setBackground(Color.GRAY);
        roomHolder.setBounds(0, 70, 1300, 830);
        roomHolder.setLayout(null);
        fp.add(roomHolder);

        createRoomZoneWindow();

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(102, 153, 153, 255));
        topPanel.setBounds(0, 0, 1300, 70);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        fp.add(topPanel);

        JButton createItem = new JButton("Create");
        createItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                createItemWindow(roomZone);
            }
        });

        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(createItem);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(153, 204, 255, 255));
        rightPanel.setBounds(1300, 0, 300, 900);
        rightPanel.setLayout(null);
        fp.add(rightPanel);

        innerScrollPanel = new JPanel();
        innerScrollPanel.setLayout(new BoxLayout(innerScrollPanel, BoxLayout.PAGE_AXIS));

        JScrollPane scrollItemsPanel = new JScrollPane(innerScrollPanel);
        rightPanel.add(scrollItemsPanel);
        scrollItemsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollItemsPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollItemsPanel.setBounds(0, 0, 285, 700);

        JButton btn1 = new JButton("REMOVE");
        btn1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (!visibleObjectArray.isEmpty() && roomZone.selectedSquare != null){
                    CustomJ cur = roomZone.getSelectedSquare();
                    roomZone.remove(cur);
                    visibleObjectArray.remove(cur);
                    roomZone.repaint();
                    roomZone.selectedSquare = null;
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
            CustomJ t = new CustomJ("TEST", 0, 0, 40, 40, roomZone);
            displayItem(t);
        }
    });

    innerScrollPanel.add(btn2);
    btn2.setAlignmentX(btn2.CENTER_ALIGNMENT);
    } 
    
    public static void displayItem(CustomJ item){
        visibleObjectArray.add(item);
        CursorTrackingJPanel allocatedPanel = item.getPanel();
        allocatedPanel.add(item);
        allocatedPanel.assignMoveableToObject(item);
        allocatedPanel.repaint();
    }
}


class CustomJ extends JPanel implements MouseListener{
    private CursorTrackingJPanel panel;
    private Rectangle collider;
    private String name;
    public CustomJ(String gName, int startingX, int startingY, int width, int height, CursorTrackingJPanel p){
        panel = p;
        name = gName;
        this.setBounds(startingX, startingY, width, height);
        collider = new Rectangle(startingX, startingY, width, height); //Creates collider 1 pixel bigger

        setBorder(BorderFactory.createLineBorder(Color.black));
        changeColorDefault();
        setVisible(true);

        this.setLayout(null);
        JLabel displayName = new JLabel(name);
        Dimension preferredLabelSize = displayName.getPreferredSize();
        int labelWidth = preferredLabelSize.width;
        int labelHeight = preferredLabelSize.height;
        displayName.setBounds((width/2 - labelWidth/2), (height/2 - labelHeight/2), labelWidth, labelHeight);
        this.add(displayName);


        this.addMouseListener(panel);
        this.addMouseMotionListener(panel);

        addMouseListener(this);
    }

    public void changeColorDefault(){
        setBackground(Color.ORANGE);
    }
    public void changeColorSelected(){
        setBackground(Color.RED);
    }

    public boolean checkForCollision(CustomJ other){
        if (this.collider.intersects(other.getCollider())){
            return true;
        }
        return false;
    }

    public Rectangle getCollider(){
        return this.collider;
    }

    public CursorTrackingJPanel getPanel(){
        return this.panel;
    }

    public void translateTo(Point newLocation){ //Translate square and collider
        this.collider.setLocation(newLocation);
        this.setLocation(newLocation);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        panel.assignMoveableToObject(this);
        panel.startPoint = SwingUtilities.convertPoint(this, e.getPoint(), this.getParent());


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
