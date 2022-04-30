package xiaor.gui.buttons;

import xiaor.gui.FumaSimulator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class LoadFileButton extends JButton implements ActionListener {

    private final FumaSimulator parent;
    private String filePath;
    public LoadFileButton(String buttonName, FumaSimulator parent) {
        super(buttonName);
        addActionListener(this);
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JFileChooser jf = new JFileChooser();
            jf.showOpenDialog(parent);
            filePath = jf.getSelectedFile().getAbsolutePath();

            File fileName = new File(filePath);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader( new FileInputStream(fileName)));
            String line = "";
            String waves = "";
            while ((line = br.readLine()) != null) {
                waves += line + '\n';
            }
            parent.setWaves(waves);

            JOptionPane.showMessageDialog(parent, "成功导入战斗配置\n路径："+filePath, "成功导入", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parent, "导入战斗配置失败", "导入失败", JOptionPane.WARNING_MESSAGE);
        }

    }

    public String getFilePath() {
        return filePath;
    }
}
