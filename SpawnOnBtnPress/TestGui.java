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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class CustomFrame extends JFrame{
    public CustomJ selectedSquare;

    CustomFrame(){
        addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent e){
                selectedSquare.move(e.getX(),e.getY());
            }
        });
    }
}


public class TestGui {
    public static CustomFrame f;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGui(); 
            }
        });
    }

    

    public static void createGui(){
        f = new CustomFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.setSize(500,500);
        f.setVisible(true);
        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(Color.BLUE);
        p1.setBounds(0, 400, 500, 100);
        p1.setLayout(new FlowLayout(FlowLayout.CENTER));
        f.add(p1);

        CustomJ p2 = new CustomJ(0, 0);
        p2.setVisible(false);
        f.add(p2);
        f.selectedSquare = p2;

        JButton btn1 = new JButton();
        btn1.setBorder(BorderFactory.createCompoundBorder(
                   BorderFactory.createLineBorder(Color.red),
                   btn1.getBorder()));
                   btn1.setPreferredSize(new Dimension(50, 50));
        btn1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (p2.isVisible())
                    p2.setVisible(false);
                else{
                    p2.setVisible(true);
                }
            }
        });


        p1.add(btn1);

        JButton btn2 = new JButton();
        btn2.setBorder(BorderFactory.createCompoundBorder(
                   BorderFactory.createLineBorder(Color.red),
                   btn2.getBorder()));
                   btn2.setPreferredSize(new Dimension(50, 50));
        btn2.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println("Filler");
        }
    });
        p1.add(btn2);
    }   
}


class CustomJ extends JPanel{
    public CustomJ(int startingX, int startingY){
        setBounds(startingX, startingY, 50, 50);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.ORANGE);
    }

    public void move(int x, int y){
        setBounds(x, y, 50, 50);
    }
}