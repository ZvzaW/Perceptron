import java.util.*;

public class Perceptron {
    public static double theta = Math.round(Math.random()*4 + 1);
    public static List<Double> weights = new ArrayList<>();
    private static volatile boolean wasWrong;
    public static double alfa = 0.2;
    double extraCoordinate = -1;
    private List<Iris> dataset;

    public Perceptron(List<Iris> dataset){

        OptionalInt maxSize = dataset.stream().mapToInt(iris -> iris.getConditionalAttributes().size()).max();
        double weigth;

        if (maxSize.isPresent()) {
            for (int i = 0; i < maxSize.getAsInt(); i++) {
                weigth = Math.round(Math.random()*4 + 1);
                weights.add(weigth);
            }
            weights.add(theta);
        }

        this.dataset = dataset;

        for (Iris iris : dataset)
            iris.getConditionalAttributes().add(extraCoordinate);
    }

    public void train(){
        int counter = 0;
            do {
                // System.out.println("nowapetla");
                wasWrong = false;

                for (Iris iris : dataset)
                    deltaAlgorithm(iris);

                counter ++;

                if (counter == 400)
                    break;

            }while(wasWrong);

        System.out.println(counter);
    }

    public static void deltaAlgorithm(Iris iris){
        double net = 0;
        int delta;
        int d;
        int y;

        if (iris.getType() == Iris.IrisType.SETOSA)
            d = 1;
        else d = 0;

        for(int i = 0; i < iris.getConditionalAttributes().size(); i++)
            net += weights.get(i) * iris.getConditionalAttributes().get(i);


        if (net >= 0)
            y = 1;
        else
            y = 0;

       // System.out.println(d==y);
        if (y != d){
            wasWrong = true;
            do {
                net = 0;
                delta = d - y;

                for (int i = 0; i < iris.getConditionalAttributes().size(); i++) {
                    weights.set(i, weights.get(i) + delta * alfa * iris.getConditionalAttributes().get(i));
                    net += weights.get(i) * iris.getConditionalAttributes().get(i);
                }

                if (net >= 0)
                    y = 1;
                else
                    y = 0;

            }while(y != d);
        }

        theta = weights.get(weights.size()-1);

    }

    public static void classify(Iris iris){
        double sum = 0;

           for (int i = 0; i < iris.getConditionalAttributes().size(); i++)
               sum += weights.get(i) * iris.getConditionalAttributes().get(i);

           if (sum >= theta)
               iris.setType(Iris.IrisType.SETOSA);
           else
               iris.setType(Iris.IrisType.NIE_SETOSA);
    }

}
