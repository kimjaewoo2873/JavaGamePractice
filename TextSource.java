import java.awt.Component;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class TextSource {
	private Vector<String> wordVector = new Vector<>(30000); // 문자를 저장
	private final String fileName = getJarDirectory() + "/이름.txt"; // 파일 경로
	private Random random = new Random(); // Random number generator

	public TextSource(Component parent) {
		loadWordsFromFile();
	}

	private void loadWordsFromFile() {
		Scanner scanner = null;
		try {
			File file = new File(fileName);
			scanner = new Scanner(new BufferedReader(new FileReader(file)));
			while (scanner.hasNext()) {
				String word = scanner.nextLine();
				wordVector.add(word);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1); // 파일을 찾을 수 없으면 오류 메시지를 출력하고 종료합니다.
		} finally {
			if (scanner != null) {
				scanner.close(); // 스캐너를 닫습니다.
			}
		}
	}

	public void addWord(String newWord) {
		BufferedWriter writer = null;

		try {
			// FileWriter를 사용하여 파일에 쓰기를 수행합니다.
			writer = new BufferedWriter(new FileWriter(fileName, true));

			// 새로운 단어를 파일에 쓰고 개행합니다.
			writer.write(newWord);
			writer.newLine();

			// wordVector에도 새로운 단어를 추가합니다.
			wordVector.add(newWord);
		} catch (IOException e) {
			// IOException이 발생하면 예외를 출력합니다.
			e.printStackTrace();
		} finally {
			try {
				// BufferedWriter를 닫습니다.
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String next() {
		int n = wordVector.size();
		int index = (int)(Math.random()*n);
		return wordVector.get(index);
	}

	private String getJarDirectory() {
		try {
			String jarPath = TextSource.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			return new File(jarPath).getParent();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return "";
		}
	}
}
