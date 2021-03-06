var dat = {
	"egf.create": {
		"numCols": 12/3,
		"url":  "/egf-master/bankbranches/_create",
		"tenantIdRequired": true,
		"idJsonPath": "BankBranches[0].code",
		"useTimestamp": true,
		"objectName": "bankBranches",
		"groups": [
			{
				"label": "Create Bank Branch",
				"name": "createBankBranch",
				"fields": [
						{
							"name": "name",
							"jsonPath": "bankBranches[0].name",
							"label": "Name",
							"pattern": "^[\s.]*([^\s.][\s.]*){0,100}$",
							"type": "text",
							"isRequired": true,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": "Length minimum is 3 and maximum is 100"
						},{
							"name": "code",
							"jsonPath": "bankBranches[0].code",
							"label": "Code",
							"pattern": "^[\s.]*([^\s.][\s.]*){0,100}$",
							"type": "text",
							"isRequired": true,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": "Length minimum is 3 and maximum is 100"
						},
						{
							"name": "address",
							"jsonPath": "bankBranches[0].address",
							"label": "Address",
							"pattern": "^[\s.]*([^\s.][\s.]*){0,250}$",
							"type": "textarea",
							"isRequired": true,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						},
						{
							"name": "address2",
							"jsonPath": "bankBranches[0].address",
							"label": "Address2",
							"pattern": "^[\s.]*([^\s.][\s.]*){0,250}$",
							"type": "textarea",
							"isRequired": false,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						},
						{
							"name": "city",
							"jsonPath": "bankBranches[0].city",
							"label": "City",
							"pattern": "^[\s.]*([^\s.][\s.]*){0,250}$",
							"type": "text",
							"isRequired": false,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						},
						{
							"name": "state",
							"jsonPath": "bankBranches[0].state",
							"label": "State",
							"pattern": "^[\s.]*([^\s.][\s.]*){0,250}$",
							"type": "text",
							"isRequired": false,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						},
						{
							"name": "pincode",
							"jsonPath": "bankBranches[0].pincode",
							"label": "Pincode",
							"pattern": "^[\s.]*([^\s.][\s.]*){0,250}$",
							"type": "text",
							"isRequired": false,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						},
						{
							"name": "phone",
							"jsonPath": "bankBranches[0].phone",
							"label": "Phone",
							"pattern": "^[\s.]*([^\s.][\s.]*){0,250}$",
							"type": "text",
							"isRequired": false,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						},
						{
							"name": "fax",
							"jsonPath": "bankBranches[0].fax",
							"label": "Fax",
							"pattern": "^[\s.]*([^\s.][\s.]*){0,250}$",
							"type": "text",
							"isRequired": false,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						},
						{
							"name": "contactPerson",
							"jsonPath": "bankBranches[0].contactPerson",
							"label": "Contact Person",
							"pattern": "^[\s.]*([^\s.][\s.]*){0,250}$",
							"type": "text",
							"isRequired": false,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						},
						{
							"name": "description",
							"jsonPath": "bankBranches[0].description",
							"label": "Description",
							"pattern": "^[\s.]*([^\s.][\s.]*){0,250}$",
							"type": "textarea",
							"isRequired": false,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						},
						{
							"name": "Active",
							"jsonPath": "bankBranches[0].active",
							"label": "wc.create.active",
							"pattern": "",
							"type": "checkbox",
							"isRequired": false,
							"defaultValue":true,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						},
						{
							"name": "micr",
							"jsonPath": "bankBranches[0].micr",
							"label": "MICR",
							"pattern": "^[\s.]*([^\s.][\s.]*){0,250}$",
							"type": "text",
							"isRequired": false,
							"isDisabled": false,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						}
				]
			}
		]
	}
}

export default dat;
