package xiaor.logger;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Export {
    public String name;
    public List<Export> children;

    public Export(String data, List<Export> exportList) {
        this.name = data;
        this.children = exportList;
    }

    public Export(String data) {
        this.name = data;
        this.children = new ArrayList<>();
    }


}
