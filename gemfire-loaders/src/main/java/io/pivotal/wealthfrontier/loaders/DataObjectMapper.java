package io.pivotal.wealthfrontier.loaders;

import io.pivotal.wealthfrontier.domain.Customer;
import io.pivotal.wealthfrontier.domain.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gemstone.gemfire.cache.Region;

@Component
public class DataObjectMapper {

	@Resource(name = "Customer")
    Region<String, Customer> customerRegion;

	@SuppressWarnings("resource")
	public void loadProductsToCustomers() {

		Customer customer = new Customer();
		String fileName = "Customer.tsv";

		// This will reference one line at a time
		String line = null;
		try {
			FileReader fileReader =
					new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader =
					new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				String [] attribtues = line.split("\\|");
				System.out.println(line);
				customer = (Customer)customerRegion.get(attribtues[0]);
				Product product = new Product();

				if (customer == null ) {
					System.out.println("Creating a new customer");
					customer = new Customer();
					customer.setId(attribtues[0]);
					customer.setFirstName(attribtues[1]);
					customer.setLastName(attribtues[2]);
					customer.setGender(attribtues[3]);
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					String dateInString = attribtues[4];
					Date date = sdf.parse(dateInString);
					customer.setDob(date);
					customer.setChildren(Integer.parseInt(attribtues[5]));
					customer.setStreetAddress(attribtues[6]);
					customer.setCity(attribtues[7]);
					customer.setState(attribtues[8]);
					customer.setZip(attribtues[9]);
					customer.setCountry(attribtues[14]);
					customer.setNetWorthInMillions(Float.parseFloat(attribtues[15]));
					customer.setSelfMade(Boolean.parseBoolean(returnTrueOrFalse(attribtues[16])));
					customer.setSourceOfWealth(attribtues[17]);
					customer.setInvestableAssestsMillionsUSD(Float.parseFloat(attribtues[18]));

					if (notNull(attribtues[20])) {
						product.setProduct(attribtues[20]);

						if (notNull(attribtues[19]) == true) {
							product.setProduct_category(attribtues[19]);
						}

						if (notNull(attribtues[21]) == true) {
							product.setCredit_duration(Integer.parseInt(attribtues[21]));
						}
						if (notNull(attribtues[22]) == true) {
							product.setCredit_rate(Float.parseFloat(attribtues[22]));
						}
						if (notNull(attribtues[23])) {
							product.setCredit_mature_date(attribtues[23]);
						}
						if (notNull(attribtues[24])) {
							product.setCredit_amt(Float.parseFloat(attribtues[24]));
						}
						if (notNull(attribtues[25])) {
							product.setInvestment_value(Float.parseFloat(attribtues[25]));
						}
						if (notNull(attribtues[26])) {
							product.setInvestment_pct_change_daily(Float.parseFloat(attribtues[26]));
						}
						if (notNull(attribtues[27])) {
							product.setInvestment_pct_change_monthly(Float.parseFloat(attribtues[27]));
						}
						if (notNull(attribtues[28])) {
							product.setInvestment_consumer_goods(Boolean.parseBoolean(returnTrueOrFalse(attribtues[28])));
						}
						if (notNull(attribtues[29])) {
							product.setInvestment_consumer_services(Boolean.parseBoolean(returnTrueOrFalse(attribtues[29])));
						}
						if (notNull(attribtues[30])) {
							product.setInvestment_telco(Boolean.parseBoolean(returnTrueOrFalse(attribtues[30])));
						}
						if (notNull(attribtues[31])) {
							product.setInvestment_healthcare(Boolean.parseBoolean(returnTrueOrFalse(attribtues[31])));
						}
						if (notNull(attribtues[32])) {
							product.setInvestment_industrials(Boolean.parseBoolean(returnTrueOrFalse(attribtues[32])));
						}
						if (notNull(attribtues[33])) {
							product.setInvestment_financials(Boolean.parseBoolean(returnTrueOrFalse(attribtues[33])));
						}
						if (notNull(attribtues[34])) {
							product.setInvestment_tech(Boolean.parseBoolean(returnTrueOrFalse(attribtues[34])));
						}
						if (notNull(attribtues[35])) {
							product.setInvestment_basic_materials(Boolean.parseBoolean(returnTrueOrFalse(attribtues[35])));
						}
						if (notNull(attribtues[36])) {
							product.setInvestment_oil_gas(Boolean.parseBoolean(returnTrueOrFalse(attribtues[36])));
						}
						if (notNull(attribtues[37])) {
							product.setDeposit_value(Float.parseFloat(attribtues[37]));
						}
						if (notNull(attribtues[38])) {
							product.setDeposit_pct_change_monthly(Float.parseFloat(attribtues[38]));
						}
						if (notNull(attribtues[39])) {
							product.setDeposit_pct_ira_paid(Boolean.parseBoolean(returnTrueOrFalse(attribtues[39])));

						}
						customer.addProductToPortfolio(product);
						customerRegion.put(customer.getId(), customer);
					}

				} else {
					System.out.println("Customer exists, adding a new product");

					if (notNull(attribtues[19]) == true) {
						product.setProduct_category(attribtues[19]);
					}
					if (notNull(attribtues[20])) {
						product.setProduct(attribtues[20]);
					}
					if (notNull(attribtues[21]) == true) {
						product.setCredit_duration(Integer.parseInt(attribtues[21]));
					}
					if (notNull(attribtues[22]) == true) {
						product.setCredit_rate(Float.parseFloat(attribtues[22]));
					}
					if (notNull(attribtues[23])) {
						product.setCredit_mature_date(attribtues[23]);
					}
					if (notNull(attribtues[24])) {
						product.setCredit_amt(Float.parseFloat(attribtues[24]));
					}
					if (notNull(attribtues[25])) {
						product.setInvestment_value(Float.parseFloat(attribtues[25]));
					}
					if (notNull(attribtues[26])) {
						product.setInvestment_pct_change_daily(Float.parseFloat(attribtues[26]));
					}
					if (notNull(attribtues[27])) {
						product.setInvestment_pct_change_monthly(Float.parseFloat(attribtues[27]));
					}
					if (notNull(attribtues[28])) {
						product.setInvestment_consumer_goods(Boolean.parseBoolean(returnTrueOrFalse(attribtues[28])));
					}
					if (notNull(attribtues[29])) {
						product.setInvestment_consumer_services(Boolean.parseBoolean(returnTrueOrFalse(attribtues[29])));
					}
					if (notNull(attribtues[30])) {
						product.setInvestment_telco(Boolean.parseBoolean(returnTrueOrFalse(attribtues[30])));
					}
					if (notNull(attribtues[31])) {
						product.setInvestment_healthcare(Boolean.parseBoolean(returnTrueOrFalse(attribtues[31])));
					}
					if (notNull(attribtues[32])) {
						product.setInvestment_industrials(Boolean.parseBoolean(returnTrueOrFalse(attribtues[32])));
					}
					if (notNull(attribtues[33])) {
						product.setInvestment_financials(Boolean.parseBoolean(returnTrueOrFalse(attribtues[33])));
					}
					if (notNull(attribtues[34])) {
						product.setInvestment_tech(Boolean.parseBoolean(returnTrueOrFalse(attribtues[34])));
					}
					if (notNull(attribtues[35])) {
						product.setInvestment_basic_materials(Boolean.parseBoolean(returnTrueOrFalse(attribtues[35])));
					}
					if (notNull(attribtues[36])) {
						product.setInvestment_oil_gas(Boolean.parseBoolean(returnTrueOrFalse(attribtues[36])));
					}
					if (notNull(attribtues[37])) {
						product.setDeposit_value(Float.parseFloat(attribtues[37]));
					}
					if (notNull(attribtues[38])) {
						product.setDeposit_pct_change_monthly(Float.parseFloat(attribtues[38]));
					}
					if (notNull(attribtues[39])) {
						product.setDeposit_pct_ira_paid(Boolean.parseBoolean(returnTrueOrFalse(attribtues[39])));

					}
				}
				customer.addProductToPortfolio(product);
				customerRegion.put(customer.getId(),customer);

			}
			fileReader.close();
			bufferedReader.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}


	public static boolean notNull(String val) {
		return !val.equals("NULL");
	}

	public static String returnTrueOrFalse(String val) {
		String result = (val.equals("0"))? "false" : "true";
		return result;
	}

}
