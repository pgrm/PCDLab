package session0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 20.09.12
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private BufferedReader input;

    public Main(InputStream inputStream){
        input = new BufferedReader(new InputStreamReader(inputStream));
    }

    public static void main(String [] args) throws IOException {
        Main mainProgram = new Main(System.in);

        mainProgram.run();
    }

    private void run() throws IOException {
        RealNumber[] numbers = new RealNumber[getAmountOfNumbers()];

        fillNumbers(numbers);
        TreeNode<RealNumber> root = sortNumbers(numbers);
        displayNumbers(root);
    }

    private int getAmountOfNumbers() throws IOException {
        String line = this.input.readLine();
        int amount = Integer.parseInt(line);

        if (amount > 0)
            return amount;
        else
            throw new IllegalArgumentException("Please specify at least 1 faction.");
    }

    private void fillNumbers(RealNumber[] numbers) throws IOException {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = getNumberFromInput();
        }
    }

    private RealNumber getNumberFromInput() throws IOException {
        String line = input.readLine();
        String[] numberParts = line.split(" ");

        if (numberParts.length != 2)
            throw new IllegalArgumentException("Missing two integers separated by a <blank> \" \".");

        return new RealNumber(Integer.parseInt(numberParts[0]), Integer.parseInt(numberParts[1]));
    }

    private TreeNode<RealNumber> sortNumbers(RealNumber[] numbers) {
        TreeNode<RealNumber> root = new TreeNode<RealNumber>(numbers[0]);

        for (int i = 1; i < numbers.length; i++) {
            root.insert(numbers[i]);
        }

        return root;
    }

    private void displayNumbers(TreeNode<RealNumber> root) {
        root.traverseInOrder(new TreeNode.TraverseMap<RealNumber>() {
            @Override
            public void mapToNodeData(RealNumber data) {
                System.out.println(data);
            }
        });
    }
}
