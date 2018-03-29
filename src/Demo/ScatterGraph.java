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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

public class ScatterGraph extends JPanel implements MouseListener {
    int sampleNb;

    List<String> paraType_list;
    JList xSelector;
    JList ySelector;

    ChartPanel chartPane;
    JFreeChart[][] charts;
    DefaultXYDataset dataSet;

    public ScatterGraph(int sampleNb, List<String> paraType_list) {
        this.sampleNb = sampleNb;
        this.paraType_list = paraType_list;

        // widgets: charts(JFreeChart), parameter selectors (JList)
        DefaultListModel xModel = new DefaultListModel();
        xSelector = new JList(xModel);
        JScrollPane xSelPane = new JScrollPane();
        xSelPane.setViewportView(xSelector);

        DefaultListModel yModel = new DefaultListModel();
        ySelector = new JList(yModel);
        JScrollPane ySelPane = new JScrollPane();
        ySelPane.setViewportView(ySelector);

        charts = new JFreeChart[paraType_list.size()][paraType_list.size()];
        for (int i = 0; i < paraType_list.size(); i++) {
            String para = paraType_list.get(i);
            xModel.addElement(para);
            yModel.addElement(para);

            for (int j = 0; j < paraType_list.size(); j++) {
                charts[i][j] = null;
            }
        }

        charts[0][0] = CreateChart(0, 0);
        chartPane = new ChartPanel(charts[0][0]);

        xSelector.setSelectedIndex(0);
        ySelector.setSelectedIndex(0);

        xSelector.addMouseListener(this);
        ySelector.addMouseListener(this);

        // layout
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.insets = new Insets(5, 20, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 20;
        gbc.weighty = 12;
        gbc.gridheight = 4;
        add(chartPane, gbc);

        gbc.insets = new Insets(5, 5, 5, 20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.gridheight = 1;

        gbc.gridy = 0;
        gbc.weighty = 1;
        add(new JLabel("X :"), gbc);
        gbc.gridy = 2;
        add(new JLabel("Y :"), gbc);

        gbc.gridy = 1;
        gbc.weighty = 5;
        add(xSelPane, gbc);
        gbc.gridy = 3;
        add(ySelPane, gbc);
    }

    private JFreeChart CreateChart(int x, int y) {
        dataSet = new DefaultXYDataset();
        AddDataSet(x, y);

        JFreeChart jfreechart = ChartFactory.createScatterPlot("Scatter", paraType_list.get(x), paraType_list.get(y),
                dataSet, PlotOrientation.VERTICAL, true, false, false);

        XYPlot plot = (XYPlot) jfreechart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        plot.setDomainGridlinesVisible(true);
        plot.setNoDataMessage("no data");

        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) plot.getRenderer();
        xylineandshaperenderer.setSeriesOutlinePaint(0, Color.WHITE);
        xylineandshaperenderer.setUseOutlinePaint(true);
        xylineandshaperenderer.setSeriesPaint(0, Color.BLUE);

        return jfreechart;
    }

    private void AddDataSet(int x, int y) {
        List<Integer> failIndexList = new ArrayList<Integer>();

        // read fail index
        Scanner scan = null;
        try {
            scan = new Scanner(new File("data/failIndex.dat"));
        } catch (FileNotFoundException e) {
            JOptionPane
                    .showMessageDialog(this, "failIndex.dat File doesn't exist.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        while (scan.hasNextLine()) {
            failIndexList.add(Integer.parseInt(scan.nextLine()));
        }

        // read X, Y
        double[] valueX = Utils.GetParaTab(paraType_list.get(x));
        double[] valueY = Utils.GetParaTab(paraType_list.get(y));

        // create data set
        double[][] dataSetPass = new double[2][sampleNb - failIndexList.size()];
        double[][] dataSetFail = new double[2][failIndexList.size()];

        int i_f = 0;
        int i_p = 0;
        for (int i = 0; i < sampleNb; i++) {
            if (failIndexList.contains(new Integer(i + 1))) {
                dataSetFail[0][i_f] = valueX[i];
                dataSetFail[1][i_f] = valueY[i];
                i_f++;
            } else {
                dataSetPass[0][i_p] = valueX[i];
                dataSetPass[1][i_p] = valueY[i];
                i_p++;
            }
        }

        dataSet.addSeries("pass", dataSetPass);
        dataSet.addSeries("fail", dataSetFail);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == xSelector || e.getSource() == ySelector) {
            int x = xSelector.getSelectedIndex();
            int y = ySelector.getSelectedIndex();

            if (charts[x][y] == null) {
                charts[x][y] = CreateChart(x, y);
            }

            chartPane.setChart(charts[x][y]);
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
