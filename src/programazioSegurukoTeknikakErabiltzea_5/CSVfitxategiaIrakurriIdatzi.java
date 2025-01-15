package programazioSegurukoTeknikakErabiltzea_5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVfitxategiaIrakurriIdatzi {
	private static final String FILE_NAME = "data.csv";

	public static void writeData(String name, String email) {
		try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
			fw.write(name + "," + email + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readData() {
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(",");
				System.out.println("Izena: " + fields[0] + ", Korreoa: " + fields[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		writeData("Jon", "jon@adibidea.com");
		writeData("Ane", "ane@adibidea.com");
		readData();
	}
}
