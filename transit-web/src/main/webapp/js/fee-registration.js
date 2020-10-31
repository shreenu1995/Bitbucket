
function viewFee(feeName) {
	get('feeView').value = feeName;
	document.forms["viewFeeManagementAction"].submit();
}