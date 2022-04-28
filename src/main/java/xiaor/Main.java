package xiaor;

import com.formdev.flatlaf.FlatDarculaLaf;
import xiaor.gui.FumaSimulator;

import javax.swing.*;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
//         GUI
        SwingUtilities.invokeLater(FumaSimulator::new);
    }
}
