package model;

public class Customer extends Person {
    
    private String cnpj;
    private String businessArea;
    
    public Customer(String cnpj, String name, String businessArea) {
        super.setName(name);
        this.cnpj = cnpj;
        this.businessArea = businessArea;
    }
    
    @Override
    public String getId() {
        return this.getCnpj();
    }

    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getBusinessArea() {
        return businessArea;
    }
    
    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

}
