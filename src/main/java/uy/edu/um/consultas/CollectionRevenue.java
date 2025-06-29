package uy.edu.um.consultas;

public class CollectionRevenue implements Comparable<CollectionRevenue> {
    private int id;
    private double revenue;

    public CollectionRevenue(int id, double ingreso) {
        this.id = id;
        this.revenue = ingreso;
    }

    public int getId() {
        return id;
    }

    public double getRevenue() {
        return revenue;
    }

    @Override
    public int compareTo(CollectionRevenue otro) {
        return Double.compare(this.revenue, otro.revenue); // para max-heap
    }
}
