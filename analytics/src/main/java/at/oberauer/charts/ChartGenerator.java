package at.oberauer.charts;

import at.oberauer.data.KeywordResult;
import javafx.util.Pair;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by michael on 06.12.17.
 */
public class ChartGenerator {

    public static BufferedImage generatePerChannelAreaChart(Iterable<KeywordResult> results, int chartWidth, int chartHeight, String chartTitle){
        BufferedImage image = new BufferedImage(chartWidth, chartHeight, BufferedImage.TYPE_INT_RGB);
        final XYChart chart = new XYChartBuilder()
                .width(chartWidth)
                .height(chartHeight)
                .title(chartTitle)
                .xAxisTitle("Date")
                .yAxisTitle("# of mentions")
                .build();
        //Add XY Series per Channel
        HashMap<String, HashMap<Date, Integer>> points = new HashMap<>();
        for(KeywordResult r : results) {
            if (!points.containsKey(r.getChannel())) {
                points.put(r.getChannel(), new HashMap<>());
            }
            if(!points.get(r.getChannel()).containsKey(r.getDate())){
                points.get(r.getChannel()).put(r.getDate(), 1);
            }
            int newval = 1 + points.get(r.getChannel()).get(r.getDate());
            points.get(r.getChannel()).put(r.getDate(), newval);
        }

        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();

        System.out.println("Points Processing started");

        for(String a : points.keySet()){
            for(Date d : points.get(a).keySet()){
                counts.add(points.get(a).get(d));
                dates.add(d);
            }
            dates.sort( (c1, c2) -> {return c1.before(c2) ? -1 : 1;});
            chart.addSeries(a, dates, counts);
            System.out.println(Arrays.toString(dates.toArray()));
            System.out.println(Arrays.toString(counts.toArray()));
            dates = new ArrayList<>();
            counts = new ArrayList<>();
        }


        System.out.println("Points Processing finished");

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);

        chart.paint(image.createGraphics(), chartWidth, chartHeight);

        System.out.println("Image Painted!");
        return image;
    }

}
