package io.pivotal.wealthfrontier.functions;

/**
 * Created by mross on 2/25/16.
 */
import io.pivotal.wealthfrontier.domain.Alert;
import io.pivotal.wealthfrontier.domain.AlertDetail;
import io.pivotal.wealthfrontier.domain.Customer;
import io.pivotal.wealthfrontier.domain.Product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

public class LoanMaturityCheckerFunction extends FunctionAdapter
        implements Declarable {

    private static final long serialVersionUID = 1L;

    private static final String ALERT_REGION = "Alert";
    private static final String CUSTOMER_REGION = "Customer";

    public static final String ID = "loan-maturity";

    private transient Cache cache = CacheFactory.getAnyInstance();
    private transient RegionFunctionContext context;
    private static LogWriter log;
    private Region<String, Alert> alertRegion;
    private Region<String, Customer> customerRegion;



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
        customerRegion = cache.getRegion(CUSTOMER_REGION);

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


        String query = "SELECT DISTINCT c.value.id,p.credit_mature_date,p.credit_amt  FROM /Customer.entries c, " +
                "c.value.portfolio p WHERE p.credit_mature_date != null";

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
            String creditMatureDate = (String) s.get("credit_mature_date"); // get credit_mature_date from the Struct
            String id = (String) s.get("id"); // get id from the Struct
            String creditAmt = "" + (Float)s.get("credit_amt");


            if(calculateLoanMaturity(creditMatureDate)) {
                registerAlert(id,creditMatureDate,creditAmt);

            }
        }

        log.info("result is of type: " + querySelectResults.getClass().toString());
        log.info("result is of size: " + querySelectResults.size());

    }

    public boolean calculateLoanMaturity( String credit_mature_date) {

    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	try {

    	    Date creditDate = sdf.parse(credit_mature_date);
    	    GregorianCalendar c =  new GregorianCalendar();
    	    c.setTime(sdf.parse(credit_mature_date));
    	    c.add(Calendar.MONTH, -3);  // add 3 months
    	    Date newDate = c.getTime();  // dt is now the new date

    	    Date currentDate = new Date();

    	    return (currentDate.compareTo(creditDate) < 0) && (currentDate.compareTo(newDate) > 0);

    	} catch(Exception e) {
    	    System.out.println("Data Formatter Exception" + e.getMessage());
    	}

    	return false;
    }

    private void registerAlert(String id, String creditMatureDate, String creditAmt) {

        String alertMessage = "Loan is maturing within 90 days";
        String matureAmountMsg = "Loan Amount: " + creditAmt + " million";
        String creditMatureDateMsg = "Matures: " + creditMatureDate;

        Alert alert = alertRegion.get(id);

        AlertDetail alertDetail= new AlertDetail();
        alertDetail.setAlertMessage(alertMessage);
        alertDetail.addDetails(matureAmountMsg);
        alertDetail.addDetails(creditMatureDateMsg);

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
