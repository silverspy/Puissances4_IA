public class Jeux {
    String[][] plateaux = new String[6][7];

    public Jeux(String[][] plateaux) {
        this.plateaux = plateaux;

    }

    public String[][] getPlateaux() {
        return plateaux;
    }

    public boolean gagnerpartie() {
        for (String[] c : plateaux) {
            for (int i = 0; i < plateaux.length; i++) {
                for (int j = 0; j < plateaux[i].length; j++) {
                    if (plateaux[i][j] != " ") {

                    }
                }
            }
        }
        return false;
    }
}
    
