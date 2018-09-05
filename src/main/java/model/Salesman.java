package model;

import java.math.BigDecimal;

public class Salesman extends Person {
    
    private String cpf;
    private BigDecimal salary;
    
    public Salesman(String cpf, String name, BigDecimal salary) {
        super.setName(name);
        this.cpf = cpf;
        this.salary = salary;
    }
    
    @Override
    public String getId() {
        return this.getCpf();
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    
}
