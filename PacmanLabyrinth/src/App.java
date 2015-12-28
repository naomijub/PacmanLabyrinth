
import javax.swing.JFrame;
import java.awt.EventQueue;

/**
 * @Naomijub
 *
 *Pacman Labyrinth Game
 *
 *2015
 */
public class App extends JFrame
{
	public App(){
		initUI();
	}
	
	 private void initUI() {
	        
	        add(new Board());
	        setTitle("Labyrinth@Naomijub");
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setSize(380, 420);
	        setLocationRelativeTo(null);
	        setVisible(true);   
	       
	    }
	
    public static void main( String[] args )
    {
    	EventQueue.invokeLater(new Runnable() {
			 
           // @Override
            public void run() {
                App ex = new App();
                ex.setVisible(true);
            }
        });
    }
}
