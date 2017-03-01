package presets;

import java.io.File;
import java.util.Arrays;

public class PresetHelper {
	
	private ReadWriteTextFile rwt = new ReadWriteTextFile();
	
	public boolean[][] loadPreset(String path){
		boolean[][] preset = new boolean[50][50];
		String[] text = rwt.readLinesToArray(path, true);
		for(int i = 0; i<preset.length; i++){
			for(int r = 0; r<preset[i].length; r++){
				if(text[i].charAt(r) == '1'){
					preset[i][r] = true;
				}else{
					preset[i][r] = false;
				}
			}
		}
		return preset;
	}
	
	public void savePreset(String path, boolean[][] set){
		String[] writeArray = new String[set.length];
		Arrays.fill(writeArray, "2");
		for(int i = 0; i<set.length; i++){
			for(int r = 0; r<set[i].length; r++){
				if(set[i][r]){
					if(writeArray[i] != "2"){
						writeArray[i] += "1"; 
					}else{
						writeArray[i] = "1";
					}
				}else{
					if(writeArray[i] != "2"){
						writeArray[i] += "0";
					}else{
						writeArray[i] = "0";
					}
				}
			}
		}
		rwt.writeLineArray("/src/presetsTxtFiles/"+path+".txt", writeArray, true);
	}
	
	public String[] getPresetNames(){
		String workingDir = System.getProperty("user.dir");
		String[] names = new String[new File(workingDir+"/src/presetsTxtFiles/").list().length];
		File folder = new File(workingDir+"/src/presetsTxtFiles/");
		File[] listOfFiles = folder.listFiles();
		int i = 0;
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        names[i] = file.getName().substring(0, file.getName().length()-4);
		        i++;
		    }
		}
		return names;
	}
	
	public boolean[][] shiftArray(Direction di, boolean[][] set){
		boolean[][] shiftedArray = new boolean[set.length][set[0].length];
		if(di == Direction.LEFT){
			for(int i = 0; i<(set.length-1); i++){
				shiftedArray[i] = set[i+1];
			}
			for(int i = 0; i<set.length; i++){
				shiftedArray[set.length-1][i] = false;
			}
		}else if(di == Direction.RIGHT){
			for(int i = 1; i<set.length; i++){
				shiftedArray[i] = set[i-1];
			}
			for(int i = 0; i<set.length; i++){
				shiftedArray[0][i] = false;
			}
		}else if(di == Direction.UP){
			for(int i = 0; i<set.length; i++){
				for(int r = 0; r<(set.length-1); r++){
					shiftedArray[i][r] = set[i][r+1];
				}
			}
			for(int i = 0; i<set.length; i++){
				shiftedArray[i][set.length-1] = false;
			}
		}else if(di == Direction.DOWN){
			for(int i = 0; i<set.length; i++){
				for(int r = 1; r<set[i].length; r++){
					shiftedArray[i][r] = set[i][r-1];
				}
			}
			for(int i = 0; i<set.length; i++){
				shiftedArray[i][0] = false;
			}
		}
		return shiftedArray;
	}
	
	public enum Direction{
		RIGHT,
		LEFT,
		UP,
		DOWN
	}
}
