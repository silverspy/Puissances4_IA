import java.io.*;

public class FileManipulator {
    private String url;
    private String[][] plateau;

    FileManipulator(String url) throws IOException {
        this.url = url;
        plateau = new String[6][7];
        for(int i = 0; i<6;i++){
            for(int y = 0;y<7;y++){
                plateau[i][y]= " ";
            }
        }

        BufferedReader br = new BufferedReader(new FileReader(url));
        String line;

        int i = 0;
        int y = 0;
        while ((line = br.readLine()) != null) {
            for (String e : line.split("")) {
                plateau[i][y] = e;
                y++;
            }
            i++;
            y = 0;
        }
    }

    public String[][] getPlateau(){
        return plateau;
    }

    public void setPlateau(String[][] newPlateau){
        plateau = newPlateau;
    }

    public void writeFile(){
        StringBuilder plat = new StringBuilder();
        for(String[] line:plateau){
            for(String caractere: line){
                plat.append(caractere);
            }
            plat.append("\n");
        }
        File f = new File(url);
        try{
            FileWriter fw = new FileWriter (f);
            fw.write(plat.toString());
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try{
            FileManipulator f = new FileManipulator("./plateau.txt");
            f.plateau[0][0] = "O";
            f.writeFile();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}