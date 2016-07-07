package io.pivotal.wealthfrontier.functions;

import io.pivotal.wealthfrontier.domain.Alert;
import io.pivotal.wealthfrontier.domain.AlertDetail;

import java.util.Properties;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.FunctionException;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.Struct;
import com.gemstone.gemfire.cache.query.TypeMismatchException;

public class IRAContributionCheckerFunction extends FunctionAdapter implements Declarable {


	private static final long serialVersionUID = 1L;

    private static final String ALERT_REGION = "Alert";

    public static final String ID = "ira-contribution";

    private transient Cache cache = CacheFactory.getAnyInstance();
    private transient RegionFunctionContext context;
    private static LogWriter log;
    private Region<String, Alert> alertRegion;


    static {
        log = CacheFactory.getAnyInstance().getDistributedSystem().getLogWriter();
    }

    public void execute(FunctionContext fc) {
        if (!(fc instanceof RegionFunctionContext)) {
            throw new FunctionException(
                    "This is a data aware function, and has to be called using FunctionService.onRegion.");
        }

        log.info("I am in the " + ID + " function");
        context = (RegionFunctionContext) fc;

        alertRegion = cache.getRegion(ALERT_REGION);

        try {
            prepareAndExecuteQuery();
            String results = "Successfully checked for expiring loans";
            context.getResultSender().lastResult(results);
        } catch (Exception e) {
            StringBuffer sb = new StringBuffer();
            if (e.getMessage() != null) {
                sb.append(e.getMessage() + "\n");
            }
            for (StackTraceElement ste : e.getStackTrace()) {
                sb.append(ste.toString());
                sb.append("\n");
            }
            context.getResultSender().lastResult(sb.toString());
        }
    }

    public void prepareAndExecuteQuery()
            throws FunctionDomainException, TypeMismatchException,
            NameResolutionException, QueryInvocationTargetException {


    	String query = "SELECT DISTINCT c.id, p.deposit_pct_ira_paid FROM /Customer c, c.portfolio p "
        		+ "WHERE p.product_category = 'deposit_account' AND p.deposit_pct_ira_paid = false";

        log.info("Query is" + query);

        SelectResults<?> querySelectResults = QueryHelper.executeQuery(query, cache);

        if (querySelectResults == null) {
            log.info("querySelectResults is null");
        }

        log.info("result is of type: " + querySelectResults.getClass().toString());
        log.info("result is of size: " + querySelectResults.size());

        processQueryResult(querySelectResults);

    }

    public void processQueryResult(SelectResults<?> querySelectResults) {

    	for (Object o : querySelectResults.asList()) {

            Struct s = (Struct) o;
            String customerId = (String) s.get("id"); // get id from the Struct
            registerAlert(customerId);
        }

    }

    private void registerAlert(String id) {

    	String alertMessage = "IRA is Underfunded";

        Alert alert = alertRegion.get(id);

        AlertDetail alertDetail= new AlertDetail();
        alertDetail.setAlertMessage(alertMessage);

        if( alert == null) {
            alert = new Alert();
            alert.setCustomerId(id);

            alert.addAlertDetail(alertDetail);

        } else {
            alert.addAlertDetail(alertDetail);
        }
        alertRegion.put(id,alert);
    }


    public String getId() {
        return ID;
    }

    public boolean optimizeForWrite() {
        return true;
    }

    public boolean hasResult() {
        return true;
    }

    public boolean isHA() {
        return true;
    }

    public void init(Properties props) {

    }

}
