package main;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SignalPlotter extends JFrame {
	
	private DynamicTimeSeriesCollection _dataSet;
	private Map<String,Integer> _keyIndexTable;
	
	 public SignalPlotter(double minY,double maxY, int timeSize,int numTimeSeries,String title,String signal) {
        super("Signal viewer");
 
        JPanel chartPanel = createChartPanel(minY,maxY,timeSize,numTimeSeries,title,signal);
        add(chartPanel, BorderLayout.CENTER);
        
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}
	 
	    /**
	 * @return the dataSet
	 */
	public DynamicTimeSeriesCollection getDataSet() {
		return _dataSet;
	}

	/**
	 * @param dataSet the dataSet to set
	 */
	public void setDataSet(DynamicTimeSeriesCollection dataSet) {
		_dataSet = dataSet;
	}

	public void addNewData(String agentId,float[] values){
		_dataSet.advanceTime();
		_dataSet.appendData(values);
		
	}

	private JPanel createChartPanel(double minRange, double maxRange, int timeSize, int numTimeSeries,String title,String signal) {        
        // DynamicTimeSeries : pour des times series dynamiques au cours du temps
        // 
        _dataSet=new DynamicTimeSeriesCollection(numTimeSeries,timeSize,new Second());
        _dataSet.setTimeBase(new Second(0,0,0,1,1,2017));
        if(numTimeSeries==2){
        	_dataSet.addSeries(new float[]{0.0f,1.0f}, 0, "Agent1");
        	_dataSet.addSeries(new float[]{1.0f,0.0f}, 1, "Agent2");
        }else if(numTimeSeries==1){
        	_dataSet.addSeries(new float[]{0.0f,1.0f}, 0, "Agent1");
        }
        
        
        JFreeChart chart = ChartFactory.createTimeSeriesChart(title,"t (s)",signal,_dataSet,true,true,false);
        final XYPlot     plot   = chart.getXYPlot();
//        ValueAxis domain = plot.getDomainAxis();
//        domain.setAutoRange(true);
//        domain.setFixedAutoRange(20000.0);
        
        ValueAxis range = plot.getRangeAxis();
        range.setRange(new Range(minRange,maxRange));
        
        
        return new ChartPanel(chart);
    }
 
    
	
    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	SignalPlotter sp = new SignalPlotter(0, 1, 1000, 2,"Example Random Plot","Signal");
                sp.setVisible(true);
                Timer t = new Timer();
                Random r = new Random();
               
        		t.scheduleAtFixedRate(new TimerTask(){
        			@Override
        			public void run() {
        				sp.addNewData("Agent1",new float[]{(float)r.nextDouble(),(float)r.nextDouble()});
        			}}, 100, 1000);
            }
        });
    }
}
