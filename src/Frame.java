import javax.swing.JFrame;

/**
 * Class to setup the frame of a graphical output
 * 
 * @author Sebastian Sonne
 * @version v2 25.06.23
 */
public class Frame extends JFrame{
    
    Frame() {
        Panel panel = new Panel();
        
        this.add(panel);
        this.setTitle("Tic Tac Toe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        Frame frame = new Frame();
    }
}
