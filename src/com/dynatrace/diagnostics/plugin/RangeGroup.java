package com.dynatrace.diagnostics.plugin;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.pdk.MonitorMeasure;

public class RangeGroup {

	NavigableMap<Integer,Integer> groups = new TreeMap<Integer, Integer>();
	
	public RangeGroup(String[] groupArray) {
		for (String group : groupArray) {
			groups.put(Integer.parseInt(group), 0);
			groups.put(Integer.MAX_VALUE, 0);
		}
	}
	
	public void addToGroup(double value) {
		for (Entry<Integer, Integer> group : groups.entrySet()) {
			if (value < group.getKey()){
				groups.put(group.getKey(), group.getValue() + 1);
				IPlanetMonitor.log.finer("added to group " + group.getKey());
				break;
			}
		}
	}
	
	public void sendMeasures(MonitorEnvironment env, MonitorMeasure measure, String dashletName){
		int previousValue = 0;
		for (Entry<Integer, Integer> group : groups.entrySet()) {
			String currentValueString = "-" + String.valueOf(group.getKey());
			if (group.getKey() == Integer.MAX_VALUE){
				currentValueString = "+";
			}
			String range = previousValue + currentValueString;
			if (env == null || measure == null){
				System.out.println("Range-" + dashletName + " - " + range + " = " + group.getValue());
			}else{
				IPlanetMonitor.log.finer("Result: Range-" + dashletName + " - " + range + " = " + group.getValue());
				env.createDynamicMeasure(measure, "Range-" + dashletName, range).setValue(group.getValue());;
			}
			previousValue = group.getKey();
		}
	}
	
	public void resetGroups() {
		for (Integer group : groups.keySet()) {
			groups.put(group, 0);
		}
	}
	
	
}
