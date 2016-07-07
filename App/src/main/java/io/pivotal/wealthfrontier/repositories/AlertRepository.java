package io.pivotal.wealthfrontier.repositories;


import io.pivotal.wealthfrontier.domain.Alert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

/**
 * Created by vupadhya on 2/23/16.
 */
@RepositoryRestResource(collectionResourceRel = "alerts", path = "alerts")
public interface AlertRepository extends CrudRepository<Alert,String> {

}
