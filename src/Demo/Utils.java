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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils
{
	static double[] GetParaTab(String paraType)
	{
		Scanner scan = null;
		try
		{
			scan = new Scanner(new File("data/" + paraType + ".dat"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		List<Double> list = new ArrayList<Double>();
		while(scan.hasNextLine())
		{
			list.add(new Double(scan.nextLine()));
		}
		
		double[] res = new double[list.size()];
		for(int i=0; i<list.size(); i++)
		{
			res[i] = list.get(i);
		}
		
		return res;
	}
}