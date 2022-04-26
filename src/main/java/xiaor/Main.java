package xiaor;

import xiaor.gui.FumaSimulator;

import javax.swing.*;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
	    // GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FumaSimulator(new File(URLDecoder.decode(Objects.requireNonNull(getClass()
                        .getResource("/")).getPath(), StandardCharsets.UTF_8))
                        .getParent() + "/classes/frame.properties");
            }
        });

    }
}
