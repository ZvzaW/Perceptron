import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class DataReader {
    static List<String> decisionAttributes;
    static final String testPath = "iris_test.txt";
    static final  String trainingPath = "iris_training.txt";
    public static void readFile(String path) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {

            Iris iris;
            Iris.IrisType type = null;
            List<Double> conditionalAttributes;

            String line;
            String[] parts;

            decisionAttributes = new ArrayList<>();

            for(Iris.IrisType value : Iris.IrisType.values())
                decisionAttributes.add(value.name());

            while ((line = br.readLine()) != null) {
                parts = line.replace(',','.').trim().split("[\\t\\s]+");


                    if (parts[parts.length-1].toLowerCase().contains("setosa"))
                        type = Iris.IrisType.SETOSA;
                    else
                        type = Iris.IrisType.NIE_SETOSA;


                conditionalAttributes = new ArrayList<>();

                for(int i = 0; i < parts.length-1; i++){
                    conditionalAttributes.add(Double.parseDouble(parts[i]));
                }

                iris = new Iris(conditionalAttributes, type);

                if (path.equals(trainingPath)) {
                    Iris.training_answers.add(iris);
                }else {
                    Iris.testData.add(iris);
                    Iris.toClassifyData.add(new Iris(conditionalAttributes, null));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
