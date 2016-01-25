package graphs;

import com.googlecode.charts4j.*;
import org.openjdk.jmh.results.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Witold on 2016-01-24.
 */
public class GraphUtil {

    public static String generateGraphURL(List<String> labels, List<Result> results) {
        ArrayList<Double> rawdata = new ArrayList<>(results.size());
        double maxValue = 0;
        double minValue = Double.MAX_VALUE;
        for (Result r : results) {
            double val = r.getScore();
            rawdata.add(val);
            if (maxValue < val)
                maxValue = val;
            if (minValue > val)
                minValue = val;
        }
        Data plotData = DataUtil.scaleWithinRange(0, maxValue * 1.5, rawdata);
        BarChartPlot plot = Plots.newBarChartPlot(plotData);
        BarChart chart = GCharts.newBarChart(plot);
        AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.BLACK, 13, AxisTextAlignment.CENTER);
        AxisLabels benchLabels = AxisLabelsFactory.newAxisLabels(labels);
        benchLabels.setAxisStyle(axisStyle);
        chart.addXAxisLabels(benchLabels);
        chart.setSize(400, 400);
        return chart.toURLString();
    }
}
