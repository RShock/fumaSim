package xiaor.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CharaPanel extends JPanel {

    protected JComboBox<String> name;
    protected JTextField attack;
    protected JTextField health;
    protected JComboBox<Integer> star;
    protected JComboBox<Integer> skill;
    protected JComboBox<Integer> potential;

    public CharaPanel(int pos) {
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    }

    @Override
    public String toString() {
        String chara = "";

        if (name != null) chara += name.getSelectedItem().toString() + " ";
        if (attack != null) chara += "攻击力" + attack.getText() + " ";
        if (health != null) chara += "生命" + health.getText() + " ";
        if (star != null) chara += "星" + star.getSelectedItem().toString() + " ";
        if (skill != null) chara += "绊" + skill.getSelectedItem().toString() + " ";
        if (potential != null) chara += "潜" + potential.getSelectedItem().toString() + " ";

        return chara;
    }
}
