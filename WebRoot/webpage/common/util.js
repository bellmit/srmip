//金额格式化：元
function formatCurrency(num) {
	if(num){
		num = num.toString().replace(/\$|\,/g, '');
	}
	
	if (isNaN(num) || !num)
		num = "0";
	
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num * 100 + 0.50000000001);
	cents = num % 100;
	num = Math.floor(num / 100).toString();
	if (cents < 10)
		cents = "0" + cents;
	for ( var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
		num = num.substring(0, num.length - (4 * i + 3)) + ','
				+ num.substring(num.length - (4 * i + 3));
	return (((sign) ? '' : '-') + num + '.' + cents);
}


//金额格式化：万元
function formatCurrencyMillion(num) {
	if(num){
		num = num.toString().replace(/\$|\,/g, '');
	}
	
	if (isNaN(num) || !num){
		num = "0";
	}else{
		num = (parseFloat(num)/10000).toString();
	}
		
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num * 100 + 0.50000000001);
	cents = num % 100;
	num = Math.floor(num / 100).toString();
	if (cents < 10)
		cents = "0" + cents;
	for ( var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
		num = num.substring(0, num.length - (4 * i + 3)) + ','
				+ num.substring(num.length - (4 * i + 3));
	return (((sign) ? '' : '-') + num + '.' + cents);
}

