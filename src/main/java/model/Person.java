package model;

public abstract class Person {
    
    private String name;

    public abstract String getId();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
