package io.pivotal.wealthfrontier.controllers;


import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import io.pivotal.wealthfrontier.domain.Customer;
import io.pivotal.wealthfrontier.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vupadhya on 2/24/16.
 */
@Controller
public class IndexController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ClientCache gemfireCache;

    @RequestMapping("/index")
    public String getIndex(){
        return "index";
    }

    @RequestMapping("/")
    public String query(Model model) {



        model.addAttribute("customers", customerRepository.getSelectedCustomers());


        return "index";
    }


}
