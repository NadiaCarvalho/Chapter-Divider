package chapter_divider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class ChapterDivider {

	String file;
	String nameFile;
	ArrayList<Chapter> chapters;

	public ChapterDivider(String file) {
		this.file = file;
	}

	/**
	 * Chapter Divider Constructor
	 * 
	 * @param args
	 */
	public ChapterDivider(String[] args) {
		this.file = args[0];
	}

	/**
	 * Main function
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		ChapterDivider chapterDivider;

		if (args.length < 1) {
			System.out.println("Run programm with: java ChapterDivider <fileToDivide> "
					+ "or insert the name of the file you want to divide \n");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter String");

			String filePath = br.readLine();
			chapterDivider = new ChapterDivider(filePath);

		} else
			chapterDivider = new ChapterDivider(args);

		chapterDivider.call();

	}

	private void call() {
		File file = new File(this.file);
		boolean exists = file.exists();
		boolean isDirectory = file.isDirectory();
		try {
			if (exists && isDirectory) {
				File[] listOfFiles = file.listFiles();
				for (File filer : listOfFiles) {
					if (filer.isFile()) {
						this.run(filer.getPath());
					}
				}
			} else
				this.run(this.file);

			System.out.println("End Of Convertion");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void run(String file) throws FileNotFoundException {

		String paths[] = file.split("/");
		String nameFile = paths[paths.length - 1].replaceAll(".txt", "");
		new File("files/" + nameFile + "_chapters").mkdirs();

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		StringBuilder sb = new StringBuilder();
		this.chapters = new ArrayList<Chapter>();

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
			String[] chaptersS = divide(sb.toString());

			for (int i = 0; i < chaptersS.length; i++)
				this.chapters.add(new Chapter(chaptersS[i], i, nameFile));

			for (Iterator<Chapter> chapterIter = this.chapters.iterator(); chapterIter.hasNext();) {
				Chapter chapter = chapterIter.next();
				chapter.clean();
				chapter.export();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String[] divide(String string) {
		return string.split("(?=Chapter)");
	}

}
