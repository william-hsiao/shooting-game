package shooter;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Image;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Shooter extends JFrame{
    public Shooter(){
        setSize(1000,650);
        setResizable(false);
        setTitle("Shooter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        MyJPanel myJPanel= new MyJPanel();
        Container c = getContentPane();
        c.add(myJPanel);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Shooter();
    }
}
