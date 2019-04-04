import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Jeux {
    String[][] plateaux = new String[6][7];
    HashSet<ArrayList<int[]>> plusGrosGroupe = new HashSet();

    public Jeux(String[][] plateaux) {
        this.plateaux = plateaux;

    }

    public String[][] getPlateaux() {
        return plateaux;
    }

    public ArrayList<int[]> getAllPossibilities(){
      ArrayList<int[]> allPossib=new ArrayList<>();
      for(String c:plateaux){
        for(int x=0;x<plateaux.length;x++){
          for(int y=0;y<plateaux[i].length;y++){

          }
        }
      }
    }

    public int[] bestMoveTurn(ArrayList<int[]> a){
      ArrayList<Integer> lsNoteCoup=new ArrayList<>();
      for(int[] coupPossible:a){
        lsNoteCoup.add(simulatePlay(coupPossible));
      }
      int taille=lsNoteCoup.size();
      if(taille>0){
        int max=lsNoteCoup[0];
        int index=0;
        for(int i=0;i<taille;i++){
          if(lsNoteCoup>max){
            max=lsNoteCoup[i];
            index=i;
          }
        }
      }
      return a[index];
    }

    public int bestMoveAdversory(ArrayList<int[] a){
      ArrayList<Integer> lsNoteCoup=new ArrayList<>();
      for(int[] coupPossible:a){
        simulatePlayAdversory(coupPossible);
      }
      int taille=lsNoteCoup.size();
      if(taille>0){
        int max=lsNoteCoup[0];
        int index=0;
        for(int i=0;i<taille;i++){
          if(lsNoteCoup>max){
            max=lsNoteCoup[i];
            index=i;
          }
        }
      }
      return a[index];
    }
    public void nbjetons() {
        for (String[] c : plateaux) {
            for (int i = 0; i < plateaux.length; i++) {
                for (int j = 0; j < plateaux[i].length; j++) {
                    if (plateaux[i][j] != " ") {
                        String joueur = plateaux[i][j];
                        for (int k = -4; k < 4; k++) {
                            ArrayList<int[]> paquetcol = new ArrayList<>(); // Col
                            if (plateaux[i + k][j] == joueur && plateaux.length < i+k) { //Ligne
                                paquetcol.add(new int[]{i, j});
                            } else {
                                if (!paquetcol.isEmpty()) {
                                    plusGrosGroupe.add(paquetcol);
                                }
                            }

                            ArrayList<int[]> paquetLig = new ArrayList<>(); // lig
                            if (plateaux[i][j + k] == joueur && plateaux[i].length < j+k) { //Ligne
                                paquetLig.add(new int[]{i, j});
                            } else {
                                if (!paquetLig.isEmpty()) {
                                    plusGrosGroupe.add(paquetLig);
                                }
                            }

                            ArrayList<int[]> paquetDiag1 = new ArrayList<>(); // diag1
                            if (plateaux[i + k][j + k] == joueur && plateaux[i].length < j+k && plateaux.length < i+k) { //Ligne
                                paquetDiag1.add(new int[]{i, j});
                            } else {
                                if (!paquetDiag1.isEmpty()) {
                                    plusGrosGroupe.add(paquetDiag1);
                                }
                            }

                            ArrayList<int[]> paquetDiag2 = new ArrayList<>(); // diag2
                            if (plateaux[i + k][j + k * - 1] == joueur && plateaux[i].length < j+k && plateaux.length < i+k) { //Ligne
                                paquetDiag2.add(new int[]{i, j});
                            } else {
                                if (!paquetDiag2.isEmpty()) {
                                    plusGrosGroupe.add(paquetDiag2);
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(plusGrosGroupe);
    }
}
