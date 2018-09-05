package report;

import java.util.Map;

public interface ReportStrategy {

    Report buildReport(Map<String, Object> entitiesMap);
    
}
