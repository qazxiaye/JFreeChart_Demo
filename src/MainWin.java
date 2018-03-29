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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import Demo.HistGraph;
import Demo.LineGraph;
import Demo.ScatterGraph;

public class MainWin extends JTabbedPane {
    int sampleNb;
    List<String> paraType_list = new ArrayList<String>();

    private MainWin() {
        super();

        Init();

        addTab("Line", new LineGraph(sampleNb));
        addTab("Histgram", new HistGraph(paraType_list));
        addTab("Scatter", new ScatterGraph(sampleNb, paraType_list));
    }

    private void Init() {
        Scanner scan = null;

        // Init sample nb
        try {
            scan = new Scanner(new File("data/fmc.import"));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "fmc.import File doesn't exist.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        sampleNb = scan.nextInt();

        // Init parameter list
        try {
            scan = new Scanner(new File("data/test.sobol"));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "test.sobol File doesn't exist.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        while (scan.hasNextLine()) {
            String s[] = scan.nextLine().split(" ");
            paraType_list.add(s[0]);
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.add(new MainWin());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}