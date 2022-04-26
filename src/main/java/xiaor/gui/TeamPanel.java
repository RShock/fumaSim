package xiaor.gui;

import xiaor.charas.ImportedChara;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TeamPanel extends JPanel {

    private List<CharaPanel> characters;

    public TeamPanel(char type, int charaNum) {

        characters = new ArrayList<>();

        try {
            if (charaNum > 5) throw new RuntimeException("一个队伍角色数量不能大于5");
            switch (type) {
                // p for player
                case 'p':
                    for (int i = 1; i <= charaNum; i++) {
                        ImportedCharaPanel chara = new ImportedCharaPanel(i);
                        characters.add(chara);
                        add(chara);
                    }
                    break;
                // d for dummy
                case 'd':
                    for (int i = 1; i <= charaNum; i++) {
                        DummyCharaPanel chara = new DummyCharaPanel(i);
                        characters.add(chara);
                        add(chara);
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<String> teamCharacters() {

        List<String> team = new ArrayList<>();
        for (CharaPanel chara : characters) {
            team.add(chara.toString());
        }

        return team;
    }
}
