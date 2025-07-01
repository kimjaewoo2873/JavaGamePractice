import java.awt.Component;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Ranking {
	private static final String RANKING_FILE = getJarDirectory() + "/랭킹.txt";

	public HashMap<String, String> playerInfoMap = new HashMap<>();

	public Ranking(Component parent) {
		loadPlayerInfoFromFile();
	}

	private static String getJarDirectory() {
		try {
			String jarPath = Ranking.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			return new File(jarPath).getParent();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private void loadPlayerInfoFromFile() {
		try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(RANKING_FILE)))) {

			while (scanner.hasNext()) {
				String[] parts = scanner.nextLine().split(" ");
				if (parts.length >= 2) {
					String name = parts[0];
					String value = parts[1];
					playerInfoMap.put(name, value);
				} else {
					System.out.println("Invalid line format: " + String.join(" ", parts));
				}
			}
		} catch (Exception e) {
			System.out.println("Error reading the file: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public HashMap<String, String> getPlayerInfo() {
		return new HashMap<>(playerInfoMap);
	}

	public void addPlayerInfo(String newName, String newValue) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(RANKING_FILE, true))) {
			writer.write(newName + " " + newValue);
			writer.newLine();
			playerInfoMap.put(newName, newValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void modifyPlayerInfo(String playerName, String newValue) {
		if (playerInfoMap.containsKey(playerName)) {
			playerInfoMap.put(playerName, newValue);
			// 파일에도 
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(RANKING_FILE))) {
				for (Map.Entry<String, String> entry : playerInfoMap.entrySet()) {
					writer.write(entry.getKey() + " " + entry.getValue());
					writer.newLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("잘못된 플레이어: " + playerName);
		}
	}
}
