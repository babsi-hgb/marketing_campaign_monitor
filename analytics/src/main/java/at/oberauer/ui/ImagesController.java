package at.oberauer.ui;

import at.oberauer.charts.ChartStore;
import javafx.scene.chart.Chart;
import org.bouncycastle.util.encoders.BufferedDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by michael on 07.12.17.
 */
@Controller()
public class ImagesController {

    private ChartStore chartStore;

    @Autowired
    public ImagesController(ChartStore chartStore){
        this.chartStore = chartStore;
    }


    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody byte[] getFile(@RequestParam("id") String id)  {
        try {
            BufferedImage img = chartStore.get(id);
            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream();

            // Write to output stream
            ImageIO.write(img, "jpg", bao);

            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
