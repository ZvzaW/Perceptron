import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Main {
    static volatile boolean flag = false;
    static volatile boolean working = true;

    public static void main(String[] args) {

        DataReader.readFile(DataReader.testPath);
        DataReader.readFile(DataReader.trainingPath);

        Scanner scanner = new Scanner(System.in);
        String answer;


        System.out.println("Welcome!");

        Perceptron perceptron = new Perceptron(Iris.training_answers);
        perceptron.train();

        int correct = 0;
        int wrong = 0;

        for (Iris iris : Iris.toClassifyData)
            Perceptron.classify(iris);

        for(int i = 0; i < Iris.testData.size(); i++){
            if (Iris.testData.get(i).getType() == Iris.toClassifyData.get(i).getType())
                correct++;
            else
                wrong++;
        }

        double accuracy = (double) correct / (double) (correct + wrong) * 100;
        BigDecimal bd = new BigDecimal(Double.toString(accuracy));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        accuracy = bd.doubleValue();

        System.out.println("Correct answers: " + correct);
        System.out.println("Wrong answers: " + wrong);
        System.out.println("Accuracy of the test: " + accuracy + "%");

        //MAIN LOOP
        do{
            //OPTIONS LOOP
            do {
                System.out.println();
                System.out.println("Choose option (1-2)" + '\n' +
                                   "1. Input own data of iris you want to classify" + '\n' +
                                   "2. Close the program");
                answer = scanner.nextLine();
            }while (!(answer.equals("1") || answer.equals("2")));

            //ANSWER
            switch (answer) {

                //OWN IRIS
                case "1" -> {
                    OptionalInt maxValue = Iris.training_answers.stream().mapToInt(iris -> iris.getConditionalAttributes().size()).max();
                    int howManyValues = maxValue.getAsInt() - 1;

                    List<Double> attributes = new ArrayList<>();
                    String[] parts;

                    //FOR VALUES LOOP
                    do {
                        flag = false;

                        System.out.println("Input " + howManyValues +
                                           " values of attributes in format: double double double double ..." + '\n' +
                                           "for example: 1.0 2.0 3.0 4.0");

                        answer = scanner.nextLine();
                        parts = answer.replace(',', '.').split(" ");

                        try {
                            if (parts.length != howManyValues)
                                throw new NumberFormatException();

                            for (String part : parts)
                                attributes.add(Double.parseDouble(part));

                        } catch (NumberFormatException n) {
                            System.out.println("Wrong format, try again!");
                            flag = true;
                        }
                    } while (flag);


                    Iris newIris = new Iris(attributes, null);
                    Perceptron.classify(newIris);

                    System.out.println("Your iris " + newIris.getConditionalAttributes() +
                                       " was classified as " + newIris.getType());

                }

                //END OF THE PROGRAM
                case "2" -> working = false;

            }
        }while(working);
    }
}
