/*
* Copyright @ Ye XIA <qazxiaye@126.com>
* 
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package Demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

public class LineGraph extends JPanel
{
	DefaultCategoryDataset dataSet;
	ChartPanel chartPane;
	int sampleNb;
	
	public LineGraph(int sampleNb)
	{
		this.sampleNb = sampleNb;
		
		InitDataSet();
		InitChart();
		
		//layout
		setLayout(new BorderLayout());
		
		add(Box.createHorizontalStrut(30), BorderLayout.WEST);
		add(Box.createHorizontalStrut(30), BorderLayout.EAST);
		add(Box.createVerticalStrut(30),   BorderLayout.NORTH);
		add(chartPane, BorderLayout.CENTER);
	}
	
	private void InitDataSet()
	{
		double[] yields = Utils.GetParaTab("yield");
		int yieldNb = yields.length;

		String[] curSampleNb = new String[yieldNb];

		for(int i=0; i<yieldNb; i++)
		{
			int n = sampleNb * (i+1) / yieldNb;
			curSampleNb[i] = "" + n;
		}
		
		dataSet = new DefaultCategoryDataset();
		try
		{
			for(int i=0; i<yieldNb; i++)
			{
				dataSet.addValue(yields[i], "yield", curSampleNb[i]);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void InitChart()
	{
		JFreeChart chart = ChartFactory.createLineChart("Yield","sample NB","Probability",
				dataSet,PlotOrientation.VERTICAL,false,false,false);

		CategoryPlot plot = chart.getCategoryPlot();
		
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);
		
		plot.setDomainGridlinesVisible(true);
		plot.setNoDataMessage("no data");
		
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		
		renderer.setBaseItemLabelsVisible(true);
		renderer.setSeriesPaint(0, Color.BLUE);
		
		renderer.setBaseShapesVisible(true);
		renderer.setBaseShapesFilled(true);
		
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelFont(new Font("Times New Romain", 0, 10));
		plot.setRenderer(renderer);

		CategoryAxis axis = plot.getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(Math.PI/6.0));
		
		chartPane = new ChartPanel(chart);
	}
}