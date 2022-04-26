package xiaor.gui.buttons;

import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.charas.木桩;
import xiaor.gui.CharaPanel;
import xiaor.gui.FumaSimulator;
import xiaor.gui.TeamPanel;
import xiaor.tools.record.DamageRecorder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculationButton extends JButton implements ActionListener {

    private FumaSimulator parent;
    private TeamPanel ourTeam;
    private TeamPanel theirTeam;
    public CalculationButton(String buttonName, FumaSimulator parent,
                             TeamPanel outTeam, TeamPanel theirTeam) {
        super(buttonName);
        addActionListener(this);
        this.parent = parent;
        this.ourTeam = outTeam;
        this.theirTeam = theirTeam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String team = "";
        for (String chara : ourTeam.teamCharacters()) {
            team += chara + "\n";
        }
        System.out.println(team);

        // Get GameBoard
        GameBoard board = parent.getBoard();

        try {
            // Reset Board
            board.resetBoard();

            // Import Characters
            Chara our1 = ImportedChara.initChara(ourTeam.teamCharacters().get(0) + " 队长");
            Chara our2 = ImportedChara.initChara(ourTeam.teamCharacters().get(1));
            Chara our3 = ImportedChara.initChara(ourTeam.teamCharacters().get(2));
            Chara our4 = ImportedChara.initChara(ourTeam.teamCharacters().get(3));
            Chara our5 = ImportedChara.initChara(ourTeam.teamCharacters().get(4));
            Chara their1 = 木桩.init(theirTeam.teamCharacters().get(0));

            // Add Characters to GameBoard
            board.addOurChara(our1);
            board.addOurChara(our2);
            board.addOurChara(our3);
            board.addOurChara(our4);
            board.addOurChara(our5);
            board.addEnemyChara(their1);

            // Initialization
            board.initSkills();
            if ("".equals(parent.getWaves())) {
                JOptionPane.showMessageDialog(parent, "请先导入战斗配置", "导入失败", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Calculate the damage
            board.run(parent.getWaves());
            DamageRecorder.getInstance().countDamage();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parent, "计算错误，可能有如下原因：\n\t1. 角色攻击力未填完整(为空)", "计算错误", JOptionPane.WARNING_MESSAGE);
        }

    }
}
