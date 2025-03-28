package exercises.jochem.soex.strategy;

public class SelectionSort implements ISorteerStrategie {

    @Override
    public void sorteer(String[] woorden) {
        for (int startpositie = 0; startpositie < woorden.length; startpositie++) {
            int positieMinimum = startpositie;
            for (int i = startpositie+1; i < woorden.length; i++) {
                if (woorden[positieMinimum].compareTo(woorden[i]) > 0) {
                    positieMinimum = i;
                }
            }
            String hulp = woorden[startpositie];
            woorden[startpositie] = woorden[positieMinimum];
            woorden[positieMinimum] = hulp;
        }
    }
}
