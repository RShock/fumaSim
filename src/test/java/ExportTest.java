import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xiaor.Common;
import xiaor.logger.Export;
import xiaor.tools.Tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static xiaor.Common.目录型输出path;

public class ExportTest {

    @Test
    void should_export_string() {
        Export export1 = new Export("1");
        Export export2 = new Export("2");
        Export export3 = new Export("3");
        Export export4 = new Export("4", Arrays.asList(export3));
        Export export5 = new Export("5", Arrays.asList(export4,export2));
        Export export6 = new Export("3", Arrays.asList(export1,export5));

        String string = new Gson().toJson(Arrays.asList(export6));
        String resourcePath = Common.getResourcePath(this.getClass(), 目录型输出path);
        String s = Tools.readFileAsString(resourcePath);
        Assertions.assertNotNull(s);
        String replace = s.replace("{**data**}", string);
        String exportPath = "output" + new SimpleDateFormat("MM_dd_HH_mm_ss").format(new Date()) + ".html";
        Tools.writeToFile(exportPath, replace);
    }
}
