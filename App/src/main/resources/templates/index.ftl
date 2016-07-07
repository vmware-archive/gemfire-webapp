<html>
<head>
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"
          name="viewport">
    <link href='https://fonts.googleapis.com/css?family=Istok+Web:400,700,400italic,700italic' rel='stylesheet'
          type='text/css'>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
            integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" href="stylesheet.css">
    <title>Wealth Frontier</title>
</head>
<body>
<header>
    <div class="container">
        <h1 class="mbn mtn big-txt night-3">Wealth Frontier</h1>
    </div>
</header>
<div class="container mtxl">
    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <#list customers as user>
            <div class="customer panel panel-default">
                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                   href="#${user.getId()}" onclick="getAlerts('${user.getId()}')" aria-expanded="false"
                   aria-controls="clientOne">
                    <div class="panel-heading pvml" role="tab" id="headingOne">
                        <h4 class="panel-title inline-block day-1">

                            <p class="p-txt">${user.getFirstName()} ${user.getLastName()}</p>
                        </h4>
                    </div>
                </a>

                <div id="${user.getId()}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                    <div class="panel-body">
                        <div class="col-md-7 pbl">

                            <ul class="list-unstyled" id="${user.getId()}AlertsLeft"></ul>
                            <p class="em-high pbm">Products Owned:</p>
                            <ul id ="${user.getId()}_portfolio" class="list-unstyled">
                                <#list user.getPortfolio() as product>
                                    <li id="" onload="format(${user.getId()}_${product.getProduct()})" class="p-txt ${user.getId()}_product">${product.getProduct()} </li>
                                </#list>
                            </ul>
                        </div>
                        <div id="right#${user.getId()}" class="col-md-5 pbl">
                            <p class="em-high ">Similar Clients:</p>

                            <p class="small-txt day-2 em-alt pbm">
                                Clients with similar profiles have also purchased these other products.
                            </p>
                            <ul class="list-unstyled" id="${user.getId()}AlertsRight"></ul>
                        </div>

                    </div>
                </div>
            </div>
        </#list>

    </div>
</div>
</div>
</div>

<div>
</div>


</body>
<script>

 function getAlerts(id) {
    console.log(id);
   array = [];
    $('.' + id + '_product').each(function(i, obj) {
        	oldVal = $(this).text();

    	if ($.inArray(oldVal, array) ==  -1) {
    		console.log("adding " + oldVal + " to " + array);
			array.push(oldVal);
			newVal = oldVal.replace(/_/g, ' ');
			$(this).text(newVal);

    	} else {
    		console.log(oldVal + " is already in " + array);
    		$(this).remove();
    	}
	});
	array = [];
    var queryUrl= "alerts/" + id
     $.ajax({url: queryUrl, success: function(result){
     			customerContainerDiv = "#" + id + "Container";
     			displayDiv = "#" + id + "Alerts";
     			displayDivClass = id + "AlertsDiv" ;

                	for (i in result.alertDetails) {

							if (result.alertDetails[i].alertMessage != null) {
								lastIndex = result.alertDetails[i].alertMessage.length - 1;
								if ( result.alertDetails[i].alertMessage.substr(lastIndex) == "|") {
                					appendSimilarClients(displayDiv,result)
                				} else {
									appendLoanMessage(displayDiv,result);

								}

							}


		    		}


	  }


	  });


 }

 function appendSimilarClients(displayDiv,result) {

 		id = result.customerId;
 		displayDivRight = displayDiv + "Right"
		$("#right" + id ).append("<div id=" + displayDiv);
		console.log("display div is " + displayDiv);
		alertId= id + "alerts" + i
		$("#" + alertId).remove();
		console.log(result.alertDetails[i].alertMessage);
		var info = result.alertDetails[i].alertMessage.split("|")

		for (i in info) {
			if( i != 0) {

				item = info[i].split(".");
				name = item[0]
				industries = item[1];
				currentPerson = "relatedPerson" + i
				$("#" + currentPerson).remove()
				$(displayDivRight).append("<ul class='list-unstyled'><li id=" + currentPerson + " class='pbs'>" + name)
				$("#" + currentPerson).append("<ul id=" + currentPerson + "List>")
				var industriesArr = industries.split(", ")

				for (x in industriesArr ) {
					$("#" + currentPerson + "List").append("<li>" + industriesArr[x] + "</li>");
				}
				$(displayDivRight).append("<ul class='list-unstyled'>")
				$(displayDivRight).append("</ul>")

			}
		}
	$(displayDivRight).append("</ul></li></div>");


 }

 function appendLoanMessage(displayDiv,result) {
 		console.log("appending loan to " + displayDiv);
		id = result.customerId;
		console.log("putting alert message on the screen " + result.alertDetails[i].alertMessage);
		displayDivLeft = displayDiv + "Left"
		$(displayDivLeft).addClass("alert alert-warning");
		$(customerContainerDiv).append("<div id=" + displayDivLeft + "class='col-md-7 pbl'>");

		alertId= id + "alerts" + i
		$("#" + alertId).remove();
		$(displayDivLeft).append("<li id=" + alertId + " class='pbm'><span class='prm glyphicon glyphicon-warning-sign'></span>" + result.alertDetails[i].alertMessage);
		var subAlertContainer = id + "messages";
		$("#" + subAlertContainer).remove();
		$(displayDivLeft).append("<ul id=" + subAlertContainer + " class='mll'>");

		resultsDetails = result.alertDetails[i].details
		console.log("details to put on the screen" + resultsDetails);
		for (x in resultsDetails) {

			$("#" + subAlertContainer).append("<li id=" + i + x + displayDivClass + ">" + resultsDetails[x] + "</li>");
		}
		$(displayDivLeft).append("</ul></li></div>")

}


</script>
</html>