var dat = {
	  "legal.update": {
    "numCols": 4,
    "title": "agencies.update.document.title",
    "useTimestamp": true,
    "objectName": "agencies",
    "searchUrl": "/lcms-services/legalcase/advocate/agency/_search?code={id}",
    "groups": [
      {
        "name": "applicantType",
        "label": "advocates.create.group.title.applicantType",
        "fields": [
          {
            "name": "primaryOwner",
            "jsonPath": "agencies[0].isIndividual",
            "label": "advocates.create.primaryOwner",
            "type": "radio",
            "styleObj": {
              "display": "-webkit-box"
            },
            "isRequired": true,
            "isDisabled": true,
            "patternErrorMsg": "",
            "values": [
              {
                "label": "advocates.create.individual",
                "value": true
              },
              {
                "label": "advocates.create.Agency_Organization",
                "value": false
              }
            ],
            "defaultValue": true,
            "showHideFields": [
              {
                "ifValue": true,
                "hide": [
                  {
                    "name": "agencyDetails",
                    "isGroup": true,
                    "isField": false
                  },
                  {
                    "name": "additionalAdvocateWindow",
                    "isGroup": true,
                    "isField": false
                  },{
                    "name":"additionalOwnerWindow",
                    "isGroup": true,
                    "isField": false
                  },
                  {
                    "name": "orgaction",
                    "isGroup": true,
                    "isField": false
                  }
                ],
                "show": [
                  {
                    "name": "personalDetails",
                    "isGroup": true,
                    "isField": false
                  },
                  {
                    "name": "action",
                    "isGroup": true,
                    "isField": false
                  }
                ]
              },
              {
                "ifValue": false,
                "show": [
                  {
                    "name": "agencyDetails",
                    "isGroup": true,
                    "isField": false
                  },
                  {
                    "name": "additionalAdvocateWindow",
                    "isGroup": true,
                    "isField": false
                  },{
                    "name":"additionalOwnerWindow",
                    "isGroup": true,
                    "isField": false
                  },
                  {
                    "name": "empanelmentDetails",
                    "isGroup": true,
                    "isField": false
                  },
                  {
                    "name": "orgBankDetails",
                    "isGroup": true,
                    "isField": false
                  },
                  {
                    "name": "orgaction",
                    "isGroup": true,
                    "isField": false
                  }
                ],
                "hide": [
                  {
                    "name": "personalDetails",
                    "isGroup": true,
                    "isField": false
                  },
                  {
                    "name": "advocateEmpanelmentDetails",
                    "isGroup": true,
                    "isField": false
                  },
                  {
                    "name": "bankDetails",
                    "isGroup": true,
                    "isField": false
                  },
                  {
                    "name": "action",
                    "isGroup": true,
                    "isField": false
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "name": "empanelmentDetails",
        "hide": true,
        "label": "advocates.create.group.title.orgEmpanelmentDetails",
        "fields": [
          {
            "name": "organizationName",
            "jsonPath": "agencies[0].name",
            "label": "advocates.create.agencOrganizationName",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "patternErrorMsg": ""
          },
          {
            "name": "agencyAddress",
            "jsonPath": "agencies[0].agencyAddress",
            "label": "advocates.create.agencyAddress",
            "pattern": "",
            "type": "textarea",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "dateOfEmpanelment",
            "jsonPath": "agencies[0].dateOfEmpanelment",
            "label": "advocates.create.dateOfEmpanelment",
            "pattern": "",
            "type": "datePicker",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "standingCommiteDecisionDate",
            "jsonPath": "agencies[0].standingCommitteeDecisionDate",
            "label": "advocates.create.standingCommiteDecisionDate",
            "pattern": "",
            "type": "datePicker",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "newsPaperAdvertismentDate",
            "jsonPath": "agencies[0].newsPaperAdvertismentDate",
            "label": "advocates.create.newsPaperAdvertismentDate",
            "pattern": "",
            "type": "datePicker",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "empanelmentFromDate",
            "jsonPath": "agencies[0].empanelmentFromDate",
            "label": "advocates.create.empanelmentFromDate",
            "pattern": "",
            "type": "datePicker",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "empanelementToDate",
            "jsonPath": "agencies[0].empanelmentToDate",
            "label": "advocates.create.empanelementToDate",
            "pattern": "",
            "type": "datePicker",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          }
        ]
      },
      {
        "name": "agencyDetails",
        "hide": true,
        "label": "advocates.create.group.title.agencyDetails",
        "fields": [
          {
            "name": "advocateTitle",
            "jsonPath": "agencies[0].personDetails[0].title",
            "label": "advocates.create.advocateTitle",
            "type": "singleValueList",
            "isRequired": true,
            "isDisabled": false,
            "patternErrorMsg": "",
            "defaultValue": [
              {
                "key": "Mr",
                "value": "Mr"
              },
              {
                "key": "Mrs",
                "value": "Mrs"
              },
              {
                "key": "Ms",
                "value": "Ms"
              },
              {
                "key": "Miss",
                "value": "Miss"
              }
            ]
          },
          {
            "name": "firstName",
            "jsonPath": "agencies[0].personDetails[0].firstName",
            "label": "advocates.create.firstName",
            "pattern": "",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "secondName",
            "jsonPath": "agencies[0].personDetails[0].secondName",
            "label": "advocates.create.secondName",
            "pattern": "",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "lastName",
            "jsonPath": "agencies[0].personDetails[0].lastName",
            "label": "advocates.create.lastName",
            "pattern": "",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "aadharNumber",
            "jsonPath": "agencies[0].personDetails[0].aadhar",
            "label": "advocates.create.aadharNumber",
            "pattern": "",
            "type": "aadhar",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": "The UID number is as follows:412521475850 : all 12 digits"
          },
          {
            "name": "gender",
            "jsonPath": "agencies[0].personDetails[0].gender",
            "label": "advocates.create.gender",
            "pattern": "",
            "type": "singleValueList",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": "",
            "defaultValue": [
              {
                "key": "Male",
                "value": "Male"
              },
              {
                "key": "Female",
                "value": "Female"
              }
            ]
          },
          {
            "name": "age",
            "jsonPath": "agencies[0].personDetails[0].age",
            "label": "advocates.create.age",
            "pattern": "",
            "type": "number",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "dob",
            "jsonPath": "agencies[0].personDetails[0].dob",
            "label": "advocates.create.dob",
            "pattern": "",
            "type": "datePicker",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "address",
            "jsonPath": "agencies[0].personDetails[0].address",
            "label": "advocates.create.address",
            "pattern": "",
            "type": "textarea",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "mobileNumber",
            "jsonPath": "agencies[0].personDetails[0].mobileNumber",
            "label": "advocates.create.mobileNumber",
            "pattern": "",
            "type": "mobileNumber",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": "The Phone number structure is as follows: 999XXXX999 "
          },
          {
            "name": "contactNumber",
            "jsonPath": "agencies[0].personDetails[0].contactNo",
            "label": "advocates.create.contactNumber",
            "pattern": "",
            "type": "mobileNumber",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": "The Phone number structure is as follows: 999XXXX999 "
          },
          {
            "name": "email",
            "jsonPath": "agencies[0].personDetails[0].emailId",
            "label": "advocates.create.email",
            "pattern": "",
            "type": "email",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": " Email should be in format e.g - abc@abc.com"
          },
          {
            "name": "panNumber",
            "jsonPath": "agencies[0].personDetails[0].pan",
            "label": "advocates.create.panNumber",
            "pattern": "",
            "type": "pan",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": "The PAN structure is as follows: AAAPL1234C ."
          },
          {
            "name": "VATTinNumber",
            "jsonPath": "agencies[0].personDetails[0].vatTinNo",
            "label": "advocates.create.VATTinNumber",
            "pattern": "",
            "type": "number",
            "isRequired": false,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          }
        ]
      },
      {
        "name": "personalDetails",
        "label": "advocates.create.group.title.personalDetails",
        "fields": [
          {
            "name": "advocateTitle",
            "jsonPath": "agencies[0].advocates[0].title",
            "label": "advocates.create.advocateTitle",
            "type": "singleValueList",
            "isRequired": true,
            "isDisabled": false,
            "patternErrorMsg": "",
            "defaultValue": [
              {
                "key": "Mr",
                "value": "Mr"
              },
              {
                "key": "Mrs",
                "value": "Mrs"
              },
              {
                "key": "Ms",
                "value": "Ms"
              },
              {
                "key": "Miss",
                "value": "Miss"
              }
            ]
          },
          {
            "name": "firstName",
            "jsonPath": "agencies[0].advocates[0].firstName",
            "label": "advocates.create.firstName",
            "pattern": "",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "secondName",
            "jsonPath": "agencies[0].advocates[0].secondName",
            "label": "advocates.create.secondName",
            "pattern": "",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "lastName",
            "jsonPath": "agencies[0].advocates[0].lastName",
            "label": "advocates.create.lastName",
            "pattern": "",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "aadharNumber",
            "jsonPath": "agencies[0].advocates[0].aadhar",
            "label": "advocates.create.aadharNumber",
            "pattern": "",
            "type": "aadhar",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": "The UID number is as follows:412521475850 : all 12 digits"
          },
          {
            "name": "gender",
            "jsonPath": "agencies[0].advocates[0].gender",
            "label": "advocates.create.gender",
            "pattern": "",
            "type": "singleValueList",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": "",
            "defaultValue": [
              {
                "key": "Male",
                "value": "Male"
              },
              {
                "key": "Female",
                "value": "Female"
              }
            ]
          },
          {
            "name": "age",
            "jsonPath": "agencies[0].advocates[0].age",
            "label": "advocates.create.age",
            "pattern": "",
            "type": "number",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "dob",
            "jsonPath": "agencies[0].advocates[0].dob",
            "label": "advocates.create.dob",
            "pattern": "",
            "type": "datePicker",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "address",
            "jsonPath": "agencies[0].advocates[0].address",
            "label": "advocates.create.address",
            "pattern": "",
            "type": "textarea",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "mobileNumber",
            "jsonPath": "agencies[0].advocates[0].mobileNumber",
            "label": "advocates.create.mobileNumber",
            "pattern": "",
            "type": "mobileNumber",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": "The Phone number structure is as follows: 999XXXX999 "
          },
          {
            "name": "contactNumber",
            "jsonPath": "agencies[0].advocates[0].contactNo",
            "label": "advocates.create.contactNumber",
            "pattern": "",
            "type": "mobileNumber",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": "The Phone number structure is as follows: 999XXXX999 "
          },
          {
            "name": "email",
            "jsonPath": "agencies[0].advocates[0].emailId",
            "label": "advocates.create.email",
            "pattern": "",
            "type": "email",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": " Email should be in format e.g - abc@abc.com"
          },
          {
            "name": "panNumber",
            "jsonPath": "agencies[0].advocates[0].pan",
            "label": "advocates.create.panNumber",
            "pattern": "",
            "type": "pan",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": "The PAN structure is as follows: AAAPL1234C ."
          },
          {
            "name": "VATTinNumber",
            "jsonPath": "agencies[0].advocates[0].vatTinNo",
            "label": "advocates.create.VATTinNumber",
            "pattern": "",
            "type": "number",
            "isRequired": false,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          }
        ]
      },
      {
        "name": "advocateEmpanelmentDetails",
        "label": "advocates.create.group.title.empanelmentDetails",
        "fields": [
          {
            "name": "dateOfEmpanelment",
            "jsonPath": "agencies[0].advocates[0].dateOfEmpanelment",
            "label": "advocates.create.dateOfEmpanelment",
            "pattern": "",
            "type": "datePicker",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "standingCommiteDecisionDate",
            "jsonPath": "agencies[0].advocates[0].standingCommitteeDecisionDate",
            "label": "advocates.create.standingCommiteDecisionDate",
            "pattern": "",
            "type": "datePicker",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "newsPaperAdvertismentDate",
            "jsonPath": "agencies[0].advocates[0].newsPaperAdvertismentDate",
            "label": "advocates.create.newsPaperAdvertismentDate",
            "pattern": "",
            "type": "datePicker",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "empanelmentFromDate",
            "jsonPath": "agencies[0].advocates[0].empanelmentFromDate",
            "label": "advocates.create.empanelmentFromDate",
            "pattern": "",
            "type": "datePicker",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "empanelementToDate",
            "jsonPath": "agencies[0].advocates[0].empanelmentToDate",
            "label": "advocates.create.empanelementToDate",
            "pattern": "",
            "type": "datePicker",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          }
        ]
      },
      {
        "name": "bankDetails",
        "label": "advocates.create.group.title.bankDetails",
        "fields": [
          {
            "name": "bankName",
            "jsonPath": "agencies[0].advocates[0].bankName",
            "label": "advocates.create.bankName",
            "pattern": "",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "bankBranch",
            "jsonPath": "agencies[0].advocates[0].bankBranch",
            "label": "advocates.create.bankBranch",
            "pattern": "",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "bankAcc",
            "jsonPath": "agencies[0].advocates[0].bankAccountNo",
            "label": "advocates.create.bankAcc",
            "pattern": "",
            "type": "number",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "IFSCCode",
            "jsonPath": "agencies[0].advocates[0].ifscCode",
            "label": "advocates.create.IFSCCode",
            "pattern": "",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "MICRCode",
            "jsonPath": "agencies[0].advocates[0].micr",
            "label": "advocates.create.MICRCode",
            "pattern": "",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          }
        ]
      },
      {
        "name": "orgBankDetails",
        "hide": true,
        "label": "advocates.create.group.title.orgBankDetails",
        "fields": [
          {
            "name": "bankName",
            "jsonPath": "agencies[0].bankName",
            "label": "advocates.create.bankName",
            "type": "singleValueList",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "url":"/egov-mdms-service/v1/_get?&moduleName=lcms&masterName=bank|$..name|$..name",
            depedants: [
              {
                jsonPath: "agencies[0].bankBranch",
                type: "dropDown",
                "pattern":"/egov-mdms-service/v1/_get?&moduleName=lcms&masterName=bankBranch&filter=%5B%3F%28%40.bankName%3D%3D'{agencies[0].bankName}'%29%5D|$..branch|$..branch"
              }]
          },
          {
            "name": "bankBranch",
            "jsonPath": "agencies[0].bankBranch",
            "label": "advocates.create.bankBranch",
            "type": "singleValueList",
            "isRequired": true,
            "isDisabled": false,
            patternErrorMsg: "",
            "url": ""

          },
          {
            "name": "bankAcc",
            "jsonPath": "agencies[0].bankAccountNo",
            "label": "advocates.create.bankAcc",
            "pattern": "",
            "type": "number",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "IFSCCode",
            "jsonPath": "agencies[0].ifscCode",
            "label": "advocates.create.IFSCCode",
            "pattern": "",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "MICRCode",
            "jsonPath": "agencies[0].micr",
            "label": "advocates.create.MICRCode",
            "pattern": "",
            "type": "text",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          }
        ]
      },
      {
        "name": "action",
        "label": "advocates.create.group.title.action",
        "fields": [
          {
            "name": "actionType",
            "jsonPath": "agencies[0].advocates[0].status",
            "label": "advocates.create.actionType",
            "type": "radio",
            "isRequired": true,
            "isDisabled": false,
            "patternErrorMsg": "",
            "values": [
              {
                "label": "advocates.create.active",
                "value": "active"
              },
              {
                "label": "advocates.create.inactive",
                "value": "inactive"
              },
              {
                "label": "advocates.create.terminate",
                "value": "terminate"
              }
            ],
            "defaultValue": "active",
            "enableDisableFields": [
              {
                "ifValue": "active",
                 "enable": [],
                "disable": [
                  "terminationDate",
                  "inActivationDate",
                  "reasonOfTermination"
                ]
              },
              {
                "ifValue": "inactive",
                "enable": [
                  "inActivationDate",
                  "reasonOfTermination"
                ],
                "disable": [
                  "terminationDate"
                ]
              },
              {
                "ifValue": "terminate",
                "enable": [
                  "terminationDate",
                  "reasonOfTermination"
                ],
                "disable": [
                  "inActivationDate"
                ]
              }
            ]
          },
          {
            "name": "inActivationDate",
            "jsonPath": "agencies[0].advocates[0].inActiveDate",
            "label": "advocates.create.inActivationDate",
            "pattern": "",
            "type": "datePicker",
            "isRequired": false,
            "isDisabled": true,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "terminationDate",
            "jsonPath": "agencies[0].advocates[0].terminationDate",
            "label": "advocates.create.terminationDate",
            "pattern": "",
            "type": "datePicker",
            "isRequired": false,
            "isDisabled": true,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            name: "reasonOfTermination",
            jsonPath: "agencies[0].reasonOfTermination",
            label: "advocates.create.reasonOfTerminationOrDeactivation",
            type: "textarea",
            fullWidth: true,
            isRequired: true,
            isDisabled: true,
            patternErrorMsg: ""
          }
        ]
      },
      {
        "name": "orgaction",
        "label": "advocates.create.group.title.action",
        "fields": [
          {
            "name": "actionType",
            "jsonPath": "agencies[0].status",
            "label": "advocates.create.actionType",
            "type": "radio",
            "isRequired": true,
            "isDisabled": false,
            "patternErrorMsg": "",
            "values": [
              {
                "label": "advocates.create.active",
                "value": "active"
              },
              {
                "label": "advocates.create.inactive",
                "value": "inactive"
              },
              {
                "label": "advocates.create.terminate",
                "value": "terminate"
              }
            ],
            "defaultValue": "active",
            "enableDisableFields": [
              {
                "ifValue": "active",
                 "enable": [],
                "disable": [
                  "terminationDate",
                  "inActivationDate",
                  "reasonOfTermination"
                ]
              },
              {
                "ifValue": "inactive",
                "enable": [
                  "inActivationDate",
                  "reasonOfTermination"
                ],
                "disable": [
                  "terminationDate"
                ]
              },
              {
                "ifValue": "terminate",
                "enable": [
                  "terminationDate",
                  "reasonOfTermination"
                ],
                "disable": [
                  "inActivationDate"
                ]
              }
            ]
          },
          {
            "name": "inActivationDate",
            "jsonPath": "agencies[0].inActiveDate",
            "label": "advocates.create.inActivationDate",
            "pattern": "",
            "type": "datePicker",
            "isRequired": false,
            "isDisabled": true,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "terminationDate",
            "jsonPath": "agencies[0].terminationDate",
            "label": "advocates.create.terminationDate",
            "pattern": "",
            "type": "datePicker",
            "isRequired": false,
            "isDisabled": true,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            name: "reasonOfTermination",
            jsonPath: "agencies[0].reasonOfTermination",
            label: "advocates.create.reasonOfTerminationOrDeactivation",
            type: "textarea",
            fullWidth: true,
            isRequired: true,
            isDisabled: true,
            patternErrorMsg: ""
          }
        ]
      },
      {
        "name": "additionalOwnerWindow",
        "hide": true,
        "label": "advocates.create.group.title.additionalOwnerWindow",
        "fields": [
          {
            "name": "additionalOwnerWindow",
            "jsonPath": "agencies[0]",
            "arrayPath": "personDetails",
            "label": "legal.advocates.create.additionalOwnerWindow",
            "modulepath":"legal.create",
            // "isExceptFirstRecord":true,
            "hidePrimaryRecord" :true,
            "pattern": "",
            "type": "window",
            "tableConfig": {
              "header": [
                {
                  "label": "legal.create.additionalOwnerName",
                  "style": {
                    "width": "700px"
                  }
                }
              ],
              "rows": [
                {
                  "displayField": "firstName"
                }
              ]
            },
            "subPath": "legal/master/addowner",
            "isRequired": false,
            "isDisabled": true,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          }
        ]
      },  {
        "name": "additionalAdvocateWindow",
        "hide": true,
        "label": "advocates.create.group.title.additionalAdvocateWindow",
        "fields": [
          {
            "name": "additionalAdvocateWindow",
            "jsonPath": "agencies[0]",
            "arrayPath": "advocates",
            "label": "legal.advocates.create.additionalAdvocateWindow",
            "pattern": "",
            "modulepath":"legal.create",
            "type": "window",
            "tableConfig": {
              "header": [
                {
                  "label": "legal.create.advocateName",
                  "style": {
                    "width": "700px"
                  }
                }
              ],
              "rows": [
                {
                  "displayField": "firstName"
                }
              ]
            },
            "subPath": "legal/master/addadvocate",
            "isRequired": false,
            "isDisabled": true,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          }
        ]
      }
    ],
    "url": "/lcms-services/legalcase/advocate/_update",
    "tenantIdRequired": true
  }
};
export default dat;