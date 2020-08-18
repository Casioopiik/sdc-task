package com.tasks.sdcTask;

import org.apache.commons.math3.primes.Primes;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

public class FileUtils {
	public static List<Integer> getPrimesFromFile(File xlsxFile) {
		List<Integer> primeNumbers = new LinkedList<>();

		try (FileInputStream fis = new FileInputStream(xlsxFile)) {
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int column = 1;

			for (Row row : sheet) {
				Cell cell = row.getCell(column);
				String value = cell.getStringCellValue();
				if (isValidNumber(value)) {
					int number = Integer.parseInt(value.trim());
					primeNumbers.add(number);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Nebyl nalezen zvolený soubor. Ukončuji program");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return primeNumbers;
	}

	public static boolean saveToFile(File file, List<Integer> primeNumbers) {
		boolean isCompleted;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			for (Integer number : primeNumbers) {
				String line = number.toString() + "\n";
				Files.write(file.toPath(), line.getBytes(), StandardOpenOption.APPEND);
			}
			isCompleted = true;
		} catch (IOException e) {
			System.out.println(String.format("Nepodařilo se vytvořit soubor s daty. Důvod: \n %s", e.getMessage()));
			isCompleted = false;
		}


		return isCompleted;
	}


	private static boolean isValidNumber(String value) {
		return value.trim().matches("[0-9]+") && Primes.isPrime(Integer.parseInt(value.trim()));
	}
}
