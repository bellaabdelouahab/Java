import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.*;
public class App extends JFrame implements MouseListener{

    JButton up ;
    JButton left ;
    JButton center ;
    JButton right ;
    JButton down ;

    App(){
        up     = new JButton("UP");
        left   = new JButton("LEFT");
        center = new JButton("CENTER");
        right  = new JButton("RIGHT");
        down   = new JButton("DOWN");
        setTitle("Hi Application");
        setSize(300 , 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        up.addMouseListener(this);
        add(up , BorderLayout.NORTH);
        add(left , BorderLayout.WEST);
        add(center , BorderLayout.CENTER);
        add(right , BorderLayout.EAST);
        add(down , BorderLayout.SOUTH);
    }
    public static void main(String[] args) throws Exception {
        new App();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
        int i =0;
        while (e.getSource() == up) {   
     
            i++;
            System.out.println(i);
        }
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
