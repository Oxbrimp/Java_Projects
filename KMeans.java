import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
@ Aria-Gharachorlou - 0.1 V
Feel free to do whatever you wish with the script! Let me know if anything is wrong or missing.
 */

public class KMeans {
    private List<double[]> dataPoints;
    private List<double[]> centroids;
    private List<List<double[]>> clusters;
    private List<double[]> initialCentroids;
    private static final int MAX_ITERATIONS = 1000; // Maximum number of iterations to prevent infinite loops

    public KMeans(List<double[]> dataPoints, List<double[]> initialCentroids) {
        this.dataPoints = dataPoints;
        this.initialCentroids = initialCentroids;
        this.centroids = new ArrayList<>();
        this.clusters = new ArrayList<>();
    }

    public void cluster() {
        initializeCentroids();
        boolean centroidsChanged;
        int iterations = 0;

        do {
            assignPointsToClusters();
            centroidsChanged = updateCentroids();
            iterations++;
            //System.out.println("Centroids updated: " + centroidsChanged);
            if (iterations >= MAX_ITERATIONS) {
                System.out.println("Reached maximum iterations.");
                break;
            }
        } while (centroidsChanged);

        // Debug output for clusters
        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster " + (i + 1) + ":");
            for (double[] point : clusters.get(i)) {
                System.out.println("(" + point[0] + ", " + point[1] + ")");
            }
        }
    }

    private void initializeCentroids() {
        centroids.addAll(initialCentroids);
        System.out.println("Initialized centroids: " + centroids);
    }

    private void assignPointsToClusters() {
        clusters.clear();
        for (int i = 0; i < centroids.size(); i++) {
            clusters.add(new ArrayList<>());
        }

        for (double[] point : dataPoints) {
            int closestCentroidIndex = getClosestCentroidIndex(point);
            clusters.get(closestCentroidIndex).add(point);
        }
    }

    private int getClosestCentroidIndex(double[] point) {
        int index = 0;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < centroids.size(); i++) {
            double distance = getEuclideanDistance(point, centroids.get(i));
            if (distance < minDistance) {
                minDistance = distance;
                index = i;
            }
        }

        return index;
    }

    private double getEuclideanDistance(double[] point1, double[] point2) {
        return Math.sqrt(Math.pow(point1[0] - point2[0], 2) + Math.pow(point1[1] - point2[1], 2));
    }

    private boolean updateCentroids() {
        boolean centroidsChanged = false;

        for (int i = 0; i < centroids.size(); i++) {
            double[] newCentroid = calculateCentroid(clusters.get(i));
            if (centroids.get(i)[0] != newCentroid[0] || centroids.get(i)[1] != newCentroid[1]) {
                centroids.set(i, newCentroid);
                centroidsChanged = true;
            }
        }

        return centroidsChanged;
    }

    private double[] calculateCentroid(List<double[]> cluster) {
        double[] centroid = new double[2];
        for (double[] point : cluster) {
            centroid[0] += point[0];
            centroid[1] += point[1];
        }
        centroid[0] /= cluster.size();
        centroid[1] /= cluster.size();
        return centroid;
    }

    public List<List<double[]>> getClusters() {
        return clusters;
    }
}
