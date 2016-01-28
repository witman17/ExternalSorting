package charts;


import org.knowm.xchart.Chart;
import org.knowm.xchart.ChartBuilder;
import org.knowm.xchart.StyleManager;
import org.openjdk.jmh.results.Result;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Witold on 2016-01-24.
 */
public class ChartUtil {


    public static Chart getBarChart(List<String> benchNames, List<Result> results) {
        double maxValue = 0;
        ArrayList<Double> rawData = new ArrayList<>(results.size());
        ArrayList<Double> stdData = new ArrayList<>(results.size());
        for (Result r : results) {
            double val = r.getScore();
            rawData.add(val);
            stdData.add(r.getStatistics().getStandardDeviation());
            if (maxValue < val)
                maxValue = val;
        }
        Chart chart = new ChartBuilder().width(600).height(400).title("Wynik Testu").theme(StyleManager.ChartTheme.XChart).build();
        setChartStyle(chart.getStyleManager());
        chart.addSeries("Test", benchNames, rawData, stdData);
        chart.setYAxisTitle("(ms)");


        return chart;
    }

    private static void setChartStyle(StyleManager manager) {
        manager.setChartType(StyleManager.ChartType.Bar);
        manager.setLegendVisible(false);
        manager.setAxisTickLabelsFont(new Font("New Times Roman", Font.PLAIN, 9));
//        manager.setLegendPosition(StyleManager.LegendPosition.OutsideE);
        manager.setYAxisTitleVisible(true);
        manager.setBarWidthPercentage(.90);
    }

}
