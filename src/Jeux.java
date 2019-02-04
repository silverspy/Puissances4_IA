public class Jeux {
    String[][] plateaux = new String[7][6];

    public Jeux(String[][] plateaux) {
        this.plateaux = plateaux;

    }

    public String[][] getPlateaux() {
        return plateaux;
    }

    public boolean gagnerpartie(){

        return false;
    }
}
