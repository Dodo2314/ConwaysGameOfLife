package presets;

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
		String[] writeArray = new String[50];
		for(int i = 0; i<writeArray.length; i++){
			writeArray[i] = "2";
		}
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
}
