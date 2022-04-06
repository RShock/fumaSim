import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.ImportedChara;
import xiaor.charas.木桩;
import xiaor.excel.ExcelCharaProvider;
import xiaor.excel.ExcelReader;

public class ExcelCharaTest {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void should_init_first_chara() {
        ImportedChara chara = ExcelCharaProvider.getCharaById(6);

        chara.setLeader(true);
        木桩 dummy = 木桩.init("生命0");
        chara.setSkillLevel(1);
        chara.setStar(5);
        chara.setPotential(8);
        board.addOurChara(chara);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
            1a1
        """);
        Assertions.assertEquals(-2000, dummy.getLife());
    }
}