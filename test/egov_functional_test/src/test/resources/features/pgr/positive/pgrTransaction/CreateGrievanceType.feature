Feature: Creating a Grievance Type

  Scenario Outline: Create a Grievance Type

    ####### Create A Grievance Category #######
    ### On Login Screen ###
    Given user on Login screen verifies signInText has visible value Sign In
    And user on Login screen types on username value narasappa
    And user on Login screen types on password value demo
    And user on Login screen clicks on signIn

    ### On Homepage Screen ###
    And user on Home screen will wait until the page loads
    And user on Home screen will see the menu
    And user on Home screen clicks on menu
    And user on Home screen types on menuSearch value Create Category
    And user on Home screen clicks on firstMenuItem

    ### On Grievance Screen ###
    And user on Grievance screen verifies text has visible value Grievance Category
    And user on Grievance screen types on categoryName value --"Category ", 5 random characters
    And user on Grievance screen copies the categoryName to categoryName
    And user on Grievance screen types on categoryCode value --5 random numbers
    And user on Grievance screen clicks on createButton
    And user on Grievance screen clicks on close

    ### On Homepage Screen ###
    And user on Home screen will wait until the page loads
    And user on Home screen will see the menu
    And user on Home screen clicks on menu
    And user on Home screen types on menuSearch value Search Category
    And user on Home screen clicks on firstMenuItem

    ### On Search Category Screen ###
    And user on Grievance screen types on grievanceSearch value categoryName
    And user on Grievance screen verifies text has visible value categoryName

    ####### Create A GrievanceType #######
    ### On Homepage Screen ###
    And user on Home screen will wait until the page loads
    And user on Home screen will see the menu
    And user on Home screen clicks on menu
    And user on Home screen types on menuSearch value Create Grievance Type
    And user on Home screen clicks on firstMenuItem

    ####### Create GrievanceType with Above Created Grievance Category #######
    ### On Create Grievance Screen ###
    And user on Grievance screen types on grievanceTypeName value --"Grievance Type ", 5 random characters
    And user on Grievance screen copies the grievanceTypeName to grievanceTypeName
    And user on Grievance screen types on grievanceTypeCode value --5 random numbers
    And user on Grievance screen types on slaHours value --1 random numbers
    And user on Grievance screen selects on grievanceTypeCategorySelect value categoryName
    And user on Grievance screen clicks on createButton
    And user on Grievance screen clicks on close

    ### On Homepage Screen ###
    And user on Home screen will wait until the page loads
    And user on Home screen will see the menu
    And user on Home screen clicks on menu
    And user on Home screen types on menuSearch value View Grievance
    And user on Home screen clicks on firstMenuItem

    ### On View GrievanceType Screen ###
    And user on Grievance screen types on grievanceSearch value grievanceTypeName
    And user on Grievance screen verifies text has visible value grievanceTypeName

    ####### Creating a Router with Above Created GrievanceType #######
    ### On Homepage Screen ###
    And user on Home screen will wait until the page loads
    And user on Home screen will see the menu
    And user on Home screen clicks on menu
    And user on Home screen types on menuSearch value Create Router
    And user on Home screen clicks on firstMenuItem

    ### On Create Router Screen ###
    And user on Grievance screen will wait until the page loads
    And user on Grievance screen types on grievanceType suggestion box with value grievanceTypeName
    And user on Grievance screen selects on boundaryType value Ward
    And user on Grievance screen types on routerBoundary suggestion box with value Election Ward No 31
    And user on Grievance screen types on routerPosition suggestion box with value Acc_Senior Account_1
    And user on Grievance screen clicks on create
    And user on Grievance screen clicks on close

    ####### Register A Complaint with Above Created GrievanceType & Grievance Category #######
    ### On Homepage Screen ###
    And user on Home screen will wait until the page loads
    And user on Home screen will see the menu
    And user on Home screen clicks on menu
    And user on Home screen types on menuSearch value Officials Register Grievance
    And user on Home screen clicks on firstMenuItem

    ### On Create Complaint Grievance Screen Entering Contact Information ###
    And user on Grievance screen verifies contactInfo has visible value Contact Information
    And user on Grievance screen selects on receivingMode value Call
    And user on Grievance screen types on name value --"User ", 4 random characters
    And user on Grievance screen types on mobileNumber value --"1",9 Digit Number
    And user on Grievance screen types on email value --email

    ### On Create Complaint Grievance Screen Entering Grievance Information ###
    And user on Grievance screen selects on grievanceCategory value categoryName
    And user on Grievance screen selects on grievanceType value grievanceTypeName

    ### On Create Complaint Grievance Screen Entering More Details ###
    And user on Grievance screen types on grievanceDetails value Grievance Details
    And user on Grievance screen types on grievanceLocation suggestion box with value Bank Road
    And user on Grievance screen uploads on selectPhoto value pgrDocument.jpg
    And user on Grievance screen clicks on create

    ### On Create Complaint Grievance Screen verifying the details ###
    And user on Grievance screen copies the complaintNum to applicationNumber
    And user on Grievance screen clicks on view
    And user on Home screen will wait until the page loads
    And user on Grievance screen copies the userName to user

    ### Logout ###
    And Intent:LogoutIntentTest

    ### Login ###
    And DataIntent:LoginIntent
      | user |
      | demo |

    ### On HomePage Screen ###
    And user on Home screen types on dashBoardSearch with above applicationNumber
    And user on Home screen opens on dashBoardSearch with above applicationNumber

    ### On Grievance Screen ###
    And user on Home screen will wait until the page loads
    And user on Grievance screen selects on changeStatus value <status>
    And user on Grievance screen types on comments value Comments
    And user on Grievance screen clicks on submitButton
    And user on Grievance screen clicks on okButton
    And user on Home screen will wait until the page loads
    And user on Grievance screen clicks on homeButton

    ### On HomePage Screen ###
    And user on Home screen will wait until the page loads
    And user on Home screen will see the myTasks
    And user on Home screen clicks on menu
    And user on Home screen types on menuSearch value Search Grievance
    And user on Home screen clicks on firstMenuItem

    ### On SearchGrievance Screen ###
    And user on SearchGrievance screen types on crnComplaintNumber with above applicationNumber
    And user on SearchGrievance screen clicks on crnSearchButton
    And user on SearchGrievance screen verifies crnStatus has visible value <status>

    ### Logout ###
    And Intent:LogoutIntentTest

    Examples:
      | status    |
      | COMPLETED |
      | REJECTED  |





