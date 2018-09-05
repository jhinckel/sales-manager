package filewatcher;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import filesystem.FileReader;
import model.Customer;
import model.EntityIdentifier;
import model.Item;
import model.Person;
import model.Sale;
import model.Salesman;

public class ObjectReader {
    
    public Map<String, Object> readFileEntities(String path) throws IOException {
        List<String> fileContent = FileReader.readFileContent(Paths.get(path));
        Map<String, Object> objectMap = new HashMap<>();
        Map<String, Person> salesmen = new HashMap<>();
        Map<String, Person> customers = new HashMap<>();
        Map<String, Sale> sales = new HashMap<>();
        
        objectMap.put(Customer.class.getName(), customers);
        objectMap.put(Salesman.class.getName(), salesmen);
        objectMap.put(Sale.class.getName(), sales);
        
        for (String content : fileContent) {
            String[] data = removeNonPrintableCharacters(content).split(Delimiters.FIELD);
            EntityIdentifier entityIdentifier = EntityIdentifier.valueOfIdentifier(data[0]);

            if (entityIdentifier != null) {
                switch (entityIdentifier) {
                    case SALESMAN:
                        Person salesman = getSalesmanFromLineData(data);
                        salesmen.put(salesman.getId(), salesman);                        
                        break;
                    case CUSTOMER:
                        Person customer = getCustomerFromLineData(data);
                        customers.put(customer.getId(), customer);
                        break;
                    case SALES_DATA:
                        Sale sale = getSaleFromLineData(data);
                        sales.put(sale.getId(), sale);
                        break;
                    default:
                        break;
                }
            }
        }
        return objectMap;
    }
    
    private String removeNonPrintableCharacters(String text) {
        return text.replaceAll("\\p{C}", "");
    }
    
    private Sale getSaleFromLineData(String[] data) {
        Sale sale = new Sale(data[1].trim(), data[5].trim());
        Item saleItem = new Item(data[2].trim(), Integer.parseInt(data[3].trim()), new BigDecimal(data[4].trim()));
        
        sale.addItem(saleItem);

        return sale;
    }
    
    private Person getCustomerFromLineData(String[] data) {
        return new Customer(data[1].trim(), data[2].trim(), data[3].trim());
    }
    
    private Person getSalesmanFromLineData(String[] data) {
        return new Salesman(data[1].trim(), data[2].trim(), new BigDecimal(data[3].trim()));
    }

}
