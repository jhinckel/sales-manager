package model;

public enum EntityIdentifier {
    
    SALESMAN("001"),
    CUSTOMER("002"),
    SALES_DATA("003");
    
    private String identifier;
    
    private EntityIdentifier(String identifier) {
        this.identifier = identifier;    
    }
    
    private String getIdentifier() {
        return this.identifier;
    }
    
    public static EntityIdentifier valueOfIdentifier(String identifier) {
        for (EntityIdentifier ei : EntityIdentifier.values()) {
            if (ei.getIdentifier().equals(identifier.trim())) {
                return ei;
            }
        }
        return null;
    }

}
