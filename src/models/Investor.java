package models;

public class Investor {
    private String name;
    private String email;
    private double budget;

    public Investor(String name, String email, double budget) {
        this.name = name;
        this.email = email;
        this.budget = budget;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getBudget() {
        return budget;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    // Optional: toString() for debugging or display
    @Override
    public String toString() {
        return "Investor{name='" + name + "', email='" + email + "', budget=" + budget + "}";
    }
}
