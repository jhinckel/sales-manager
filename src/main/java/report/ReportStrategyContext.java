package report;

import java.util.HashMap;
import java.util.Map;

public class ReportStrategyContext {
    
    private Map<String, ReportStrategy> strategyContext = new HashMap<>();

    public ReportStrategyContext() {
        this.strategyContext.put(SalesReportStrategy.class.getName(), new SalesReportStrategy());
    }
    
    public ReportStrategy getStrategy(Class<?> clazz) {
        return strategyContext.get(clazz.getName());
    }
    
}
