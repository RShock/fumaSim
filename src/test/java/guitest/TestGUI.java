package guitest;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.gui.FumaSimulator;
import xiaor.gui.TeamPanel;
import xiaor.gui.buttons.CalculationButton;
import xiaor.gui.buttons.HelpButton;
import xiaor.gui.buttons.LoadFileButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

import static xiaor.excel.ExcelReader.getXlsxPictures;

@Disabled
public class TestGUI extends JFrame {

    public TestGUI() {
        initGUI();

        File resourcePath = new File(URLDecoder.decode(Objects.requireNonNull(getClass().getResource("/")).getPath(), StandardCharsets.UTF_8));
        String excelPath = resourcePath.getParent() + "/classes/角色技能资料.xlsx";

        try {
            Workbook book = new XSSFWorkbook(excelPath);
            Sheet userDataSheet = book.getSheet("Sheet1");
            var picMap = getXlsxPictures((XSSFSheet) userDataSheet);
            Assertions.assertNotNull(picMap);
            Image image = Toolkit.getDefaultToolkit().createImage(picMap.get(1).getData());
            ImageIcon imageIcon = new ImageIcon(image);

            // Layout - Hard code
            JLabel jLabel = new JLabel(imageIcon);
            add(jLabel).setBounds(40, 0, 100, 100);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void initGUI() {
        setVisible(true);
        setTitle("天下布魔伤害模拟器");
        setSize(800, 800);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Test
    public void should_show_gui() {
        SwingUtilities.invokeLater(TestGUI::new);
        Scanner input = new Scanner(System.in);
        input.hasNextLine();
    }
}
