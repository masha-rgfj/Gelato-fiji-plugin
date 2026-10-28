package gelanno;

import ij.plugin.PlugIn;
import javax.swing.SwingUtilities;

public class GelAnno_Plugin implements PlugIn {
    @Override
    public void run(String arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GelAnno.Controller().showFrame();
            }
        });
    }
}
