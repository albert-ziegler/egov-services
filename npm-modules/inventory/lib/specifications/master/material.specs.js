var dat =
{
   "inventory.search":{
      "numCols":4,
      "useTimestamp":true,
      "objectName":"",
      "url":"/inventory-services/materials/_search",
      "title":"inventory.material.title",
      "groups":[
         {
            "name":"search",
            "label":"inventory.common.searchcriteria",
            "fields":[
                {
                   "name":"code",
                   "pattern":"",
                   "label":"inventory.material.name",
                   "type":"autoCompelete",
                   "jsonPath":"code",
                   "displayJsonPath":"materialName",
                   "isRequired":false,
                   "isDisabled":false,
                   "isKeyValuePair":true,
                   "url":"/inventory-services/materials/_search?|$.materials[*].code|$.materials[*].name"
                },
                {
                   "name":"code",
                   "jsonPath":"materialType",
                   "label":"inventory.material.materialtype",
                   "type":"singleValueList",
                   "isRequired":false,
                   "isDisabled":false,
                   "patternErrorMsg":"",
                   "url":"/egov-mdms-service/v1/_get?&moduleName=inventory&masterName=MaterialType|$..code|$..name"
                },

                {
                   "name":"store",
                   "pattern":"",
                   "label":"inventory.store.name",
                   "type":"autoCompelete",
                   "jsonPath":"store",
                   "isKeyValuePair":true,
                   "isRequired":false,
                   "isDisabled":false,
                   "url":"inventory-services/stores/_search?|$.stores[*].code|$.stores[*].name"
                },
                {
                  "name": "status",
                  "jsonPath": "status",
                  "label": "inventory.common.status",
                  "type": "singleValueList",
                  "defaultValue":[
                    {key: null, value: "-- Please Select --"},
                    {key:"Active", value:"Active"},
                    {key:"Withdrawn", value:"Withdrawn"},
                    {key:"Obsolete", value:"Obsolete"},
                    {key:"Inactive", value:"Inactive"}
                  ],
                  "isRequired": false,
                  "isDisabled": false,
                  "patternErrorMsg": ""
                },

                {
                  "name": "listStoreInfo",
                  "jsonPath": "listStoreInfo",
                  "label": "inventory.material.listStoreInfo",
                  "type": "checkbox",
                  "defaultValue":true,
                  "isRequired": false,
                  "isDisabled": false,
                  "patternErrorMsg": ""
                }
            ]
         }
      ],
      "result":{
         "header":[
            {
               "label":"Material Name"
            },
            {
               "label":"Material Type Name"
            },
            {
               "label":"Status"
            },

         ],
         "values":[
            "name",
            {
               path:"materialType.code",
               valExp:`getValFromDropdownData('materialType', _.get(values[i], specsValuesList[j].path), 'value')`
            },
            "status"
         ],
         "resultPath":"materials",
         "resultIdKey":"code",
         "rowClickUrlUpdate":"/update/inventory/material/{code}",
         "rowClickUrlView":"/view/inventory/material/{code}",
         "rowClickUrlAdd" : "/create/inventory/material",
         "rowClickUrlDelete" : {
           url:"inventory-services/materials/_update",
           body:{ status:'Inactive', inActiveDate:function(){ return new Date().getTime() } }
         }
      }
   },
   "inventory.create":{
      "numCols":4,
      "useTimestamp":true,
      "objectName":"materials",
      "url":"/inventory-services/materials/_search",
      "title":"inventory.material.title",
      "groups":[
         {
            "name":"Add Material",
            "label":"inventory.create.group.title.addmaterial",
            "fields":[
               {
                 name:"status",
                 jsonPath:"materials[0].status",
                 defaultValue:"Active",
                 isHidden:true
               },
               {
                  "name":"oldCode",
                  "jsonPath":"materials[0].oldCode",
                  "label":"inventory.material.oldcode",
                  "type":"text",
                  "isRequired":false,
                  "isDisabled":false,
                  "maxLength":50,
                  "patternErrorMsg":""
               },
               {
                  "name":"code",
                  "jsonPath":"materials[0].materialType.code",
                  "label":"inventory.material.materialtype",
                  "type":"singleValueList",
                  "isRequired":true,
                  "isDisabled":false,
                  "patternErrorMsg":"",
                  "url":"/egov-mdms-service/v1/_get?&moduleName=inventory&masterName=MaterialType|$..code|$..name"
               },
               {
                  "name":"name",
                  "jsonPath":"materials[0].name",
                  "label":"inventory.material.name",
                  "type":"text",
                  "isRequired":true,
                  "isDisabled":false,
                  "maxLength":50,
                  "patternErrorMsg":""
               },
               {
                  "name":"description",
                  "jsonPath":"materials[0].description",
                  "label":"inventory.material.description",
                  "type":"textarea",
                  "isRequired":true,
                  "isDisabled":false,
                  "maxLength":1000,
                  "patternErrorMsg":""
               },
               {
                  "name":"name",
                  "jsonPath":"materials[0].baseUom.code",
                  "label":"inventory.material.baseuom",
                  "type":"singleValueList",
                  "isRequired":true,
                  "isDisabled":false,
                  "patternErrorMsg":"",
                  "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Uom|$..code|$..description"
               },
               {
                  "name":"inventoryType",
                  "jsonPath":"materials[0].inventoryType",
                  "defaultValue":[
                    {key: null, value: "-- Please Select --"},
                    {
                       "key":"Asset",
                       "value":"Asset"
                    },
                    {
                       "key":"Consumable",
                       "value":"Consumable"
                    }
                  ],
                  "label": "inventory.material.inventorytype",
                  "type":"singleValueList",
                  "isRequired":true,
                  "isDisabled":false,
                  "patternErrorMsg":"",
                  "showHideFields": [{
                      "ifValue": "Asset",
                      "hide": [],
                      "show": [{
                          "name": "assetCategory",
                          "isGroup": false,
                          "isField": true
                      }]
                  }]
               },
               {
                  "name":"assetCategory",
                  "jsonPath":"materials[0].assetCategory.code",
                  "label":"inventory.material.assetcategory",
                  "type":"singleValueList",
                  "isRequired":true,
                  "isDisabled":false,
                  "hide":true,
                  "patternErrorMsg":"",
                  "url":"/egov-mdms-service/v1/_get?tenantId=default&moduleName=ASSET&masterName=AssetCategory|$..code|$..name"
               },

            ]
         },
         {
            "name":"Material Map To Store",
            "label":"inventory.material.maptostore",
            "fields":[
                {
                  "name":"department",
                  "pattern":"",
                  "type":"singleValueList",
                  "jsonPath":"departmentMaster",
                  "isRequired":false,
                  "isDisabled":false,
                  "hide":true,
                  "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Department|$..code|$..name"
                },

               {
                  "type":"tableList",
                  "jsonPath":"materials[0].storeMapping",
                  "tableList":{
                     "header":[
                        {
                           "label":"Store Name"
                        },
                        {
                           "label":"Department Name"
                        },
                        {
                           "label":"Account Code"
                        },
                        {
                           "label":"Active"
                        }
                     ],
                     "values":[
                       {
                          "name":"store",
                          "pattern":"",
                          "type":"singleValueList",
                          "jsonPath":"materials[0].storeMapping[0].store.code",
                          "isRequired":true,
                          "isDisabled":false,
                          "url":"inventory-services/stores/_search?|$.stores[*].code|$.stores[*].name|$.stores[*].department",
                          "depedants":[
                             {
                               "jsonPath":"materials[0].storeMapping[0].department.code",
                               "type":"textField",
                               "valExp":"getValFromDropdownData('materials[0].storeMapping[*].store.code', getVal('materials[0].storeMapping[*].store.code'), 'others[0].code')"
                             }
                            //  ,{
                            //     "jsonPath":"materials[0].storeMapping[0].department.name",
                            //     "type":"textField",
                            //     "valExp":"getValFromDropdownData('departmentMaster', getVal('materials[0].storeMapping[*].department.code'), 'value')"
                            //  }
                          ]
                       },
                       {
                          "name":"department",
                          "pattern":"",
                          "type":"singleValueList",
                          "jsonPath":"materials[0].storeMapping[0].department.code",
                          "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Department|$..code|$..name",
                          "isRequired":true,
                          "isDisabled":true
                       },
                       {
                          "name":"accountcode",
                          "pattern":"",
                          "type":"singleValueList",
                          "jsonPath":"materials[0].storeMapping[0].chartofAccount.glCode",
                          "isRequired":true,
                          "isDisabled":false,
                          "url": "/egf-master/chartofaccounts/_search?|$.chartOfAccounts[*].glcode|$.chartOfAccounts[*].name"
                       },
                       {
                          "name":"active",
                          "pattern":"",
                          "type":"checkbox",
                          "defaultValue":true,
                          "label":"",
                          "jsonPath":"materials[0].storeMapping[0].active",
                          "isRequired":false,
                          "isDisabled":false
                       }
                     ]
                  }
               }
            ]
         },
         {
            "name":"Puchasing Information",
            "label":"inventory.material.purchasinginfo",
            "fields":[
               {
                  "name":"code",
                  "jsonPath":"materials[0].purchaseUom.code",
                  "label":"inventory.material.purchaseuom",
                  "type":"singleValueList",
                  "isRequired":true,
                  "isDisabled":false,
                  "patternErrorMsg":"",
                  "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Uom|$..code|$..description"
               },
               {
                  "name":"accountcode",
                  "pattern":"",
                  "type":"singleValueList",
                  "jsonPath":"materials[0].expenseAccount.glCode",
                  "label":"inventory.material.expenseacctcode",
                  "isRequired":false,
                  "isDisabled":false,
                  "url": "/egf-master/chartofaccounts/_search?|$.chartOfAccounts[*].glcode|$.chartOfAccounts[*].name"
               }
            ]
         },
         {
            "name":"Stocking Information",
            "label":"inventory.material.stockinginfo",
            "fields":[
               {
                  "name":"materialClass",
                  "jsonPath":"materials[0].materialClass",
                  "label":"inventory.material.usageclass",
                  "type":"singleValueList",
                  "isRequired":true,
                  "isDisabled":false,
                  "patternErrorMsg":"",
                  defaultValue:[
                    {key: null, value: "-- Please Select --"},
                    {
                       "key":"HighUsage",
                       "value":"High usage"
                    },
                    {
                       "key":"MediumUsage",
                       "value":"Medium usage"
                    },
                    {
                       "key":"LowUsage",
                       "value":"Low usage"
                    }
                  ]
               },
               {
                  "name":"name",
                  "jsonPath":"materials[0].stockingUom.code",
                  "label":"inventory.material.stockinguom",
                  "type":"singleValueList",
                  "isRequired":true,
                  "isDisabled":false,
                  "patternErrorMsg":"",
                  "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Uom|$..code|$..description"
               },
               {
                  "name":"minQuantity",
                  "jsonPath":"materials[0].minQuantity",
                  "label":"inventory.material.minqty",
                  "type":"number",
                  "isRequired":true,
                  "isDisabled":false,
                  "patternErrorMsg":""
               },
               {
                  "name":"maxQuantity",
                  "jsonPath":"materials[0].maxQuantity",
                  "label":"inventory.material.maxqty",
                  "type":"number",
                  "isRequired":true,
                  "isDisabled":false,
                  "patternErrorMsg":""
               },
               {
                  "name":"reorderLevel",
                  "jsonPath":"materials[0].reorderLevel",
                  "label":"inventory.material.reorderlevel",
                  "type":"number",
                  "isRequired":true,
                  "isDisabled":false,
                  "patternErrorMsg":""
               },
               {
                  "name":"reorderQuantity",
                  "jsonPath":"materials[0].reorderQuantity",
                  "label":"inventory.material.reorderqty",
                  "type":"number",
                  "isRequired":true,
                  "isDisabled":false,
                  "patternErrorMsg":""
               },
               {
                  "name":"materialControlType",
                  "jsonPath":"materials[0].lotControl",
                  "label":"inventory.material.lot",
                  "type":"checkbox",
                  "defaultValue":true,
                  "isRequired":false,
                  "isDisabled":false,
                  "patternErrorMsg":""
               },
               {
                  "name":"serialNumberMandatory",
                  "jsonPath":"materials[0].serialNumber",
                  "label":"inventory.material.serialno",
                  "defaultValue":true,
                  "type":"checkbox",
                  "isRequired":false,
                  "isDisabled":false,
                  "patternErrorMsg":""
               },
               {
                  "name":"shelfLifeControlType",
                  "jsonPath":"materials[0].shelfLifeControl",
                  "label":"inventory.material.shelflife",
                  "defaultValue":true,
                  "type":"checkbox",
                  "isRequired":false,
                  "isDisabled":false,
                  "patternErrorMsg":""
               },
               {
                  "name":"scrapable",
                  "jsonPath":"materials[0].scrapable",
                  "label":"inventory.material.scrapable",
                  "defaultValue":true,
                  "type":"checkbox",
                  "isRequired":false,
                  "isDisabled":false,
                  "patternErrorMsg":""
               }
            ]
         },
         {
            "name":"Specification",
            "label":"inventory.material.specification",
            "fields":[
               {
                  "name":"model",
                  "jsonPath":"materials[0].model",
                  "label":"inventory.material.model",
                  "type":"text",
                  "isRequired":false,
                  "isDisabled":false,
                  "patternErrorMsg":""
               },
               {
                  "name":"manufacturePartNo",
                  "jsonPath":"materials[0].manufacturePartNo",
                  "label":"inventory.material.manufactureno",
                  "type":"text",
                  "isRequired":false,
                  "isDisabled":false,
                  "patternErrorMsg":""
               },
               {
                  "name":"techincalSpecs",
                  "jsonPath":"materials[0].techincalSpecs",
                  "label":"inventory.material.technicalspecification",
                  "type":"textarea",
                  "isRequired":false,
                  "isDisabled":false,
                  "patternErrorMsg":""
               },
               {
                  "name":"termsOfDelivery",
                  "jsonPath":"materials[0].termsOfDelivery",
                  "label":"inventory.material.termsofdelivery",
                  "type":"textarea",
                  "isRequired":false,
                  "isDisabled":false,
                  "patternErrorMsg":""
               }
            ]
         }
      ],
      "url":"/inventory-services/materials/_create",
      "tenantIdRequired":true
   },
   "inventory.view":{
      "numCols":4,
      "useTimestamp":true,
      "objectName":"materials",
      "url":"/inventory-services/materials/_search",
      "title":"inventory.material.title",
      "groups":[
           {
              "name":"View Material",
              "label":"inventory.create.group.title.viewmaterial",
              "fields":[
                {
                   "name":"code",
                   "jsonPath":"materials[0].code",
                   "label":"inventory.material.code",
                   "type":"text",
                   "isRequired":false,
                   "isDisabled":false,
                   "maxLength":50,
                   "patternErrorMsg":""
                },
                 {
                    "name":"oldCode",
                    "jsonPath":"materials[0].oldCode",
                    "label":"inventory.material.oldcode",
                    "type":"text",
                    "isRequired":false,
                    "isDisabled":false,
                    "maxLength":50,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"code",
                    "jsonPath":"materials[0].materialType.code",
                    "label":"inventory.material.materialtype",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":"",
                    "url":"/egov-mdms-service/v1/_get?&moduleName=inventory&masterName=MaterialType|$..code|$..name"
                 },
                 {
                    "name":"name",
                    "jsonPath":"materials[0].name",
                    "label":"inventory.material.name",
                    "type":"text",
                    "isRequired":true,
                    "isDisabled":false,
                    "maxLength":50,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"description",
                    "jsonPath":"materials[0].description",
                    "label":"inventory.material.description",
                    "type":"textarea",
                    "isRequired":true,
                    "isDisabled":false,
                    "maxLength":1000,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"name",
                    "jsonPath":"materials[0].baseUom.code",
                    "label":"inventory.material.baseuom",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":"",
                    "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Uom|$..code|$..description"
                 },
                 {
                    "name":"inventoryType",
                    "jsonPath":"materials[0].inventoryType",
                    "defaultValue":[
                      {key: null, value: "-- Please Select --"},
                      {
                         "key":"Asset",
                         "value":"Asset"
                      },
                      {
                         "key":"Consumable",
                         "value":"Consumable"
                      }
                    ],
                    "label": "inventory.material.inventorytype",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":"",
                    "showHideFields": [{
                        "ifValue": "Asset",
                        "hide": [],
                        "show": [{
                            "name": "assetCategory",
                            "isGroup": false,
                            "isField": true
                        }]
                    }]
                 },
                 {
                    "name":"assetCategory",
                    "jsonPath":"materials[0].assetCategory.code",
                    "label":"inventory.material.assetcategory",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "hide":true,
                    "patternErrorMsg":"",
                    "url":"/egov-mdms-service/v1/_get?tenantId=default&moduleName=ASSET&masterName=AssetCategory|$..code|$..name"
                 },
                 {
                    "name":"status",
                    "jsonPath":"materials[0].status",
                    "label":"inventory.material.status",
                    "type":"text",
                    "isRequired":false,
                    "isDisabled":false,
                    "maxLength":50,
                    "patternErrorMsg":""
                 }
              ]
           },
           {
              "name":"Material Map To Store",
              "label":"inventory.material.maptostore",
              "fields":[
                  {
                    "name":"department",
                    "pattern":"",
                    "type":"singleValueList",
                    "jsonPath":"departmentMaster",
                    "isRequired":false,
                    "isDisabled":false,
                    "isHidden":true,
                    "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Department|$..code|$..name"
                  },

                 {
                    "type":"tableList",
                    "jsonPath":"materials[0].storeMapping",
                    "tableList":{
                       actionsNotRequired:true,
                       "header":[
                          {
                             "label":"Store Name"
                          },
                          // {
                          //    "label":"Department Name"
                          // },
                          {
                             "label":"Account Code"
                          },
                          {
                             "label":"Active"
                          }
                       ],
                       "values":[
                         {
                            "name":"store",
                            "pattern":"",
                            "type":"singleValueList",
                            "jsonPath":"materials[0].storeMapping[0].store.code",
                            "isRequired":true,
                            "isDisabled":true,
                            "url":"inventory-services/stores/_search?|$.stores[*].code|$.stores[*].name|$.stores[*].department",
                            "depedants":[
                               {
                                 "jsonPath":"materials[0].storeMapping[0].department.code",
                                 "type":"textField",
                                 "valExp":"getValFromDropdownData('materials[0].storeMapping[*].store.code', getVal('materials[0].storeMapping[*].store.code'), 'others[0].code')"
                               },
                               {
                                  "jsonPath":"materials[0].storeMapping[0].department.name",
                                  "type":"textField",
                                  "valExp":"getValFromDropdownData('departmentMaster', getVal('materials[0].storeMapping[*].department.code'), 'value')"
                               }
                            ]
                         },
                        //  {
                        //     "name":"department",
                        //     "pattern":"",
                        //     "type":"text",
                        //     "jsonPath":"materials[0].storeMapping[0].department.name",
                        //     "isRequired":true,
                        //     "isDisabled":true
                        //  },
                         {
                            "name":"accountcode",
                            "pattern":"",
                            "type":"singleValueList",
                            "jsonPath":"materials[0].storeMapping[0].chartofAccount.glCode",
                            "isRequired":true,
                            "isDisabled":true,
                            "url": "/egf-master/chartofaccounts/_search?|$.chartOfAccounts[*].glcode|$.chartOfAccounts[*].name"
                         },
                         {
                            "name":"active",
                            "pattern":"",
                            "type":"checkbox",
                            "defaultValue":true,
                            "label":"",
                            "jsonPath":"materials[0].storeMapping[0].active",
                            "isRequired":false,
                            "isDisabled":true
                         }
                       ]
                    }
                 }
              ]
           },
           {
              "name":"Puchasing Information",
              "label":"inventory.material.purchasinginfo",
              "fields":[
                 {
                    "name":"code",
                    "jsonPath":"materials[0].purchaseUom.code",
                    "label":"inventory.material.purchaseuom",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":"",
                    "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Uom|$..code|$..description"
                 },
                 {
                    "name":"accountcode",
                    "pattern":"",
                    "type":"singleValueList",
                    "jsonPath":"materials[0].expenseAccount.glCode",
                    "label":"inventory.material.expenseacctcode",
                    "isRequired":false,
                    "isDisabled":false,
                    "url": "/egf-master/chartofaccounts/_search?|$.chartOfAccounts[*].glcode|$.chartOfAccounts[*].name"
                 }
              ]
           },
           {
              "name":"Stocking Information",
              "label":"inventory.material.stockinginfo",
              "fields":[
                 {
                    "name":"materialClass",
                    "jsonPath":"materials[0].materialClass",
                    "label":"inventory.material.usageclass",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":"",
                    defaultValue:[
                      {key: null, value: "-- Please Select --"},
                      {
                         "key":"HighUsage",
                         "value":"High usage"
                      },
                      {
                         "key":"MediumUsage",
                         "value":"Medium usage"
                      },
                      {
                         "key":"LowUsage",
                         "value":"Low usage"
                      }
                    ]
                 },
                 {
                    "name":"name",
                    "jsonPath":"materials[0].stockingUom.code",
                    "label":"inventory.material.stockinguom",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":"",
                    "url": "/egf-master/chartofaccounts/_search?|$.chartOfAccounts[*].glcode|$.chartOfAccounts[*].name"
                 },
                 {
                    "name":"minQuantity",
                    "jsonPath":"materials[0].minQuantity",
                    "label":"inventory.material.minqty",
                    "type":"number",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"maxQuantity",
                    "jsonPath":"materials[0].maxQuantity",
                    "label":"inventory.material.maxqty",
                    "type":"number",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"reorderLevel",
                    "jsonPath":"materials[0].reorderLevel",
                    "label":"inventory.material.reorderlevel",
                    "type":"number",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"reorderQuantity",
                    "jsonPath":"materials[0].reorderQuantity",
                    "label":"inventory.material.reorderqty",
                    "type":"number",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"materialControlType",
                    "jsonPath":"materials[0].lotControl",
                    "label":"inventory.material.lot",
                    "type":"checkbox",
                    "defaultValue":true,
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"serialNumberMandatory",
                    "jsonPath":"materials[0].serialNumber",
                    "label":"inventory.material.serialno",
                    "defaultValue":true,
                    "type":"checkbox",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"shelfLifeControlType",
                    "jsonPath":"materials[0].shelfLifeControl",
                    "label":"inventory.material.shelflife",
                    "defaultValue":true,
                    "type":"checkbox",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"scrapable",
                    "jsonPath":"materials[0].scrapable",
                    "label":"inventory.material.scrapable",
                    "defaultValue":true,
                    "type":"checkbox",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 }
              ]
           },
           {
              "name":"Specification",
              "label":"inventory.material.specification",
              "fields":[
                 {
                    "name":"model",
                    "jsonPath":"materials[0].model",
                    "label":"inventory.material.model",
                    "type":"text",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"manufacturePartNo",
                    "jsonPath":"materials[0].manufacturePartNo",
                    "label":"inventory.material.manufactureno",
                    "type":"text",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"techincalSpecs",
                    "jsonPath":"materials[0].techincalSpecs",
                    "label":"inventory.material.technicalspecification",
                    "type":"textarea",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"termsOfDelivery",
                    "jsonPath":"materials[0].termsOfDelivery",
                    "label":"inventory.material.termsofdelivery",
                    "type":"textarea",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 }
              ]
           }
       ],
      "tenantIdRequired":true,
      "url":"/inventory-services/materials/_search?code={code}"
   },
   "inventory.update":{
      "numCols":4,
      "useTimestamp":true,
      "objectName":"materials",
      "url":"/inventory-services/materials/_search",
      "title":"inventory.material.title",
      "groups":[
           {
              "name":"Update Material",
              "label":"inventory.create.group.title.updatematerial",
              "fields":[
                {
                   "name":"code",
                   "jsonPath":"materials[0].code",
                   "label":"inventory.material.code",
                   "type":"text",
                   "isRequired":false,
                   "isDisabled":true,
                   "maxLength":50,
                   "patternErrorMsg":""
                },
                 {
                    "name":"oldCode",
                    "jsonPath":"materials[0].oldCode",
                    "label":"inventory.material.oldcode",
                    "type":"text",
                    "isRequired":false,
                    "isDisabled":false,
                    "maxLength":50,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"code",
                    "jsonPath":"materials[0].materialType.code",
                    "label":"inventory.material.materialtype",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":"",
                    "url":"/egov-mdms-service/v1/_get?&moduleName=inventory&masterName=MaterialType|$..code|$..name"
                 },
                 {
                    "name":"name",
                    "jsonPath":"materials[0].name",
                    "label":"inventory.material.name",
                    "type":"text",
                    "isRequired":true,
                    "isDisabled":false,
                    "maxLength":50,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"description",
                    "jsonPath":"materials[0].description",
                    "label":"inventory.material.description",
                    "type":"textarea",
                    "isRequired":true,
                    "isDisabled":false,
                    "maxLength":1000,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"name",
                    "jsonPath":"materials[0].baseUom.code",
                    "label":"inventory.material.baseuom",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":"",
                    "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Uom|$..code|$..description"
                 },
                 {
                    "name":"inventoryType",
                    "jsonPath":"materials[0].inventoryType",
                    "defaultValue":[
                      {key: null, value: "-- Please Select --"},
                      {
                         "key":"Asset",
                         "value":"Asset"
                      },
                      {
                         "key":"Consumable",
                         "value":"Consumable"
                      }
                    ],
                    "label": "inventory.material.inventorytype",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":"",
                    "showHideFields": [{
                        "ifValue": "Asset",
                        "hide": [],
                        "show": [{
                            "name": "assetCategory",
                            "isGroup": false,
                            "isField": true
                        }]
                    }]
                 },
                 {
                    "name":"assetCategory",
                    "jsonPath":"materials[0].assetCategory.code",
                    "label":"inventory.material.assetcategory",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "isHidden":true,
                    "patternErrorMsg":"",
                    "url":"/egov-mdms-service/v1/_get?tenantId=default&moduleName=ASSET&masterName=AssetCategory|$..code|$..name"
                 },
                 {
                    "name":"status",
                    "jsonPath":"materials[0].status",
                    "label":"inventory.material.status",
                    "type":"singleValueList",
                    "defaultValue":[
                      {key: null, value: "-- Please Select --"},
                      {key:"Active", value:"Active"},
                      {key:"Withdrawn", value:"Withdrawn"},
                      {key:"Obsolete", value:"Obsolete"},
                      {key:"Inactive", value:"Inactive"}
                    ],
                    "isRequired":true,
                    "isDisabled":false,
                    "maxLength":50,
                    "patternErrorMsg":""
                 }
              ]
           },
           {
              "name":"Material Map To Store",
              "label":"inventory.material.maptostore",
              "fields":[
                  {
                    "name":"department",
                    "pattern":"",
                    "type":"singleValueList",
                    "jsonPath":"departmentMaster",
                    "isRequired":false,
                    "isDisabled":false,
                    "hide":true,
                    "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Department|$..code|$..name"
                  },

                 {
                    "type":"tableList",
                    "jsonPath":"materials[0].storeMapping",
                    "tableList":{
                       "header":[
                          {
                             "label":"Store Name"
                          },
                          {
                             "label":"Department Name"
                          },
                          {
                             "label":"Account Code"
                          },
                          {
                             "label":"Active"
                          }
                       ],
                       "values":[
                         {
                            "name":"store",
                            "pattern":"",
                            "type":"singleValueList",
                            "jsonPath":"materials[0].storeMapping[0].store.code",
                            "isRequired":true,
                            "isDisabled":false,
                            "url":"inventory-services/stores/_search?|$.stores[*].code|$.stores[*].name|$.stores[*].department",
                            "depedants":[
                               {
                                  "jsonPath":"materials[0].storeMapping[0].department.code",
                                  "type":"textField",
                                  "valExp":"getValFromDropdownData('materials[0].storeMapping[*].store.code', getVal('materials[0].storeMapping[*].store.code'), 'others[0].code')"
                               }
                              //  ,
                              //  {
                              //     "jsonPath":"materials[0].storeMapping[0].department.name",
                              //     "type":"textField",
                              //     "valExp":"getValFromDropdownData('departmentMaster', getVal('materials[0].storeMapping[*].department.code'), 'value')"
                              //  }
                            ]
                         },
                         {
                            "name":"department",
                            "pattern":"",
                            "type":"singleValueList",
                            "jsonPath":"materials[0].storeMapping[0].department.code",
                            "isRequired":true,
                            "isDisabled":true,
                            "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Department|$..code|$..name"
                         },
                         {
                            "name":"accountcode",
                            "pattern":"",
                            "type":"singleValueList",
                            "jsonPath":"materials[0].storeMapping[0].chartofAccount.glCode",
                            "isRequired":true,
                            "isDisabled":false,
                            "url": "/egf-master/chartofaccounts/_search?|$.chartOfAccounts[*].glcode|$.chartOfAccounts[*].name"
                         },
                         {
                            "name":"active",
                            "pattern":"",
                            "type":"checkbox",
                            "defaultValue":true,
                            "label":"",
                            "jsonPath":"materials[0].storeMapping[0].active",
                            "isRequired":false,
                            "isDisabled":false
                         }
                       ]
                    }
                 }
              ]
           },
           {
              "name":"Puchasing Information",
              "label":"inventory.material.purchasinginfo",
              "fields":[
                 {
                    "name":"code",
                    "jsonPath":"materials[0].purchaseUom.code",
                    "label":"inventory.material.purchaseuom",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":"",
                    "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Uom|$..code|$..description"
                 },
                 {
                    "name":"accountcode",
                    "pattern":"",
                    "type":"singleValueList",
                    "jsonPath":"materials[0].expenseAccount.glCode",
                    "label":"inventory.material.expenseacctcode",
                    "isRequired":false,
                    "isDisabled":false,
                    "url": "/egf-master/chartofaccounts/_search?|$.chartOfAccounts[*].glcode|$.chartOfAccounts[*].name"
                 }
              ]
           },
           {
              "name":"Stocking Information",
              "label":"inventory.material.stockinginfo",
              "fields":[
                 {
                    "name":"materialClass",
                    "jsonPath":"materials[0].materialClass",
                    "label":"inventory.material.usageclass",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":"",
                    defaultValue:[
                      {key: null, value: "-- Please Select --"},
                      {
                         "key":"HighUsage",
                         "value":"High usage"
                      },
                      {
                         "key":"MediumUsage",
                         "value":"Medium usage"
                      },
                      {
                         "key":"LowUsage",
                         "value":"Low usage"
                      }
                    ]
                 },
                 {
                    "name":"name",
                    "jsonPath":"materials[0].stockingUom.code",
                    "label":"inventory.material.stockinguom",
                    "type":"singleValueList",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":"",
                    "url":"/egov-mdms-service/v1/_get?&moduleName=common-masters&masterName=Uom|$..code|$..description"
                 },
                 {
                    "name":"minQuantity",
                    "jsonPath":"materials[0].minQuantity",
                    "label":"inventory.material.minqty",
                    "type":"number",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"maxQuantity",
                    "jsonPath":"materials[0].maxQuantity",
                    "label":"inventory.material.maxqty",
                    "type":"number",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"reorderLevel",
                    "jsonPath":"materials[0].reorderLevel",
                    "label":"inventory.material.reorderlevel",
                    "type":"number",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"reorderQuantity",
                    "jsonPath":"materials[0].reorderQuantity",
                    "label":"inventory.material.reorderqty",
                    "type":"number",
                    "isRequired":true,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"materialControlType",
                    "jsonPath":"materials[0].lotControl",
                    "label":"inventory.material.lot",
                    "type":"checkbox",
                    "defaultValue":true,
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"serialNumberMandatory",
                    "jsonPath":"materials[0].serialNumber",
                    "label":"inventory.material.serialno",
                    "defaultValue":true,
                    "type":"checkbox",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"shelfLifeControlType",
                    "jsonPath":"materials[0].shelfLifeControl",
                    "label":"inventory.material.shelflife",
                    "defaultValue":true,
                    "type":"checkbox",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"scrapable",
                    "jsonPath":"materials[0].scrapable",
                    "label":"inventory.material.scrapable",
                    "defaultValue":true,
                    "type":"checkbox",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 }
              ]
           },
           {
              "name":"Specification",
              "label":"inventory.material.specification",
              "fields":[
                 {
                    "name":"model",
                    "jsonPath":"materials[0].model",
                    "label":"inventory.material.model",
                    "type":"text",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"manufacturePartNo",
                    "jsonPath":"materials[0].manufacturePartNo",
                    "label":"inventory.material.manufactureno",
                    "type":"text",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"techincalSpecs",
                    "jsonPath":"materials[0].techincalSpecs",
                    "label":"inventory.material.technicalspecification",
                    "type":"textarea",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 },
                 {
                    "name":"termsOfDelivery",
                    "jsonPath":"materials[0].termsOfDelivery",
                    "label":"inventory.material.termsofdelivery",
                    "type":"textarea",
                    "isRequired":false,
                    "isDisabled":false,
                    "patternErrorMsg":""
                 }
              ]
           }
       ],
      "url":"/inventory-services/materials/_update",
      "tenantIdRequired":true,
      "searchUrl":"/inventory-services/materials/_search?code={code}"
   }
}
export default dat;
