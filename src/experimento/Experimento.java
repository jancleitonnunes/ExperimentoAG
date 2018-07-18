package experimento;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/** @author Jancleiton Nunes */

public class Experimento {        
    
    public static void main(String[] args) throws UnsupportedEncodingException{   
        try {
            UIManager.setLookAndFeel( "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Experimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Experimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Experimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Experimento.class.getName()).log(Level.SEVERE, null, ex);
        }
        ExperimentoInterface janelaPrincipal = new ExperimentoInterface();
        janelaPrincipal.show();    
        janelaPrincipal.setExtendedState(MAXIMIZED_BOTH);
    }           
}
