package report;

import java.util.List;

public class SalesReport implements Report {
    
    private int customers;
    private int salesmen;
    private String mostExpensiveSaleId;
    private List<String> worstSalesmen;
    
    public int getCustomers() {
        return customers;
    }
    
    public void setCustomers(int customers) {
        this.customers = customers;
    }
    
    public int getSalesmen() {
        return salesmen;
    }
    
    public void setSalesmen(int salesmen) {
        this.salesmen = salesmen;
    }
    
    public String getMostExpensiveSaleId() {
        return mostExpensiveSaleId;
    }

    public void setMostExpensiveSaleId(String mostExpensiveSaleId) {
        this.mostExpensiveSaleId = mostExpensiveSaleId;
    }

    public List<String> getWorstSalesmen() {
        return worstSalesmen;
    }

    public void setWorstSalesmen(List<String> worstSalesmen) {
        this.worstSalesmen = worstSalesmen;
    }

    @Override
    public String getReportData() {
        return this.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Quantidade de Clientes: ").append(getCustomers()).append("\n");
        sb.append("Quantidade de Vendedores: ").append(getSalesmen()).append("\n");
        sb.append("ID da Venda de valor mais alto: ").append(getMostExpensiveSaleId()).append("\n");
        sb.append("Nome do Vendedor que menos vendeu: ").append(getWorstSalesmen().toString()).append("\n");

        return sb.toString();
    }

}
