package xiaor.gui;

import xiaor.GameBoard;
import xiaor.gui.buttons.CalculationButton;
import xiaor.gui.buttons.HelpButton;
import xiaor.gui.buttons.LoadFileButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.nio.file.Path;

public class FumaSimulator extends JFrame{

    private final GameBoard board = GameBoard.getInstance();
    private String waves = "";

    public FumaSimulator() {
        initGUI();

        Font font = (new Font("", Font.BOLD, 20));
        JLabel ourTeamInfo = new JLabel("玩家队伍");
        JLabel theirTeamInfo = new JLabel("敌对队伍");
        ourTeamInfo.setFont(font);
        theirTeamInfo.setFont(font);

        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        TeamPanel ourTeam = new TeamPanel('p',5);
        TeamPanel theirTeam = new TeamPanel('d',1);
        ourTeam.setBorder(blackline);
        theirTeam.setBorder(blackline);

        CalculationButton calculation = new CalculationButton("计算", this, ourTeam, theirTeam);
        calculation.setFont(font);

        LoadFileButton loadFilePath = new LoadFileButton("打开战斗配置文件", this);
        loadFilePath.setFont(font);

        HelpButton help = new HelpButton("帮助", this);
        help.setFont(font);

        // Layout - Hard code
        add(ourTeamInfo).setBounds(40, 0, 100, 100);
        add(ourTeam).setBounds(50,65,700,280);
        add(theirTeamInfo).setBounds(40, 315, 100, 100);
        add(theirTeam).setBounds(50,380,700,280);
        add(loadFilePath).setBounds(50, 690, 300, 50);
        add(help).setBounds(400, 690, 150, 50);
        add(calculation).setBounds(600, 690, 150, 50);
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

    public String getWaves() {
        return waves;
    }

    public void setWaves(String waves) {
        this.waves = waves;
    }

    public GameBoard getBoard() {
        return board;
    }
}
