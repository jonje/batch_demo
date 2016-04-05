package gov.utah.dts;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jjensen on 4/5/16.
 */
public class PropertyFile {
    Map<String, String> entries = new HashMap<>();

    public PropertyFile(InputStream inputStream) throws FileNotFoundException, IOException{
        read(inputStream);

    }

    public void read (InputStream inputStream) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.contains(":")) {
                String[] strings = line.split(":");
                String key = strings[0];
                String value = strings[0];

                if (value.startsWith(" ")) {
                    value = value.substring(1);
                }
                entries.put(key, value);

            }

        }

    }

    public String getEntry(String key) {
        return entries.get(key);
    }
}
