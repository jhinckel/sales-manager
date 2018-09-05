package report;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Customer;
import model.Sale;
import model.Salesman;

public class SalesReportStrategy implements ReportStrategy {

    @SuppressWarnings("unchecked")
    @Override
    public Report buildReport(Map<String, Object> entitiesMap) {
        Map<String, Customer> customers = (Map<String, Customer>) entitiesMap.get(Customer.class.getName());
        Map<String, Salesman> salesmen = (Map<String, Salesman>) entitiesMap.get(Salesman.class.getName());
        Map<String, Sale> sales = (Map<String, Sale>) entitiesMap.get(Sale.class.getName());
        Map<String, BigDecimal> salesBySalesman = new HashMap<>();
        SalesReport salesReport = new SalesReport();        
        BigDecimal mostExpensiveSaleValue = BigDecimal.ZERO;
        Sale mostExpensiveSale = null;
        
        for (Sale sale : sales.values()) {
            BigDecimal salesValue = salesBySalesman.get(sale.getSalesmanName());

            if (sale.getTotal().compareTo(mostExpensiveSaleValue) > 0) {
                mostExpensiveSaleValue = sale.getTotal();
                mostExpensiveSale = sale;
            }
            if (salesValue == null) {
                salesValue = BigDecimal.ZERO;
            }
            salesValue = salesValue.add(sale.getTotal());
            salesBySalesman.put(sale.getSalesmanName(), salesValue);
        }
        salesReport.setCustomers(customers.size());
        salesReport.setSalesmen(salesmen.size());
        salesReport.setMostExpensiveSaleId(mostExpensiveSale.getId());
        salesReport.setWorstSalesmen(getWorstSalesman(salesBySalesman, salesmen));
 
        return salesReport;
    }
    
    private List<String> getWorstSalesman(Map<String, BigDecimal> salesBySalesman, Map<String, Salesman> salesmen) {
        List<String> worstSalesmen = new LinkedList<>();
        BigDecimal worstSalesmanValue = getWorstSalesmanValue(salesBySalesman.values());
        boolean hasSalesmanWithoutSell = salesBySalesman.size() != salesmen.size();
        
        for (Iterator<Salesman> itSalesman = salesmen.values().iterator(); itSalesman.hasNext();) {
            Salesman salesman = itSalesman.next();            
            BigDecimal sales = salesBySalesman.get(salesman.getName());

            if (sales == null) {
                worstSalesmen.add(salesman.getName());
            } else if (!hasSalesmanWithoutSell && (sales.compareTo(worstSalesmanValue) <= 0)) {
                worstSalesmen.add(salesman.getName());
            }            
        }
        return worstSalesmen;
    }
    
    private BigDecimal getWorstSalesmanValue(Collection<BigDecimal> salesValue) {
        BigDecimal worstValue = null;
        
        for (BigDecimal saleValue : salesValue) {
            if (worstValue == null) {
                worstValue = saleValue;
            } else if (saleValue.compareTo(worstValue) < 0) {
                worstValue = saleValue;
            }
        }
        return worstValue;
    }

}
