package com.dynatrace.diagnostics.plugin;

public class Main {

	public static void main(String[] args) {
		IPlanetMonitor iPlanetMonitor = new IPlanetMonitor();
		try {
			iPlanetMonitor.setup(null);
			iPlanetMonitor.execute(null);
			iPlanetMonitor.teardown(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}