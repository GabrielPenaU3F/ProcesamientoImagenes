package core.service;


public class RandomNumberGenerationService {

    public double generateExponentialNumber(double lambda) {

        double uniformNumber = Math.random();
        double expNumber = (-1/lambda)*(Math.log(uniformNumber));
        return expNumber;

    }

    public double generateRayleighNumber(double psi) {

        double uniformNumber = Math.random();
        double rayleighNumber = psi * Math.sqrt(-2*Math.log(1-uniformNumber));
        return rayleighNumber;

    }

    public double generateGaussianNumber(double mu, double sigma) {

        double firstUniformNumber = Math.random();
        double secondUniformNumber = Math.random();

        //This block is to make sure that none of the generated numbers is 0, so the logarithm will exist
        while (firstUniformNumber*secondUniformNumber == 0) {
            firstUniformNumber = Math.random();
            secondUniformNumber = Math.random();
        }

        double firstGaussianNumber = (Math.sqrt(-2*Math.log(firstUniformNumber)))*Math.cos(2*Math.PI*secondUniformNumber);
        double secondGaussianNumber = (Math.sqrt(-2*Math.log(firstUniformNumber)))*Math.sin(2*Math.PI*secondUniformNumber);

        //This I'm allowed to do because the sum of two gaussian variables is also gaussian
        double standardGaussianNumber = (firstGaussianNumber + secondGaussianNumber)/2;
        return standardGaussianNumber*sigma + mu;

    }
}
