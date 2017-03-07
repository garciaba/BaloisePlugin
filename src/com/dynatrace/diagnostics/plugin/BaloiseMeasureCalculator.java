package com.dynatrace.diagnostics.plugin;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaloiseMeasureCalculator {

	private static final String MEASURE_ATTRIBUTE = "measure";
	private Node measure1;
	private Node measure2;
	private String measure1NamePrefix;
	private Double sumMeasure1;
	private Double sumMeasure2;
	private Double countMeasure1;
	private Double countMeasure2;

	public BaloiseMeasureCalculator(Node measure1, Node measure2) {
		this.measure1 = getMeasurementNode(measure1);
		this.measure2 = getMeasurementNode(measure2);
		String measure1Name = measure1.getAttributes().getNamedItem(MEASURE_ATTRIBUTE).getNodeValue();
		this.measure1NamePrefix = measure1Name.split("-")[0];
	}
	
	private Node getMeasurementNode(Node measure){
		NodeList measurements = measure.getChildNodes();
		for (int i = 0; i < measurements.getLength(); i++) {
			if (measurements.item(i).getNodeName().equals("measurement")){
				return measurements.item(i);
			}
		}
		return null;
	}

	public double calculateNetDiff() {

		double result = 0;
		if (this.measure2 != null) {
			// Extract the sum and count attribute from <measure> tag for first
			// measure
			this.sumMeasure1 = Double.parseDouble(this.measure1.getAttributes().getNamedItem("sum").getNodeValue());
			this.countMeasure1 = Double.parseDouble(this.measure1.getAttributes().getNamedItem("count").getNodeValue());
			// Extract the sum attribute from <measure> tag for second measure
			Double measure2Final = new Double(0);
			Double countMeasure2Final = new Double(0);
			if (this.measure2.getAttributes() != null && this.measure2.getAttributes().getNamedItem("sum") != null && this.measure2.getAttributes().getNamedItem("sum").getNodeValue() != null){
				this.sumMeasure2 = Double.parseDouble(this.measure2.getAttributes().getNamedItem("sum").getNodeValue());
				if (this.sumMeasure2 != null){
					measure2Final = this.sumMeasure2;
				}
			}
			this.sumMeasure2 = measure2Final;
			if (this.measure2.getAttributes() != null && this.measure2.getAttributes().getNamedItem("count") != null && this.measure2.getAttributes().getNamedItem("count").getNodeValue() != null){
				this.countMeasure2 = Double.parseDouble(this.measure2.getAttributes().getNamedItem("count").getNodeValue());
				if (this.countMeasure2 != null){
					countMeasure2Final = this.countMeasure2;
				}
			}
			this.countMeasure2 = countMeasure2Final;
			
			result = Math.abs(sumMeasure1.doubleValue() - this.sumMeasure2.doubleValue()) / countMeasure1;

		} else {
			this.sumMeasure1 = Double.parseDouble(this.measure1.getAttributes().getNamedItem("sum").getNodeValue());
			result = this.sumMeasure1;
		}

		return result;

	}

	public String getMeasure1NamePrefix() {
		return measure1NamePrefix;
	}

	public Double getSumMeasure1() {
		return sumMeasure1;
	}

	public Double getSumMeasure2() {
		return sumMeasure2;
	}

	public Double getCountMeasure1() {
		return countMeasure1;
	}

	public Double getCountMeasure2() {
		return countMeasure2;
	}

	public String toString() {

		return measure1NamePrefix;

	}

}
