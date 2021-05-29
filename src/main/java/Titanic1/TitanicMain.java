/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Titanic1;
        
        
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Color;
import java.util.stream.Collectors;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

/**
 *
 * @author amrad
 */
public class TitanicMain {
    
    public static void main(String[] args) {
        
        TitanicMain obj = new TitanicMain();
        obj.graphPassengerClass(getPassengersFromJsonFile());
        obj.graphPassengerSurvived(getPassengersFromJsonFile());
        obj.graphPassengerSurvivedGender(getPassengersFromJsonFile());
    }
    


public static List<Passenger> getPassengersFromJsonFile() {
        List<Passenger> allPassengers = new ArrayList<Passenger> ();
        ObjectMapper objectMapper = new ObjectMapper ();
        objectMapper.configure (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try (InputStream input = new FileInputStream ("C:\\Users\\amrad\\Downloads\\Day6\\titanic_csv.json")) {
            //Read JSON file
            allPassengers = objectMapper.readValue (input, new TypeReference<List<Passenger>> () {
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
return allPassengers;

}



public static void graphPassengerAges(List<Passenger> passengerList) {
		List<Float> pAges= passengerList.stream().map (Passenger::getAge).limit (8).collect (Collectors.toList());
		List<String> pNames= passengerList.stream().map (Passenger::getName).limit (8).collect (Collectors.toList());
		
		CategoryChart chart = new CategoryChartBuilder().width (1024).height (768).title ("Age Histogram").xAxisTitle("Names").yAxisTitle("Age").build();
		chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
		chart.getStyler().setHasAnnotations(true);
		chart.getStyler().setStacked(true);
		chart.addSeries("Passenger's Ages", pNames, pAges);
		new SwingWrapper(chart).displayChart();
		
		}
	


	public void graphPassengerClass(List<Passenger> passengerList) {
		//filter to get a map of passenger class and total number of passengers in each class
		Map<String, Long> result =passengerList.stream().collect (
				Collectors.groupingBy(Passenger::getPclass, Collectors.counting() ) );
	
	PieChart chart = new PieChartBuilder().width (800).height (600).title (getClass().getSimpleName()).build ();
	// Customize Chart
	Color[] sliceColors= new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
	chart.getStyler().setSeriesColors(sliceColors);
	// Series
	chart.addSeries("First Class", result.get("1"));
	chart.addSeries("Second Class", result.get("2"));
	chart.addSeries("Third Class", result.get("3"));
	// Show it 
	new SwingWrapper(chart).displayChart();
	}
	
	public void graphPassengerSurvived(List<Passenger> passengerList) {
		
		Map<String, Long> result =passengerList.stream().collect (
				Collectors.groupingBy(Passenger::getSurvived, Collectors.counting() ) );
	
	PieChart chart = new PieChartBuilder().width (800).height (600).title (getClass().getSimpleName()).build ();
	// Customize Chart
	Color[] sliceColors= new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
	chart.getStyler().setSeriesColors(sliceColors);
	// Series
	chart.addSeries("Survived", result.get("0"));
	chart.addSeries("Not Survived", result.get("1"));
	// Show it 
	new SwingWrapper(chart).displayChart();
	}
        
        public void graphPassengerSurvivedGender(List<Passenger> passengerList) {
		
		Map<String, Long> result =passengerList.stream().filter(x->x.getSurvived().equals("1")).collect (
				Collectors.groupingBy(Passenger::getSex, Collectors.counting() ) );
	
	PieChart chart = new PieChartBuilder().width (800).height (600).title (getClass().getSimpleName()).build ();
	// Customize Chart
	Color[] sliceColors= new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
	chart.getStyler().setSeriesColors(sliceColors);
	// Series of survived males and females
	chart.addSeries("Survived Males", result.get("male"));
	chart.addSeries("Survived Females", result.get("female"));
	// Show it 
	new SwingWrapper(chart).displayChart();
        
}



}

