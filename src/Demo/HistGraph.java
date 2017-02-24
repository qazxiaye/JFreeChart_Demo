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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

public class HistGraph extends JPanel implements MouseListener
{
	List<String> paraType_list;
	JList paraSelector;
	
	ChartPanel chartPane;
	JFreeChart[] charts;
	
	public HistGraph(List<String> paraType_list)
	{
		this.paraType_list = paraType_list;
		
		
		//widgets: charts(JFreeChart), parameter selector (JList)
		chartPane = new ChartPanel(null);
		charts = new JFreeChart[paraType_list.size()];
		
		DefaultListModel modelOfList = new DefaultListModel();
        paraSelector = new JList(modelOfList);
        
        for(int i=0; i<paraType_list.size(); i++)
        {
        	modelOfList.addElement(paraType_list.get(i));
        	charts[i] = null;
        }
        
        paraSelector.setSelectedIndex(0);
        
        charts[0] = CreateChart(paraType_list.get(0));
        chartPane.setChart(charts[0]);
        
        
        JScrollPane selectorPanel = new JScrollPane();
    	selectorPanel.setViewportView(paraSelector);
        
        paraSelector.addMouseListener(this);
		
		
		//layout
        GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
        gbc.weighty = 1;
        gbc.anchor  = GridBagConstraints.WEST;
        
        gbc.insets = new Insets(5,20,5,5);
        gbc.gridx  = 0;
        gbc.gridy  = 0;
        gbc.weightx = 20;
        add(new JLabel("Distribution:"),gbc);
        
        gbc.insets = new Insets(5,5,5,20);
        gbc.gridx  = 1;
        gbc.gridy  = 0;
        gbc.weightx = 1;
        add(new JLabel("Parameters:"),gbc);
        
        gbc.weighty = 14;
        
        gbc.insets = new Insets(5,20,5,5);
        gbc.gridx  = 0;
        gbc.gridy  = 1;
        gbc.weightx = 20;
        add(chartPane,gbc);
        
        gbc.insets = new Insets(5,5,15,20);
        gbc.gridx  = 1;
        gbc.gridy  = 1;
        gbc.weightx = 1;
        add(selectorPanel,gbc);
	}

	private JFreeChart CreateChart(String para)
	{
		//read .dat file
		double[] value = Utils.GetParaTab(para);
		
		//generate chart
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.FREQUENCY);
		dataset.addSeries(para, value, 20);
		
		String plotTitle = para;
		String xaxis = "value";
		String yaxis = "frequecy";
		PlotOrientation orientation = PlotOrientation.VERTICAL;
		
		boolean show = false;
		boolean toolTips = false;
		boolean urls = false;
		
		JFreeChart chart = ChartFactory.createHistogram(plotTitle, xaxis,
				yaxis, dataset, orientation, show, toolTips, urls);
		
		final XYPlot xyPlot = chart.getXYPlot();
		final XYBarRenderer renderer = (XYBarRenderer) xyPlot.getRenderer();
		renderer.setSeriesPaint(0, Color.BLUE);
		
		return chart;
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(e.getSource() == paraSelector)
		{
			int selected = paraSelector.getSelectedIndex();
			
			if(charts[selected] == null)
				charts[selected] = CreateChart(paraType_list.get(selected));
			
			chartPane.setChart(charts[selected]);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}
}