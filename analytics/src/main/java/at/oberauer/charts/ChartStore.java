package at.oberauer.charts;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.HashMap;

/**
 * Created by michael on 06.12.17.
 */
public class ChartStore {

    private HashMap<String, BufferedImage> storage = new HashMap<>();

    public String put(BufferedImage bmp){
        String key = String.valueOf(bmp.hashCode());
        storage.put(key, bmp);
        return key;
    }

    public BufferedImage get(String key){
        return storage.get(key);
    }

}
