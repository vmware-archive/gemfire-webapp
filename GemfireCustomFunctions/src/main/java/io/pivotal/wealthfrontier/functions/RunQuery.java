package io.pivotal.wealthfrontier.functions;

/**
 *
 */

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.FunctionException;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;
import com.gemstone.gemfire.internal.util.StopWatch;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.TypeMismatchException;


public class RunQuery extends FunctionAdapter
        implements Declarable {

    private static final long serialVersionUID = 1L;

    public static final String ID = "run-query";

    private transient Cache cache = CacheFactory.getAnyInstance();
    private transient QueryService queryService = cache.getQueryService();
    private transient RegionFunctionContext context;
    private static LogWriter log;



    static {
        log = CacheFactory.getAnyInstance().getDistributedSystem().getLogWriter();
    }
    
    @SuppressWarnings("unchecked")
    public void execute(FunctionContext fc) {
        if (!(fc instanceof RegionFunctionContext)) {
            throw new FunctionException(
                    "This is a data aware function, and has to be called using FunctionService.onRegion.");
        }

        log.info("I am in the " + ID + " function");
        context = (RegionFunctionContext) fc;
        List<Object> arguments = (List<Object>) context.getArguments();
        String query = (String) arguments.get(0);


        try {
            List<String> queryResults = execute(query);
            context.getResultSender().lastResult(queryResults);
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

    public List<String> execute(String query)
            throws FunctionDomainException, TypeMismatchException,
            NameResolutionException, QueryInvocationTargetException {


    /*
     * get all availabilities that match the criteria //
     */

        SelectResults<String> querySelectResults = executeQuery(
                query);

        if (querySelectResults == null) {
            log.info("querySelectResults is null");
            List<String> nullAnswer = new ArrayList<String>();
            nullAnswer.add("the returned answer from the query was null");
            return nullAnswer;
        }
        log.info("result is of type: " + querySelectResults.getClass().toString());
        log.info("result is of size: " + querySelectResults.size());

        return querySelectResults.asList();

    }


    @SuppressWarnings("unchecked")
    private SelectResults<String> executeQuery(String sql)
            throws FunctionDomainException, TypeMismatchException,
            NameResolutionException, QueryInvocationTargetException {
        SelectResults<String> availableResults = null;

        Query query = queryService.newQuery(sql);
        log.info("\nExecuting query:\n\t" + sql);
        StopWatch sw = new StopWatch();
        sw.start();
        availableResults = (SelectResults<String>) query.execute();
        sw.stop();
            log.info("Query took " + sw.elapsedTimeMillis() + "ms: " + sql);
       return availableResults;
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
        // TODO Auto-generated method stub

    }
}
