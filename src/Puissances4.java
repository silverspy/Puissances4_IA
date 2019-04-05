import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Puissances4 {
	String[][] plateaux;
	public Puissances4() {
		// TODO Auto-generated constructor stub
		this.plateaux=new String[6][7];
	}
	//ok
	public void exportGame() {
		String str="";
		for(int i=(this.plateaux.length-1);i>=0;i--) {
			for(int j=0;j<this.plateaux[i].length;j++) {
				str+=this.plateaux[i][j];
			}
			str+="\n";
		}
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("game.txt");
			fileWriter.write(str);
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//ok
	public  void changementJeux(String nomFile){
		Puissances4 p=new Puissances4();
		File f=new File(nomFile);
		BufferedReader br;
		ArrayList<String> tab=new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(f));
			String line;
			while((line=br.readLine())!=null) {
				tab.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.reverse(tab);
		String [] t= new String [6];
		for(int i=0;i<tab.size();i++) {
			t[i]=tab.get(i);
		}
		for(int y=0;y<this.plateaux.length;y++) {
			t[y]=t[y].replace("X", "D");
			t[y]=t[y].replace("O", "X");
			t[y]=t[y].replace("D", "O");
			char[] lineChar=t[y].toCharArray();
			for(int x=0;x<this.plateaux[y].length;x++) {
				this.plateaux[y][x]=String.valueOf(lineChar[x]);
			}
		}
	}
	//renvoie une arrayList des coups possibles 
	public ArrayList<int[]> Possibilities(){
		ArrayList<int[]> possibilities=new ArrayList<int[]>();
		if(!gameLost()) {
			for(int numCol=0;numCol<7;numCol++) {
				if(possibilityCol(numCol)!=-1) {
					int []t = {possibilityCol(numCol),numCol};
					possibilities.add(t);
				}
			}
		}
		return possibilities;
	}
	
public void play(int x,int y) {
		this.plateaux[x][y]="O";
}
//to test
public void bestPlay(String [][] plateauCourant,HashMap<int[],Integer> accuValue,int recursivityLevel,int[] branchKey) {
	if(recursivityLevel>6) {
		playBestScore(accuValue);
		return;
	}else {
		if(accuValue.containsValue(10)||accuValue.containsValue(9)) {
			playBestScore(accuValue);
			return;
		}
		if(accuValue.containsValue(-10)||accuValue.containsValue(-9)) {
			//remove keyBranch from map
		}else {
			if(recursivityLevel%2==0) {
				String[][] plateauAdv=simulatePlateauAdv(plateauCourant,accuValue,branchKey);
				bestPlay(plateauAdv,accuValue,(recursivityLevel-1),branchKey);
			}else {
				if(branchKey.equals(null)) {
					ArrayList<int[]> possibilities=Possibilities();
					HashMap<int[],Integer> mapPossibilities=bestPossibilities(this.plateaux,possibilities);
					String [][] plateauSimul;
					for(int[] key:mapPossibilities.keySet()) {
						plateauSimul=simulationCoup(this.plateaux,key);
						bestPlay(plateauSimul, mapPossibilities, (recursivityLevel-1), key);
					}
				}else {
					ArrayList<int[]> possibilities=Possibilities();
					HashMap<int[],Integer> mapPossibilities=bestPossibilities(plateauCourant,possibilities);
					String [][] plateauSimul;
					for(int[] key:mapPossibilities.keySet()) {
						plateauSimul=simulationCoup(plateauCourant,key);
						accuValue.put(branchKey, mapPossibilities.get(key));
						bestPlay(plateauSimul, accuValue, (recursivityLevel-1), branchKey);
					}
				}
			}
		}
	}
}

private String[][] simulationCoup(String[][] plateauAvant,int[] key) {
	// TODO Auto-generated method stub
	plateauAvant[key[0]][key[1]]="O";
	return plateauAvant;
}

//compter score adversaire + integrer son score dans l'hashmap et renvoyer le plateau une fois qu'il a jouer
private String[][] simulatePlateauAdv(String[][] plateauCourant, HashMap<int[], Integer> accuValue, int[] branchKey) {
	// TODO Auto-generated method stub
	return null;
}

//renvoie l'hashmap des 3 meilleurs coup du joueurs avec les points associés
private HashMap<int[], Integer> bestPossibilities(String[][] plateaux2, ArrayList<int[]> possibilities) {
	
	// TODO Auto-generated method stub
	return null;
}
//to do
//voir les directions dans evaluation coup plus bas
public int evaluationDirection(String[][] plateau,int[] coup,String direction) {
	boolean place=true;
	int nbJetonAligné=0;
	switch (direction) {
	case "GN":
		
		break;
	case "N":
		
		break;
	case "DN":
	
		break;
	case "D":
	
		break;
	case "DS":
	
		break;
	case "S":
	
		break;
	case "GS":
	
		break;
	case "G":
	
		break;
	}
	return nbJetonAligné;
	
}
//probleme de la place pour placer les pions une direction peut avoir 3 pions aligner mais pas de place pour un poser un 4 eme 
//solution faire en sorte que evaluation direction renvoie un tuple int boolean si deux boolean a faux alors pas de place
//donc on le supprime de la liste
//explication direction
//GN gauche Nord (x+1)(y-1)
//N Nord x+1
//DN droite Nord (x+1) y+1
//D droite y+1
//G gauche y-1
//GS gauche sud x-1 y-1
//ect
//peut etre rajouté un boolean pour indiquer si joueur ou non pour reutiliser code
public int evaluationPoint(String[][] plateau,int[] coup){
	HashMap<String, Integer> result=new HashMap<String, Integer>();
	int totdir1=1;
	int totdir2=1;
	int totdir3=1;
	int somme=0;
	int gau=evaluationDirection(plateau, coup, "G");
	int d=evaluationDirection(plateau, coup, "D");
	totdir1+=gau+d;
	result.put("G+D",totdir1);
	somme+=result.get("G+D");
	gau=evaluationDirection(plateau, coup, "GN");
	d=evaluationDirection(plateau, coup, "DS");
	totdir2+=gau+d;
	result.put("GS+DS",totdir2);
	somme+=totdir2;
	gau=evaluationDirection(plateau, coup, "DN");
	d=evaluationDirection(plateau, coup, "GS");
	totdir3+=gau+d;
	result.put("DN+GS",totdir3);
	somme+=result.get("DN+GS");
	switch (somme) {
	case 3:
		return 0;
	case 4:
		return 1;
	case 5:
		if(result.containsValue(3)) {
			return 3;
		}else {
			return 2;
		}
	case 6:
		if(result.containsValue(3)) {
			return 5;
		}else {
			return 4;
		}
	case 7:
		if(result.containsValue(1)) {
			return 7;
		}else {
			return 6;
		}
	case 8:
		return 8;
	case 9:
		return 9;
	default:
		if(result.containsValue(4)){
			return 10;
		}else {
			return 0;
		}
	}
}

//joue le meilleur score possible pour le joueur (valeur la plus elevé de la hashmap passé key[0] et key[1] a la fonction play qui se charge du reste
private void playBestScore(HashMap<int[], Integer> accuValue) {
	// TODO Auto-generated method stub
	
}
//determine si la partie est finie
private boolean gameLost() {
		// TODO Auto-generated method stub
		return false;
	}
//ok
private int possibilityCol(int numCol) {
	int index=-1;
	boolean lastOne=true;
	for(int i=0;i<6;i++) {
		if(this.plateaux[i][numCol].equals("-")&&lastOne==true) {
			index=i;
			lastOne=false;
		}
	}
	return index;
}

//pour le moment joue en random et le fichier de sortie est game.txt dans le repertoire courant
public static void main(String[] args) {
	Puissances4 p=new Puissances4();
	if (args.length!=1) {
		System.out.println("Veuillez indiquer le fichier txt necessaire pour jouer");
	}else {
		File jeuxPre=new File(args[0]);
		System.out.println(args[0]);
		System.out.println("premier int ordonnée (ligne) second abscisse (colone)");
		if(jeuxPre.exists()) {
			p.changementJeux(args[0]);
			ArrayList<int[]> possibility=p.Possibilities();
			for(int[] t:possibility) {
				System.out.println(Arrays.toString(t));
			}
			Random rand =new Random();
			int n=rand.nextInt(possibility.size());
			p.play(possibility.get(n)[0], possibility.get(n)[1]);
			p.exportGame();
		}else {
			System.out.println("Indiquer un fichier existant");
		}
	}
}

}
