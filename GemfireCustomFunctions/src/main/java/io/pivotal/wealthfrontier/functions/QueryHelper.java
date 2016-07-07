package io.pivotal.wealthfrontier.functions;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.TypeMismatchException;
import com.gemstone.gemfire.internal.util.StopWatch;

public class QueryHelper {


	private static LogWriter log;

	static {
        log = CacheFactory.getAnyInstance().getDistributedSystem().getLogWriter();
    }


    public static SelectResults<?> executeQuery(String sql, Cache cache)
            throws FunctionDomainException, TypeMismatchException,
            NameResolutionException, QueryInvocationTargetException {
        SelectResults<?> availableResults = null;


        QueryService queryService = cache.getQueryService();
        Query query = queryService.newQuery(sql);
        log.info("\nExecuting query:\n\t" + sql);
        StopWatch sw = new StopWatch();
        sw.start();
        availableResults = (SelectResults<?>) query.execute();
        sw.stop();
        log.info("Query took " + sw.elapsedTimeMillis() + "ms: " + sql);
        return availableResults;
    }

}
