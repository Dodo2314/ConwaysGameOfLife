package presets;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class ReadWriteTextFile {

	public String readOneLine(String path, boolean workDir) {

		String line = "Could not read File";
		String workingDir = System.getProperty("user.dir");
		FileReader file;

		try {
			if (workDir == true) {
				file = new FileReader(workingDir + path);
			} else {
				file = new FileReader(path);
			}
			BufferedReader reader = new BufferedReader(file);
			line = reader.readLine();
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return line;
	}

	public String readOneLineAt(String path, int lineNumber, boolean workDir) {

		String line = "Could not read File";
		String workingDir = System.getProperty("user.dir");
		FileReader file;

		try {
			if (workDir == true) {
				file = new FileReader(workingDir + path);
			} else {
				file = new FileReader(path);
			}
			BufferedReader reader = new BufferedReader(file);
			for (int i = 0; i < lineNumber; i++) {
				reader.readLine();
			}
			line = reader.readLine();
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return line;
	}

	public String[] readLinesToArray(String path, boolean workDir) {

		String workingDir = System.getProperty("user.dir");
		FileReader file;

		String[] array = new String[1];
		try {
			if (workDir == true) {
				array = new String[countLines(workingDir + path) + 1];
			} else {
				array = new String[countLines(path) + 1];
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			if(workDir){
				file = new FileReader(workingDir + path);
				BufferedReader reader = new BufferedReader(file);

				for (int i = 0; i < array.length; i++) {
					array[i] = reader.readLine();
				}
				reader.close();
			}else{
				file = new FileReader(path);
				BufferedReader reader = new BufferedReader(file);

				for (int i = 0; i < array.length; i++) {
					array[i] = reader.readLine();
				}
				reader.close();
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}

		return array;
	}

	public void writeLine(String path, String line, boolean workDir) {

		String workingDir = System.getProperty("user.dir");

		try {
			if (workDir == true) {
				File data = new File(workingDir + path);
				FileWriter fileW = new FileWriter(data);
				BufferedWriter buffW = new BufferedWriter(fileW);
				
				if(data.exists()){}
				else{
					data.createNewFile();
				}
				
				buffW.write(line);
				buffW.newLine();
				buffW.close();
			} else {
				File data = new File(path);
				FileWriter fileW = new FileWriter(data);
				
				if(data.exists()){}
				else{
					data.createNewFile();
				}
				
				BufferedWriter buffW = new BufferedWriter(fileW);
				buffW.write(line);
				buffW.newLine();
				buffW.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeLineArray(String path, String[] array, boolean workDir) {

		String workingDir = System.getProperty("user.dir");
		try {
			if (workDir == true) {
				File data = new File(workingDir + path);
				FileWriter fileW = new FileWriter(data);
				BufferedWriter buffW = new BufferedWriter(fileW);
				
				if(data.exists()){}
				else{
					data.createNewFile();
				}
				
				for (int i = 0; i < array.length; i++) {
					buffW.write(array[i]);
					if(i < array.length - 1){
						buffW.newLine();
					}
				}

				buffW.close();
			} else {
				File data = new File(path);
				FileWriter fileW = new FileWriter(data);
				BufferedWriter buffW = new BufferedWriter(fileW);
				
				if(data.exists()){}
				else{
					data.createNewFile();
				}

				for (int i = 0; i < array.length; i++) {
					buffW.write(array[i]);
					if(i < array.length - 1){
						buffW.newLine();
					}
				}

				buffW.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeLineOverAt(String path, String line, int lineNr, boolean workDir){
		boolean addLines = false;
		lineNr--;
		String workingDir = System.getProperty("user.dir");
		try{
			if (workDir == true) {
				int countedLines = countLines(workingDir + path);
				
				File data = new File(workingDir + path);
				
				if(data.exists()){}
				else{
					data.createNewFile();
				}
				
				if(countedLines < lineNr){
					addLines = true;
				}
				String[] text = readLinesToArray(path, true);
				if(addLines == false){
					text[lineNr] = line;
					writeLineArray(path, text, true);
				}else{
					int addingLines = (lineNr+1) - countedLines;
					String[] text2 = new String[(text.length-1) + addingLines];
					for(int i = 0; i<text.length; i++){
						text2[i] = text[i];
					}
					if(addLines == true){
						for(int i = text.length-1; i<text2.length; i++){
							text2[i] = "";
						}
					}
					text2[lineNr] = line;
					writeLineArray(path, text2, true);
				}
			}else{
				int countedLines = countLines(path);
				
				File data = new File(path);
				
				if(data.exists()){}
				else{
					data.createNewFile();
				}
				
				if(countedLines < lineNr){
					addLines = true;
				}
				String[] text = readLinesToArray(path, false);
				if(addLines == false){
					text[lineNr] = line;
					writeLineArray(path, text, false);
				}else{
					int addingLines = (lineNr+1) - countedLines;
					String[] text2 = new String[(text.length-1) + addingLines];
					for(int i = 0; i<text.length; i++){
						text2[i] = text[i];
					}
					if(addLines == true){
						for(int i = text.length-1; i<text2.length; i++){
							text2[i] = "";
						}
					}
					text2[lineNr] = line;
					writeLineArray(path, text2, false);
				}
			}
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	
	public static int countLines(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}
}