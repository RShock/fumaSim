package xiaor.gui.buttons;

import xiaor.gui.FumaSimulator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpButton extends JButton implements ActionListener {

    private FumaSimulator parent;

    public HelpButton(String buttonName, FumaSimulator parent) {
        super(buttonName);
        addActionListener(this);
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(parent, "" +
                """
                1. [请确保计算前]，玩家队伍中的攻击力都填满
                2. [请确保计算前]，正确导入战斗配置文件。具体实例请参考"example.txt"
                3. 当前计算器仅能计算以下角色
                    机灵古怪 塞露西亚
                    精灵王 塞露西亚
                    法斯公主 露露
                    复生公主 千鹤
                    胆小纸袋狼 沃沃
                    小恶魔 布兰妮
                    夏日 露露
                    偶像 黑白诺艾利
                    丰收圣女 菲欧拉
                    新春 凛月
                    梦游魔境 露露
                4. 当前计算器是在天下布魔ma吧主-小r基础上写的GUI
                """, "帮助", JOptionPane.WARNING_MESSAGE);
    }
}
