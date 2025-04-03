import java.util.*;

public class GeneticAlgorithm {
    static int POPULATION_SIZE = 5;
    static int CHROMOSOME_LENGTH = 5;
    static double MUTATION_RATE = 0.2; // 20% chance of mutation
    static Random random = new Random();

    public static int fitnessFunction(int x) {
        return -x * x / 10 + 3 * x;
    }

    public static List<Integer> generatePopulation() {
        List<Integer> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(random.nextInt(29) + 2); // Range 2 to 30
        }
        return population;
    }

    public static List<Integer> calculateFitness(List<Integer> population) {
        List<Integer> fitnessValues = new ArrayList<>();
        for (int individual : population) {
            fitnessValues.add(fitnessFunction(individual));
        }
        return fitnessValues;
    }

    public static List<Integer> selectByRouletteWheel(List<Integer> population, List<Integer> fitnessValues) {
        int totalFitness = fitnessValues.stream().mapToInt(Integer::intValue).sum();
        List<Integer> selectedPopulation = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            int pick = random.nextInt(Math.max(totalFitness, 1)); // Avoid zero division
            int sum = 0;
            for (int j = 0; j < population.size(); j++) {
                sum += fitnessValues.get(j);
                if (sum > pick) {
                    selectedPopulation.add(population.get(j));
                    break;
                }
            }
        }
        return selectedPopulation;
    }

    public static int[] crossover(int parent1, int parent2) {
        String binary1 = String.format("%5s", Integer.toBinaryString(parent1)).replace(' ', '0');
        String binary2 = String.format("%5s", Integer.toBinaryString(parent2)).replace(' ', '0');

        int point = random.nextInt(CHROMOSOME_LENGTH - 1) + 1;
        int child1 = Integer.parseInt(binary1.substring(0, point) + binary2.substring(point), 2);
        int child2 = Integer.parseInt(binary2.substring(0, point) + binary1.substring(point), 2);

        return new int[]{Math.max(2, Math.min(30, child1)), Math.max(2, Math.min(30, child2))};
    }

    public static List<Integer> performCrossover(List<Integer> population) {
        List<Integer> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size() - 1; i += 2) {
            int[] offspring = crossover(population.get(i), population.get(i + 1));
            newPopulation.add(offspring[0]);
            newPopulation.add(offspring[1]);
        }
        if (population.size() % 2 == 1) {
            newPopulation.add(population.get(population.size() - 1));
        }
        return newPopulation;
    }

    public static int mutate(int individual) {
        if (random.nextDouble() < MUTATION_RATE) {
            int bitPosition = random.nextInt(CHROMOSOME_LENGTH);
            int mask = 1 << bitPosition;
            individual ^= mask; // Flip the bit
            individual = Math.max(2, Math.min(30, individual));
        }
        return individual;
    }

    public static List<Integer> performMutation(List<Integer> population) {
        List<Integer> mutatedPopulation = new ArrayList<>();
        for (int individual : population) {
            mutatedPopulation.add(mutate(individual));
        }
        return mutatedPopulation;
    }

    public static void main(String[] args) {
        List<Integer> population = generatePopulation();
        System.out.println("Initial Population: " + population);

        List<Integer> fitnessValues = calculateFitness(population);
        System.out.println("Fitness Values: " + fitnessValues);

        List<Integer> selectedPopulation = selectByRouletteWheel(population, fitnessValues);
        System.out.println("Selected Population: " + selectedPopulation);

        List<Integer> crossoverPopulation = performCrossover(selectedPopulation);
        System.out.println("Population after Crossover: " + crossoverPopulation);

        List<Integer> mutatedPopulation = performMutation(crossoverPopulation);
        System.out.println("Population after Mutation: " + mutatedPopulation);

        List<Integer> finalFitnessValues = calculateFitness(mutatedPopulation);
        System.out.println("Final Fitness Values: " + finalFitnessValues);
    }
}
