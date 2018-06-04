/**
 * 
 */
package chapter_divider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author nadiacarvalho
 *
 */
public class Chapter {

	String body;
	int number;
	String name;

	/**
	 * 
	 */
	public Chapter(String chapterCont, int number, String name) {
		this.body = chapterCont;
		this.number = number;
		this.name = name;
	}

	public void clean() {
		String[] parts = body.split("(?<=\n\n)");
		this.body = "";
		for (int i = 0; i < parts.length; i++) {
			String p;
			if (parts[i].contains("Chapter"))
				p = "<h1> " + parts[i].replace("\n\n", "") + " </h1>\n\n";
			else if (parts[i].contains("Prologue") || parts[i].contains("Epilogue"))
				p = "<h2> " + parts[i].replace("\n\n", "") + " </h2>\n\n";
			else if (!parts[i].contains("<h1>") && !parts[i].contains("<h3>"))
				p = "<p style=\"text-align: justify;\">" + parts[i].replace("\n\n", "") + " </p>\n\n";
			else
				p = parts[i];
			parts[i] = p;
			this.body += parts[i];
		}
	}

	public void export() {

		String header = "<?xml version='1.0' encoding='utf-8'?>\n" + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
				+ "<head>\n<title>Chapter " + this.number + "</title>\n" + "</head>\n\n<body>\n";

		String toPrint = "";

		if (!this.body.contains(
				"<?xml version='1.0' encoding='utf-8'?>\n" + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"))
			toPrint = header + this.body + "</body>\n</html>";
		else
			toPrint = this.body + "</body>\n</html>";

		try {
			Files.write(Paths.get("files/" + this.name + "_chapters/Chapter" + number + ".html"),
					toPrint.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
