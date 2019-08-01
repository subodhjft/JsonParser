import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.util.Map;

public class JsonParserTest {
    @Test
    public void processjson() {
        File file = new File(Main.class.getClassLoader().getResource("test.json").getFile());
        Map<String, String> jsonMap = new Main().processJson(file);

        String actual = "{\"stationTitle\":\"Hungama Classic\",\"stationType\":\"old\",\"stationId\":\"playmesong\",\"imageName\":\"hungama-classic\",\"language\":\"kannada\"}";
        try {
            Gson gson = new Gson();
            String received = gson.toJson(jsonMap);
            JSONAssert.assertEquals(received, actual, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
