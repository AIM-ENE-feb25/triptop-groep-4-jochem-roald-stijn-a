package exercises.jochem.soex.strategy;

public class Woordenlijst {
    private String[] woorden = {"hond", "beer", "leeuw", "kat", "aap", "tijger", "olifant"};
    private ISorteerStrategie sorteerStrategie;

    public void print() {
        for (int i = 0; i < woorden.length; i++) {
            System.out.print(woorden[i] + " ");
        }
        System.out.println();
    }

    public void sorteer() {
        if (sorteerStrategie != null) {
            sorteerStrategie.sorteer(woorden);
        } else {
            System.out.println("No sorting strategy set.");
        }
    }

    public void setSorteerStrategie(ISorteerStrategie strategie) {
        this.sorteerStrategie = strategie;
    }
}