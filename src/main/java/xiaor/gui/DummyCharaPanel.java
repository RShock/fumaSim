package xiaor.gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DummyCharaPanel extends CharaPanel{
    public DummyCharaPanel(int pos) {
        super(pos);

        name = new JComboBox();
        health = new JTextField("0",16);

        // Component - Setting
        health.setEditable(false);

        // Test - Hard Code
        List<String> charaters = new ArrayList<>();
        charaters.add("木桩");

        // Character List Initialization
        for (String chara : charaters) {
            name.addItem(chara);
        }

        this.add(new JLabel("角色位[%d]: ".formatted(pos)));
        this.add(name);
        this.add(new JLabel("生命"));
        this.add(health);
    }

    @Override
    public String toString() {
        return "生命"+health.getText().toString();
    }
}
