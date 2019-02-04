public class Jeux {
    String[][] plateaux = new String[7][6];
    boolean joueur1;

    public Jeux(String[][] plateaux) {
        this.plateaux = plateaux;

    }

    public String[][] getPlateaux() {
        return plateaux;
    }

    public void addJeton(int col){
        for(int i = 0; i<6; i++){
            if (plateaux[i][col] == " ") {
                if(joueur1){
                    plateaux[i][col] = "O";
                }else{
                    plateaux[i][col] = "X";
                }
                break;
            }
        }
    }

    public

    public boolean gagnerpartie(){

        return false;
    }
}
