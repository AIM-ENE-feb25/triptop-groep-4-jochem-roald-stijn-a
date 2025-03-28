package exercises.jochem.soex.strategy;

public class Main {
    public static void main(String[] args) {
        Woordenlijst woordenlijst = new Woordenlijst();

        System.out.println("Original list:");
        woordenlijst.print();

        // Set sorting strategy to BubbleSort
        woordenlijst.setSorteerStrategie(new BubbleSort());
        woordenlijst.sorteer();
        System.out.println("Sorted with BubbleSort:");
        woordenlijst.print();

        // Set sorting strategy to SelectionSort
        woordenlijst.setSorteerStrategie(new SelectionSort());
        woordenlijst.sorteer();
        System.out.println("Sorted with SelectionSort:");
        woordenlijst.print();
    }
}

