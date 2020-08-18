package com.tasks.sdcTask;

import java.io.File;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		String endMsg = args.length == 0
				? "Nebyl zvolen žádný soubor. Ukončuji program."
				: selectAndSavePrimes(args);
		System.out.println(endMsg);
	}

	private static String selectAndSavePrimes(String[] args) {
		String path = args[0];
		String endMsg;

		if (isValidFile(args, path)) {
			File xlsxFile = new File(path);
			List<Integer> primeNumbers = FileUtils.getPrimesFromFile(xlsxFile);

			boolean isCompleted = savePrimeNumbers(primeNumbers);

			endMsg = isCompleted
					? "Program dokončen, čísla byla uložena do souboru export.txt"
					: "Nepodařilo se zapsat čísla do souboru, program bude ukončen.";
		} else {
			endMsg = "Nebyl zvolen soubor formátu .xlsx, Ukončuji program";
		}
		return endMsg;
	}

	private static boolean isValidFile(String[] args, String path) {
		return args.length != 0 && path.trim().matches("([a-zA-Z0-9\\s_.\\-()/:\\\\])+(.xlsx)$");
	}

	private static boolean savePrimeNumbers(List<Integer> primeNumbers) {
		File export = new File(System.getProperty("user.dir") + "/export.txt");
		return FileUtils.saveToFile(export, primeNumbers);
	}
}
