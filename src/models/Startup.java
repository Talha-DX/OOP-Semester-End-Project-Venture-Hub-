package models;

public class Startup {
    private int id;
    private String founderName;
    private String startupName;
    private String industry;
    private String model;
    private String description;
    private String website;
    private String email;
    private String password;

    // Constructors
    public Startup() {
    }

    public Startup(int id, String founderName, String startupName, String industry, String model,
                   String description, String website, String email, String password) {
        this.id = id;
        this.founderName = founderName;
        this.startupName = startupName;
        this.industry = industry;
        this.model = model;
        this.description = description;
        this.website = website;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFounderName() {
        return founderName;
    }

    public void setFounderName(String founderName) {
        this.founderName = founderName;
    }

    public String getStartupName() {
        return startupName;
    }

    public void setStartupName(String startupName) {
        this.startupName = startupName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Optional: toString() for debugging
    @Override
    public String toString() {
        return "Startup{" +
                "id=" + id +
                ", founderName='" + founderName + '\'' +
                ", startupName='" + startupName + '\'' +
                ", industry='" + industry + '\'' +
                ", model='" + model + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
