package core.service.statistics;

import java.util.Random;

public class RandomNumberGenerationService {

    private Random random;

    public RandomNumberGenerationService(Random random) {
        this.random = random;
    }

    // y = (-1/lambda) * log(x)
    public double generateExponentialNumber(double lambda) {
        return (-1 / lambda) * (Math.log(randomBetweenZeroAndOne())); // Exponential Number
    }

    // y = psi * (-2 * log(1 - x))^(1/2)
    public double generateRayleighNumber(double psi) {
        return psi * Math.sqrt(-2 * Math.log(1 - randomBetweenZeroAndOne())); // Rayleigh Number
    }

    // y = (((y1 + y2) / 2) * sigma) + mu
    public double generateGaussianNumber(double mu, double sigma) {

        double firstUniformNumber = randomBetweenZeroAndOne();
        double secondUniformNumber = randomBetweenZeroAndOne();

        //This block is to make sure that none of the generated numbers is 0, so the logarithm will exist
        while (firstUniformNumber * secondUniformNumber == 0) {
            firstUniformNumber = Math.random();
            secondUniformNumber = Math.random();
        }

        // y1 = (-2 * log(x1) * cos(2 * pi * x2))^(1/2)
        double firstGaussianNumber = (Math.sqrt(-2 * Math.log(firstUniformNumber))) * Math.cos(2 * Math.PI * secondUniformNumber);

        // y1 = (-2 * log(x1) * sen(2 * pi * x2))^(1/2)
        double secondGaussianNumber = (Math.sqrt(-2 * Math.log(firstUniformNumber))) * Math.sin(2 * Math.PI * secondUniformNumber);

        //This I'm allowed to do because the sum of two gaussian variables is also gaussian
        double standardGaussianNumber = (firstGaussianNumber + secondGaussianNumber) / 2;
        return standardGaussianNumber * sigma + mu;
    }

    public Integer withUniformDistribution(Integer origin, Integer bound) {
        int delta = bound - origin;
        return (int) (randomBetweenZeroAndOne() * delta) + origin;
    }

    private double randomBetweenZeroAndOne() {
        return this.random.nextDouble();
    }

    public int[][] generateRandomGaussianMatrix(int width, int height, double mu, double sigma) {
        int[][] randomNumberMatrix = new int[width][height];
        for(int i=0; i < randomNumberMatrix.length; i++) {
            for (int j=0; j < randomNumberMatrix[i].length; j++) {

                double number = this.generateGaussianNumber(mu, sigma);
                randomNumberMatrix[i][j] = (int) (number*100); //This is a scale adjustment, just to avoid getting all zeros, in case the random number generated is < 1

            }
        }
        return randomNumberMatrix;
    }

    public int [][] generateRandomRayleighMatrix(int width, int height, double psi) {
        int randomNumberMatrix[][] = new int[width][height];
        for(int i=0; i < randomNumberMatrix.length; i++) {
            for (int j=0; j < randomNumberMatrix[i].length; j++) {

                double number = this.generateRayleighNumber(psi);
                randomNumberMatrix[i][j] = (int) (number*100); //This is a scale adjustment, just to avoid getting all zeros, in case the random number generated is < 1

            }
        }
        return randomNumberMatrix;
    }

    public int[][] generateRandomExponentialMatrix(int width, int height, double lambda) {
        int randomNumberMatrix[][] = new int[100][100];
        for(int i=0; i < randomNumberMatrix.length; i++) {
            for (int j=0; j < randomNumberMatrix[i].length; j++) {

                double number = this.generateExponentialNumber(lambda);
                randomNumberMatrix[i][j] = (int) (number*100); //This is a scale adjustment, just to avoid getting all zeros, in case the random number generated is < 1

            }
        }
        return randomNumberMatrix;
    }
}
