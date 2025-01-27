package com.roxiler.assignment;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.roxiler.assignment.entity.BarChartData;
import com.roxiler.assignment.entity.PieChartData;
import com.roxiler.assignment.entity.Statistics;
import com.roxiler.assignment.entity.Transaction;

@SpringBootApplication
public class AssignmentApplication {

	private static List<Transaction> transactions = new ArrayList<>();

	private static final String THIRD_PARTY_API_URL = "https://s3.amazonaws.com/roxiler.com/product_transaction.json";
	
	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}

	@GetMapping("/initialize-database")
	public String initializeDatabase() {
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Transaction[]> response = restTemplate.getForEntity(THIRD_PARTY_API_URL, Transaction[].class);
	    Transaction[] transactionsArray = response.getBody();

	    if (transactionsArray != null) {
	        transactions.clear(); // Clear existing transactions
	        transactions.addAll(Arrays.asList(transactionsArray));
	        return "Database initialized successfully with " + transactions.size() + " transactions.";
	    } else {
	        return "Failed to initialize database: No data received from the API.";
	    }
	}

	// Controller to list transactions
	@GetMapping("/transactions")
	public List<Transaction> listTransactions(@RequestParam String month,
											  @RequestParam(defaultValue = "") String search,
											  @RequestParam(defaultValue = "1") int page,
											  @RequestParam(defaultValue = "10") int perPage) {
		// Filter transactions based on month and search criteria
		List<Transaction> filteredTransactions = transactions.stream()
				.filter(transaction -> transaction.getDateOfSale().toLowerCase().contains(month.toLowerCase()))
				.filter(transaction -> search.isEmpty() ||
						transaction.getTitle().toLowerCase().contains(search.toLowerCase()) ||
						transaction.getDescription().toLowerCase().contains(search.toLowerCase()))
				.collect(Collectors.toList());

		// Perform pagination
		int startIdx = (page - 1) * perPage;
		int endIdx = Math.min(startIdx + perPage, filteredTransactions.size());
		return filteredTransactions.subList(startIdx, endIdx);
	}

	// Controller to get statistics
	@GetMapping("/statistics")
	public Statistics getStatistics(@RequestParam String month) {
		int totalSaleAmount = 0;
		int totalSoldItems = 0;
		int totalNotSoldItems = 0;

		for (Transaction transaction : transactions) {
			if (transaction.getDateOfSale().toLowerCase().contains(month.toLowerCase())) {
				totalSaleAmount += transaction.getPrice();
				if (transaction.isSold()) {
					totalSoldItems++;
				} else {
					totalNotSoldItems++;
				}
			}
		}

		return new Statistics(totalSaleAmount, totalSoldItems, totalNotSoldItems);
	}

	// Controller to get bar chart data
	@GetMapping("/bar-chart")
	public List<BarChartData> getBarChartData(@RequestParam String month) {
		// Logic to calculate bar chart data for the given month
		// Here, let's assume some sample data
		List<BarChartData> barChartData = new ArrayList<>();
		barChartData.add(new BarChartData("0-100", 5));
		barChartData.add(new BarChartData("101-200", 10));
		barChartData.add(new BarChartData("201-300", 8));
		// Add more data as needed
		return barChartData;
	}

	// Controller to get pie chart data
	@GetMapping("/pie-chart")
	public List<PieChartData> getPieChartData(@RequestParam String month) {
		// Logic to calculate pie chart data for the given month
		// Here, let's assume some sample data
		List<PieChartData> pieChartData = new ArrayList<>();
		pieChartData.add(new PieChartData("Category X", 20));
		pieChartData.add(new PieChartData("Category Y", 10));
		// Add more data as needed
		return pieChartData;
	}
	
	
}
