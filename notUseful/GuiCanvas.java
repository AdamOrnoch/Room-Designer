package notUseful;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GuiCanvas{
    private static JFrame f;
    private static JPanel roomPanel;

    public static void createAndShowGui(){
        GuiCanvas GUI = new GuiCanvas();
        f = new JFrame("Main");
        f.setSize(1000, 800);
        f.setResizable(false);
        f.setVisible(true);
        f.setLayout(null);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.BLUE);
        btnPanel.setBounds(200, 0, 200, 200);
        f.add(btnPanel);

        roomPanel = new JPanel();
        roomPanel.setBackground(Color.GREEN);
        roomPanel.setBounds(0, 0, 200, 200);
        //f.add(roomPanel);


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
        RoomJObject square = new RoomJObject();
        f.add(square);
    }






    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                createAndShowGui();
            }
        });
    }
}