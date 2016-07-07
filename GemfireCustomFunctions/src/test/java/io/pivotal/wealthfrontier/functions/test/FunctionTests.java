package io.pivotal.wealthfrontier.functions.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by mross on 2/25/16.
 */
public class FunctionTests {


    @BeforeClass
    public  static void init() {

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


    @Test
    public void testLoanExpiringTest() {


        String maturityDate = "03/15/2016";
        Assert.assertTrue(calculateLoanMaturity(maturityDate));

    }

    @Test
    public void testLoanNotExpiringTest() {
        String nextYearMaturityDate = "07/07/2017";
        Assert.assertFalse(calculateLoanMaturity(nextYearMaturityDate));
    }
}
