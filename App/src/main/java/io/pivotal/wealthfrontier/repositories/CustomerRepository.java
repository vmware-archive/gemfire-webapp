package io.pivotal.wealthfrontier.repositories;



import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;
import io.pivotal.wealthfrontier.domain.Customer;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by vupadhya on 2/23/16.
 */
public interface CustomerRepository extends CrudRepository<Customer,String> {

    @Query("select * from /Customer limit 5")
    Collection<Customer> getTopFiveCustomers();

    @Query("select * from /Customer where id in set('305-62-9903','925-92-2273','875-11-2831','507-51-6325','929-12-0500','091-89-5431')")
    Collection<Customer> getSelectedCustomers();

}
