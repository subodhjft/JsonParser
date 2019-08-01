import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Main {
    Map<String, String> jsonMap = null;

    public static void main(String[] args) {
        File file = new File(Main.class.getClassLoader().getResource("main.json").getFile());
        new Main().processJson(file);
    }

    public Map<String, String> processJson(File file){
        jsonMap = new HashMap<>();
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(file);
            Object obj = jsonParser.parse(fileReader);
            JSONObject objectList = (JSONObject) obj;

            objectList.keySet().forEach(keyStr ->
            {
                Object keyvalue = objectList.get(keyStr);
                //for nested objects iteration if required
                if (keyvalue instanceof JSONObject) {
                    processJsonObject((JSONObject) keyvalue);
                }
                else if(keyvalue instanceof JSONArray){
                    processJsonArray((JSONArray) keyvalue, keyStr.toString());
                }else{
                    System.out.println("key: "+ keyStr + " value: " + keyvalue);
                    jsonMap.put(keyStr.toString(), keyvalue.toString());
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonMap;
    }

    public void processJsonObject(JSONObject jsonObject){
        jsonObject.keySet().forEach(keyStr ->
        {
            Object keyvalue = jsonObject.get(keyStr);
            if (keyvalue instanceof JSONObject) {
                processJsonObject((JSONObject) keyvalue);
            }
            else if(keyvalue instanceof JSONArray){
                processJsonArray((JSONArray) keyvalue, keyStr.toString());
            }else{
                System.out.println("key: "+ keyStr + " value: " + keyvalue);
                jsonMap.put(keyStr.toString(), keyvalue.toString());
            }
        });
    }

    public void processJsonArray(JSONArray jsonArray, String arrayName){
        System.out.println("====== Process Array ===== " + arrayName);
        jsonArray.forEach(item -> {
            JSONObject obj = (JSONObject) item;
            processJsonObject(obj);
        });
    }
}
