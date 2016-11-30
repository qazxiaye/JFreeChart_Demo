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

import Demo.LineGraph;
import Demo.HistGraph;
import Demo.ScatterGraph;

public class Main extends JTabbedPane
{
	int sampleNb;
	List<String> paraList = new ArrayList<String>();
	
	public Main()
	{
		super();
		
		Init();
		
		addTab("Line",     new LineGraph(sampleNb));
		addTab("Histgram", new HistGraph(paraList));
		addTab("Scatter",  new ScatterGraph(sampleNb, paraList));
	}
	
	private void Init()
	{
		//Init sample nb
		Scanner scan = null;
		
		try
		{
			scan = new Scanner(new File("data/fmc.import"));
		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(this, "fmc.import File doesn't exist.",
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		sampleNb = scan.nextInt();
		
		//Yield nb
//		line = scan.nextLine();
		
		//Init parameter list
		try
		{
			scan = new Scanner(new File("data/test.sobol"));
		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(this, "test.sobol File doesn't exist.",
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		while(scan.hasNextLine())
		{
			String s[] = scan.nextLine().split(" ");
			paraList.add(s[0]);
		}
	}
	
	public static void main(String[] args)
    {
		JFrame f = new JFrame();
		f.add(new Main());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();           
		f.setVisible(true);
    }
}