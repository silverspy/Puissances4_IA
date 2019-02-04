public class main {
    public static void main(String[] args) {
        String[][] jeux = new String[6][7];
        jeux[5][0] = "a";
        jeux[5][4] = "b";

        Jeux j = new Jeux(jeux);
        j.gagnerpartie();
    }
}
