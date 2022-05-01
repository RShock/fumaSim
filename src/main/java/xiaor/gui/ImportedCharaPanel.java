package xiaor.gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class ImportedCharaPanel extends CharaPanel{

    public ImportedCharaPanel(int pos) {
        super(pos);

        name = new JComboBox<>();
        attack = new JTextField(8);
        star = new JComboBox<>();
        skill = new JComboBox<>();
        potential = new JComboBox<>();

        // Test - Hard Code
        List<String> charaters = new ArrayList<>();
        charaters.add("机灵古怪 塞露西亚");
        charaters.add("法斯公主 露露");
        charaters.add("精灵王 塞露西亚");
        charaters.add("复生公主 千鹤");
        charaters.add("胆小纸袋狼 沃沃");
        charaters.add("小恶魔 布兰妮");
        charaters.add("夏日 露露");
        charaters.add("偶像 黑白诺艾利");
        charaters.add("丰收圣女 菲欧拉");
        charaters.add("新春 凛月");
        charaters.add("梦游魔境 露露");
        charaters.add("梦游魔境 千鹤");
        charaters.add("异界 凯萨");
        charaters.add("花嫁 撒旦");

        // Character List Initialization
        for (String chara : charaters) {
            name.addItem(chara);
        }

        IntStream.rangeClosed(1,5)
                .forEach(i -> star.addItem(i));

        // Skill Initialization
        for (int i = 1; i <= 5; i++) {
            skill.addItem(i);
        }

        // Potential Initialization
        for (int i = 1; i <= 12; i++) {
            potential.addItem(i);
        }

        if (pos == 1)
            this.add(new JLabel("队长位[%d]: ".formatted(pos)));
        else
            this.add(new JLabel("角色位[%d]: ".formatted(pos)));
        this.add(name);
        this.add(new JLabel("攻击力"));
        this.add(attack);
        this.add(new JLabel("星"));
        this.add(star);
        this.add(new JLabel("绊"));
        this.add(skill);
        this.add(new JLabel("潜力"));
        this.add(potential);

    }

}
