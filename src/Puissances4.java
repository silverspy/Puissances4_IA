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
import java.util.List;
import java.util.Map.Entry;
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
	public ArrayList<int[]> Possibilities(String[][] plateau){
		ArrayList<int[]> possibilities=new ArrayList<int[]>();
		if(!gameLost()) {
			for(int numCol=0;numCol<7;numCol++) {
				if(possibilityCol(numCol,plateau)!=-1) {
					int []t = {possibilityCol(numCol,plateau),numCol};
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
public void bestPlay2(String [][] plateauCourant,HashMap<int[],Integer> accuValue,int recursivit�Level,int branchKey,List<int[]> listBranchKey) {
	if(branchKey==3&recursivit�Level>=6) {
		playBestScore(accuValue, this.plateaux);
		return;
	}else {
		if(accuValue.get(listBranchKey.get(branchKey))==-15||accuValue.get(listBranchKey.get(branchKey))==-12||accuValue.get(listBranchKey.get(branchKey))==-11||accuValue.get(listBranchKey.get(branchKey))==-10||accuValue.get(listBranchKey.get(branchKey))==-9) {
			bestPlay2(plateauCourant, accuValue, recursivit�Level, branchKey, listBranchKey);
		}
	}
}
public void bestPlay(String [][] plateauCourant,HashMap<int[],Integer> accuValue,int recursivityLevel,int[] branchKey) {
	System.out.println("\n");
	System.out.println("plateau : "+Arrays.deepToString(plateauCourant));
	System.out.println("Hashmap : "+Arrays.asList(accuValue));
	for (Entry<int[], Integer> entry : accuValue.entrySet()) {
	    System.out.println(Arrays.toString(entry.getKey())+" : "+entry.getValue());
	}
	System.out.println("branch test : "+Arrays.toString(branchKey));
	System.out.println("recursivity level : "+recursivityLevel);
	if(recursivityLevel>6) {
		playBestScore(accuValue,this.plateaux);
		return;
	}else {
		if(accuValue.containsValue(15)||accuValue.containsValue(9)||accuValue.containsValue(10)||accuValue.containsValue(11)||accuValue.containsValue(12)) {
			playBestScore(accuValue,this.plateaux);
			return;
		}
		if((accuValue.containsValue(-15)||accuValue.containsValue(-9)||accuValue.containsValue(-10)||accuValue.containsValue(-11)||accuValue.containsValue(-12))&&accuValue.size()>0) {
			accuValue.remove(branchKey);
			return;
		}else {
			if(recursivityLevel%2==0) {
				System.out.println("adversory turn");
				//get best possibility 
				//get score of best possibility
				//update plateau wuth best coup
				//bestPlay(plateauAdv)
				HashMap<int[], Integer> bestPossib=bestPossibAdv(plateauCourant);
				int [] coupAJouer=new int[2];
				int maxValueInMap=(Collections.max(accuValue.values()));
			    for (Entry<int[], Integer> entry : accuValue.entrySet()) {  // Itrate through hashmap
			        if (entry.getValue()==maxValueInMap) {
			        	coupAJouer=entry.getKey();     // Print the key with max value
			        }
			    }
				String[][] plateauAdv=simulatePlateauAdv(plateauCourant,coupAJouer);
				accuValue.replace(branchKey, bestPossib.get(coupAJouer));
				bestPlay(plateauAdv,accuValue,(recursivityLevel+1),branchKey);
			}else {
				if(branchKey.length==0) {
					System.out.println("ici");
					ArrayList<int[]> possibilities=Possibilities(this.plateaux);
					HashMap<int[],Integer> mapPossibilities=bestPossibilities(this.plateaux,possibilities);
					String [][] plateauSimul;
					for(int[] key:mapPossibilities.keySet()) {
						plateauSimul=simulationCoup(this.plateaux,key);
						recursivityLevel=1;
						bestPlay(plateauSimul, mapPossibilities, (recursivityLevel+1), key);
					}
				}else {
					System.out.println("player turn but not during the first turn");
					ArrayList<int[]> possibilities=Possibilities(plateauCourant);
					HashMap<int[],Integer> mapPossibilities=bestPossibilities(plateauCourant,possibilities);
					String [][] plateauSimul;
					for(int[] key:mapPossibilities.keySet()) {
						plateauSimul=simulationCoup(plateauCourant,key);
						accuValue.put(branchKey, mapPossibilities.get(key));
						bestPlay(plateauSimul, accuValue, (recursivityLevel+1), branchKey);
					}
				}
			}
		}
	}
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
    public ArrayList<int[]> Possibilities(String[][] plateau){
        ArrayList<int[]> possibilities=new ArrayList<int[]>();
        if(!gameLost()) {
            for(int numCol=0;numCol<7;numCol++) {
                if(possibilityCol(numCol,plateau)!=-1) {
                    int []t = {possibilityCol(numCol,plateau),numCol};
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
        System.out.println("\n");
        System.out.println("plateau : "+Arrays.deepToString(plateauCourant));
        System.out.println("Hashmap : "+Arrays.asList(accuValue));
        for (Entry<int[], Integer> entry : accuValue.entrySet()) {
            System.out.println(Arrays.toString(entry.getKey())+" : "+entry.getValue());
        }
        System.out.println("branch test : "+Arrays.toString(branchKey));
        System.out.println("recursivity level : "+recursivityLevel);
        if(recursivityLevel>6) {
            playBestScore(accuValue,this.plateaux);
            return;
        }else {
            if(accuValue.containsValue(10)||accuValue.containsValue(9)) {
                playBestScore(accuValue,this.plateaux);
                return;
            }
            if(accuValue.containsValue(-10)||accuValue.containsValue(-9)) {
                //remove keyBranch from map
                accuValue.remove(branchKey);
            }else {
                if(recursivityLevel%2==0) {
                    System.out.println("adversory turn");
                    //get best possibility
                    //get score of best possibility
                    //update plateau wuth best coup
                    //bestPlay(plateauAdv)
                    HashMap<int[], Integer> bestPossib=bestPossibAdv(plateauCourant);
                    int [] coupAJouer=new int[2];
                    int maxValueInMap=(Collections.max(accuValue.values()));
                    for (Entry<int[], Integer> entry : accuValue.entrySet()) {  // Itrate through hashmap
                        if (entry.getValue()==maxValueInMap) {
                            coupAJouer=entry.getKey();     // Print the key with max value
                        }
                    }
                    String[][] plateauAdv=simulatePlateauAdv(plateauCourant,coupAJouer);
                    accuValue.replace(branchKey, bestPossib.get(coupAJouer));
                    bestPlay(plateauAdv,accuValue,(recursivityLevel+1),branchKey);
                }else {
                    if(branchKey.length==0) {
                        System.out.println("ici");
                        ArrayList<int[]> possibilities=Possibilities(this.plateaux);
                        HashMap<int[],Integer> mapPossibilities=bestPossibilities(this.plateaux,possibilities);
                        String [][] plateauSimul;
                        for(int[] key:mapPossibilities.keySet()) {
                            plateauSimul=simulationCoup(this.plateaux,key);
                            recursivityLevel=1;
                            bestPlay(plateauSimul, mapPossibilities, (recursivityLevel+1), key);
                        }
                    }else {
                        System.out.println("player turn but not during the first turn");
                        ArrayList<int[]> possibilities=Possibilities(plateauCourant);
                        HashMap<int[],Integer> mapPossibilities=bestPossibilities(plateauCourant,possibilities);
                        String [][] plateauSimul;
                        for(int[] key:mapPossibilities.keySet()) {
                            plateauSimul=simulationCoup(plateauCourant,key);
                            accuValue.put(branchKey, mapPossibilities.get(key));
                            bestPlay(plateauSimul, accuValue, (recursivityLevel+1), branchKey);
                        }
                    }
                }
            }
        }
    }

    private HashMap<int[], Integer> bestPossibAdv(String[][] plateauCourant) {
        ArrayList<int[]> poss=Possibilities(plateauCourant);
        HashMap<int[], Integer> accuValue=new HashMap();
        HashMap<int[], Integer> res=new HashMap();
        for(int[] coup:poss) {
            accuValue.put(coup,evaluationPoint(plateauCourant, coup, false));
        }
        int [] coupAJouer=new int[2];
        int maxValueInMap=(Collections.max(accuValue.values()));
        for (Entry<int[], Integer> entry : accuValue.entrySet()) {  // Itrate through hashmap
            if (entry.getValue()==maxValueInMap) {
                coupAJouer=entry.getKey();     // Print the key with max value
            }
        }
        res.put(coupAJouer, maxValueInMap);
        // TODO Auto-generated method stub
        return res;
    }
    private String[][] simulationCoup(String[][] plateauAvant,int[] key) {
        // TODO Auto-generated method stub
        plateauAvant[key[0]][key[1]]="O";
        return plateauAvant;
    }

    //compter score adversaire + integrer son score dans l'hashmap et renvoyer le plateau une fois qu'il a jouer
    private String[][] simulatePlateauAdv(String[][] plateauCourant, int[] branchKey) {
        // TODO Auto-generated method stub
        plateauCourant[branchKey[0]][branchKey[1]]="X";
        return plateauCourant;
    }
    //renvoie l'hashmap des 3 meilleurs coup du joueurs avec les points associes
    private HashMap<int[], Integer> bestPossibilities(String[][] plateaux2, ArrayList<int[]> possibilities) {
        HashMap<int[], Integer> accu=new HashMap();
        ArrayList<Integer> res=new ArrayList<>();
        for(int[] coup:possibilities) {
            res.add(evaluationPoint(plateaux2, coup,true));
            System.out.println("eval point : "+Arrays.toString(coup)+" = "+evaluationPoint(plateaux2, coup, true));
        }
        int max=Collections.max(res);
        int indiceMax=res.indexOf(max);
        System.out.println(max);
        accu.put(possibilities.get(res.indexOf(max)), max);
        res.remove(indiceMax);
        possibilities.remove(indiceMax);
        max=Collections.max(res);
        indiceMax=res.indexOf(max);
        System.out.println(max);
        accu.put(possibilities.get(res.indexOf(max)), max);
        res.remove(indiceMax);
        possibilities.remove(indiceMax);
        max=Collections.max(res);
        System.out.println(max);
        accu.put(possibilities.get(res.indexOf(max)), max);
        System.out.println("dans bestPossibilities");
        for (Entry<int[], Integer> entry : accu.entrySet()) {
            System.out.println(Arrays.toString(entry.getKey())+" : "+entry.getValue());
        }
        return accu;
    }
    //to do
//voir les directions dans evaluation coup plus bas
    public int evaluationDirection(String[][] plateau,int[] coup,String direction) {

        boolean place=true;
        int nbJetonAligne=0;
        int lastX=coup[0];
        int lastY=coup[1];
        switch (direction) {
            case "GN":
                lastX=lastX+1;
                lastY=lastY-1;
                while(place==true) {
                    if(lastX<plateau.length&&lastY>=0) {
                        if(plateau[lastX][lastY].equals("O")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastX=lastX+1;
                            lastY=lastY-1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "N":
                lastX=lastX+1;
                while(place==true) {
                    if(lastX<plateau.length) {
                        if(plateau[lastX][lastY].equals("O")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastX=lastX+1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "DN":
                lastX=lastX+1;
                lastY=lastY+1;
                while(place==true) {
                    if(lastX<plateau.length&&lastY<7) {
                        if(plateau[lastX][lastY].equals("O")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastX=lastX+1;
                            lastY=lastY+1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "D":
                lastY=lastY+1;
                while(place==true) {
                    if(lastY<7) {
                        if(plateau[lastX][lastY].equals("O")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastY=lastY+1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "DS":
                lastX=lastX-1;
                lastY=lastY+1;
                while(place==true) {
                    if(lastX>=0&&lastY<7) {
                        if(plateau[lastX][lastY].equals("O")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastX=lastX-1;
                            lastY=lastY+1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "S":
                lastX=lastX-1;
                while(place==true) {
                    if(lastX>=0) {
                        if(plateau[lastX][lastY].equals("O")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastX=lastX-1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "GS":
                lastX=lastX-1;
                lastY=lastY-1;
                while(place==true) {
                    if(lastX>=0&&lastY>=0) {
                        if(plateau[lastX][lastY].equals("O")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastX=lastX-1;
                            lastY=lastY-1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "G":
                lastY=lastY-1;
                while(place==true) {
                    if(lastY>=0) {
                        if(plateau[lastX][lastY].equals("O")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastY=lastY-1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
        }
        return nbJetonAligne;
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
//peut etre rajout� un boolean pour indiquer si joueur ou non pour reutiliser code
public int evaluationPoint(String[][] plateau,int[] coup,boolean joueur){
	HashMap<String, Integer> result=new HashMap<String, Integer>();
	if(joueur) {
		int totdir1=1;
		int totdir2=1;
		int totdir3=1;
		int totdir4=1;
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
		gau=evaluationDirection(plateau, coup, "N");
		d=evaluationDirection(plateau, coup, "S");
		totdir4+=gau+d;
		result.put("NS", totdir4);
		somme+=totdir4;
		//System.out.println("somme : "+somme+" coup  : "+Arrays.toString(coup));
		if(result.containsValue(4)) {
			return 15;
		}else {
			switch (somme) {
			case 4:
				return 0;
			case 5:
				return 1;
			case 6:
				if(!result.containsValue(3))
					return 2;
				else
					return 5;
			case 7:
					if(result.containsValue(3))
						return 6;
					else
						return 4;
			case 8:
				if(result.containsValue(3))
					return 7;
				else
					return 4;
			case 9:
				if(result.containsValue(1)) {
					return 9;
				}else {
					return 8;
				}
			case 10:
				return 10;
			case 11:
				return 11;
			case 12:
				return 12;
			default:
				return 0;
			}
		}
	}else {
		int totdir1=1;
		int totdir2=1;
		int totdir3=1;
		int totdir4=1;
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
		gau=evaluationDirection(plateau, coup, "N");
		d=evaluationDirection(plateau, coup, "S");
		totdir4+=gau+d;
		result.put("NS", totdir4);
		somme+=totdir4;
		//System.out.println("somme : "+somme+" coup  : "+Arrays.toString(coup));
		if(result.containsValue(4)) {
			return -15;
		}else {
			switch (somme) {
			case 4:
				return 0;
			case 5:
				return -1;
			case 6:
				if(!result.containsValue(3))
					return -2;
				else
					return -5;
			case 7:
					if(result.containsValue(3))
						return -6;
					else
						return -4;
			case 8:
				if(result.containsValue(3))
					return -7;
				else
					return -4;
			case 9:
				if(result.containsValue(1)) {
					return -9;
				}else {
					return -8;
				}
			case 10:
				return -10;
			case 11:
				return -11;
			case 12:
				return -12;
			default:
				return 0;
			}
		}
	}
}

    private int evaluationDirection(String[][] plateau, int[] coup, String direction, boolean b) {
        // TODO Auto-generated method stub
        boolean place=true;
        int nbJetonAligne=0;
        int lastX=coup[0];
        int lastY=coup[1];
        switch (direction) {
            case "GN":
                lastX=lastX+1;
                lastY=lastY-1;
                while(place==true) {
                    if(lastX<plateau.length&&lastY>=0) {
                        if(plateau[lastX][lastY].equals("X")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastX=lastX+1;
                            lastY=lastY-1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "N":
                lastX=lastX+1;
                while(place==true) {
                    if(lastX<plateau.length) {
                        if(plateau[lastX][lastY].equals("X")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastX=lastX+1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "DN":
                lastX=lastX+1;
                lastY=lastY+1;
                while(place==true) {
                    if(lastX<plateau.length&&lastY<7) {
                        if(plateau[lastX][lastY].equals("X")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastX=lastX+1;
                            lastY=lastY+1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "D":
                lastY=lastY+1;
                while(place==true) {
                    if(lastY<7) {
                        if(plateau[lastX][lastY].equals("X")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastY=lastY+1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "DS":
                lastX=lastX-1;
                lastY=lastY+1;
                while(place==true) {
                    if(lastX>=0&&lastY<7) {
                        if(plateau[lastX][lastY].equals("X")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastX=lastX-1;
                            lastY=lastY+1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "S":
                lastX=lastX-1;
                while(place==true) {
                    if(lastX>=0) {
                        if(plateau[lastX][lastY].equals("X")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastX=lastX-1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "GS":
                lastX=lastX-1;
                lastY=lastY-1;
                while(place==true) {
                    if(lastX>=0&&lastY>=0) {
                        if(plateau[lastX][lastY].equals("X")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastX=lastX-1;
                            lastY=lastY-1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
            case "G":
                lastY=lastY-1;
                while(place==true) {
                    if(lastY>=0) {
                        if(plateau[lastX][lastY].equals("X")) {
                            nbJetonAligne=nbJetonAligne+1;
                            lastY=lastY-1;
                        }else {
                            place=false;
                        }
                    }else {
                        place=false;
                    }
                }
                break;
        }
        return nbJetonAligne;
    }
    //joue le meilleur score possible pour le joueur (valeur la plus eleve de la hashmap pass key[0] et key[1] a la fonction play qui se charge du reste
    private void playBestScore(HashMap<int[], Integer> accuValue, String[][] plateaux2) {
        // TODO Auto-generated method stub
        int [] coupAJouer=new int[2];
        int maxValueInMap=(Collections.max(accuValue.values()));
        for (Entry<int[], Integer> entry : accuValue.entrySet()) {  // Itrate through hashmap
            if (entry.getValue()==maxValueInMap) {
                coupAJouer=entry.getKey();     // Print the key with max value
            }
        }
        this.plateaux[coupAJouer[0]][coupAJouer[1]]="X";
        exportGame();
    }
    //determine si la partie est finie
    private boolean gameLost() {
        // TODO Auto-generated method stub
        return false;
    }
    //ok
    private int possibilityCol(int numCol,String[][] plateau) {
        int index=-1;
        boolean lastOne=true;
        for(int i=0;i<6;i++) {
            if(plateau[i][numCol].equals("-")&&lastOne==true) {
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
            System.out.println("premier int ordonnee (ligne) second abscisse (colone)");
            if(jeuxPre.exists()) {

                p.changementJeux(args[0]);
                ArrayList<int[]> possibility=p.Possibilities(p.plateaux);
                Random rand =new Random();
                HashMap<int[], Integer> accuValue=new HashMap<int[], Integer>();
                int[] key= {};
                p.bestPlay(p.plateaux, accuValue, 1, key);
                //int n=rand.nextInt(possibility.size());
                //p.play(possibility.get(n)[0], possibility.get(n)[1]);
                //p.exportGame();
            }else {
                System.out.println("Indiquer un fichier existant");
            }
        }
    }

}
