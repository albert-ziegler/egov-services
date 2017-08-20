import React, {Component} from 'react';
import {connect} from 'react-redux';

import {Grid, Row, Col, Table, DropdownButton} from 'react-bootstrap';
import {Card, CardHeader, CardText} from 'material-ui/Card';
import FloatingActionButton from 'material-ui/FloatingActionButton';
import Chip from 'material-ui/Chip';
import FontIcon from 'material-ui/FontIcon';
import {RadioButton, RadioButtonGroup} from 'material-ui/RadioButton';
import Upload from 'material-ui-upload/Upload';
import FlatButton from 'material-ui/FlatButton';
import TextField from 'material-ui/TextField';
import {blue800, red500,white} from 'material-ui/styles/colors';
import DatePicker from 'material-ui/DatePicker';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';
import Checkbox from 'material-ui/Checkbox';
import RaisedButton from 'material-ui/RaisedButton';
import {translate} from '../../../../common/common';
import Dialog from 'material-ui/Dialog';
import Api from '../../../../../api/api';


var flag = 0;
const styles = {
  errorStyle: {
    color: red500
  },
  underlineStyle: {
 
  },
  underlineFocusStyle: {

  },
  floatingLabelStyle: {
    color: "#354f57"
  },
  floatingLabelFocusStyle: {
    color:"#354f57"
  },
  customWidth: {
    width:100
  },
  checkbox: {
    marginBottom: 0,
    marginTop:15
  },
  uploadButton: {
   verticalAlign: 'middle',
 },
 uploadInput: {
   cursor: 'pointer',
   position: 'absolute',
   top: 0,
   bottom: 0,
   right: 0,
   left: 0,
   width: '100%',
   opacity: 0,
 },
 floatButtonMargin: {
   marginLeft: 20,
   fontSize:12,
   width:30,
   height:30
 },
 iconFont: {
   fontSize:17,
   cursor:'pointer'
 },
 radioButton: {
    marginBottom:0,
  },
actionWidth: {
  width:160
},
reducePadding: {
  paddingTop:4,
  paddingBottom:0
},
noPadding: {
  paddingBottom:0,
  paddingTop:0
},
noMargin: {
  marginBottom: 0
},
textRight: {
  textAlign:'right'
},
chip: {
  marginTop:4
}
};

const  getNameById = (object, code, property = "") => {
  if (code == "" || code == null) {
        return "";
    }
    for (var i = 0; i < object.length; i++) {
        if (property == "") {
            if (object[i].code == code) {
                return object[i].name;
            }
        } else {
            if (object[i].hasOwnProperty(property)) {
                if (object[i].code == code) {
                    return object[i][property];
                }
            } else {
                return "";
            }
        }
    }
    return "";
}

var totalFloors = [];

var temp = [];

class FloorDetails extends Component {
	
  constructor(props) {
    super(props);
    this.state= {
		unitType:[{code:"FLAT", name:'Flat'}, {code:"ROOM", name:'Room'}],
		floorNumber:[{code:1, name:'Basement-3'},{code:2, name:'Basement-2'},{code:3, name:'Basement-1'},{code:4, name:'Ground Floor'}],
		rooms: [],
		structureclasses:[],
		occupancies:[],
		usages:[],
		hasLengthWidth: false,
		roomInFlat:[{code:1, name:'Yes'}, {code:2, name:'No'}],
		newFloorError : false,
		negativeValue : false,
		occupantNames : [],
		dialogOpen: false
    }
  }

  componentDidMount() {
    //call boundary service fetch wards,location,zone data
    var currentThis = this;

        Api.commonApiPost('pt-property/property/structureclasses/_search').then((res)=>{
          console.log(res);
          currentThis.setState({structureclasses: res.structureClasses})
        }).catch((err)=> {
          console.log(err)
        })

        Api.commonApiPost('pt-property/property/occuapancies/_search').then((res)=>{
          console.log(res);
          currentThis.setState({occupancies : res.occuapancyMasters})
        }).catch((err)=> {
          console.log(err)
        })

        Api.commonApiPost('pt-property/property/usages/_search').then((res)=>{
          console.log(res);
          currentThis.setState({usages : res.usageMasters})
        }).catch((err)=> {
          console.log(err)
        })
		
		var temp = this.state.floorNumber;
		
		for(var i=5;i<=34;i++){
			var label = 'th';
			if((i-4)==1){
				label = 'st';
			} else if ((i-4)==2){
				label = 'nd';
			} else if ((i-4)==3){
				label = 'rd';
			}
			var commonFloors = {
				code:i,
				name:(i-4)+label+" Floor"
			}
			temp.push(commonFloors);
			
		}
		
		this.setState({
				floorNumber:temp
			});
	
		this.createFloorObject();
		
		this.props.initForm();
  }
  
  createFloorObject = () => {
	  
		let {floorDetails} = this.props;
		
		floorDetails.floorsArr = [];
			  
	    var allRooms = floorDetails.floors;		
		
		if(!allRooms){
			return false;
		}
		
		if(allRooms) {
			for(var i = 0; i<allRooms.length;i++){
			if(!allRooms[i].hasOwnProperty('isAuthorised')){
					allRooms[i].isAuthorised = true;
			}	
			if(allRooms[i].isStructured == 'YES'){
				allRooms[i].isStructured = true;
			} else {
				allRooms[i].isStructured = false;
			}
		}
		
		}
		
		var rooms = allRooms.filter(function(item, index, array){
			if(!item.hasOwnProperty('flatNo')){
				return item;
			}
		});
		
		rooms.map((item, index)=>{
			var floor = {
				  floorNo:'',
				  units:[]
				}
			  floor.floorNo = item.floorNo;
			  floor.units.push(item);
			  floorDetails.floorsArr.push(floor);
		})
		
		var flats = allRooms.filter(function(item, index, array){
			if(item.hasOwnProperty('flatNo')){
				return item;
			}
		});		
		
		var flatNos = flats.map(function(item, index, array){
			return item.flatNo;
		});
		
		flatNos = flatNos.filter(function(item, index, array){
			return index == array.indexOf(item);
		});
		
		var floorNos = flats.map(function(item, index, array){
			return item.floorNo;
		});
		
		floorNos = floorNos.filter(function(item, index, array){
			return index == array.indexOf(item);
		});
		
		
		var flatsArray = [];
		
		
		for(var i =0;i<flatNos.length;i++){
			var local = {
				units : []
			}
			for(var j = 0;j<flats.length;j++){
				if(flats[j].flatNo == flatNos[i]) {
					local.units.push(flats[j])
				}
			}
			flatsArray.push(local);
		}
		
		
	var finalFloors = [];
		for(let j=0;j<floorNos.length;j++){
			
			var local = {
				flats : []
			}
			for(let k =0;k<flatsArray.length;k++){
				for(let l=0;l<flatsArray[k].units.length;l++){
					if(flatsArray[k].units[l].floorNo == floorNos[j]){
						for(let m = 0 ; m<flatNos.length;m++){
							if(flatNos[m] == flatsArray[k].units[l].flatNo){
								local.flats.push(flatsArray[k].units[l]);
							}
						}
							
					}
				}
			}
			finalFloors.push(local);
		}
		
		var finalFlats = [];
		
		for(let x = 0;x<finalFloors.length;x++){
			var temp = finalFloors[x].flats;
			for(var i =0;i<finalFloors[x].flats.length;i++){
				var local= {
					units:[]
				};
				for(var j = 0;j<temp.length;j++){
					if(temp[j].flatNo == finalFloors[x].flats[i].flatNo) {
						local.units.push(temp[j])
					}
				}
			finalFlats.push(local);
			}
			
		}
		
		finalFlats = finalFlats.map((item, index)=>{
			return JSON.stringify(item);
		})
		
		finalFlats = finalFlats.filter((item, index, array)=>{
			return index == array.indexOf(item);
		})
		
		finalFlats = finalFlats.map((item, index)=>{
			return JSON.parse(item);
		})
		
		finalFlats.map((item, index)=>{
			let floor = {
				  floorNo:'',
				  units:[]
				}
			  floor.floorNo = item.units[0].floorNo;
			  floor.units.push(item);
			  floorDetails.floorsArr.push(floor);
		})
		
  }
  
formatDate(date){
	
	var day = (date.getDate() < 10) ? ('0'+date.getDate()) : date.getDate();
	var month = ((date.getMonth() + 1)<10) ? ('0'+(date.getMonth() + 1)) : (date.getMonth() + 1)
	
	return day + "/" + month + "/" + date.getFullYear();
}

getSmallestDate = (DateArray) => {
	
	let {handleChange} = this.props;	

    var SmallestDate = new Date(DateArray[0]);
	
    for(var i = 1; i < DateArray.length; i++)
		{	
			var TempDate = new Date(DateArray[i]);
			if(TempDate < SmallestDate)
			SmallestDate  = TempDate ;
		}
		
		var date = (new Date(SmallestDate).getDate() < 10) ? ('0'+new Date(SmallestDate).getDate()) : new Date(SmallestDate).getDate();
		var month = ((new Date(SmallestDate).getMonth() + 1)<10) ? ('0'+(new Date(SmallestDate).getMonth() + 1)) : (new Date(SmallestDate).getMonth() + 1);
		
		var e = {
			target: {
				value: date + "/" + month + "/" + new Date(SmallestDate).getFullYear()
			}
		}
				
		handleChange(e, 'occupancyDate', false, '');
    return SmallestDate;
}
	
componentDidUpdate(){

}


calcArea = (e, type) => {
		let {floorDetails, handleChangeNextOne, handleChangeFloor} = this.props;
		
		let f = {
			target:{
				value: ''
			}
		}
		
		let hasLW = false
		
		if(floorDetails.hasOwnProperty('floor')) {
			if(type == 'length' && floorDetails.floor.hasOwnProperty('width')){
			
				f.target.value = parseFloat(e.target.value) * parseFloat(floorDetails.floor.width)
		
				if(!f.target.value){ 
					hasLW = false;
				} else {
					hasLW = true;
				}
				
				handleChangeFloor(f, "floor","builtupArea", true, "");
				
			} else if(type == 'width' 	&& floorDetails.floor.hasOwnProperty('length')){
			
				f.target.value = parseFloat(floorDetails.floor.length) * parseFloat(e.target.value)
				
				if(!f.target.value){ 
					hasLW = false;
				} else {
					hasLW = true;
				}
				
				handleChangeFloor(f, "floor","builtupArea", true, "");
				
			} 			
		}

		this.setState({
			hasLengthWidth: hasLW
		});
}

handleAge = (year) => {
/*	var query = {
		fromYear : year,
		toYear: year
	}
	var currentThis = this;
	Api.commonApiPost('/property/depreciations/_search',query).then((res)=>{
	  console.log(res);
	  currentThis.setState({structureclasses: res.structureClasses})
	}).catch((err)=> {
	  console.log(err)
	})*/
}	

calcAssessableArea = (e, type) => {
	
	let {floorDetails, handleChangeNextOne, handleChangeFloor} = this.props;
	
	let f = {
			target:{
				value: ''
			}
		}
	
	if(floorDetails.hasOwnProperty('floor')) {
	
		if(type == 'exempted' && floorDetails.floor.hasOwnProperty('carpetArea')){
			
			if(parseFloat(floorDetails.floor.carpetArea) < parseFloat(e.target.value)) {
				this.setState({
					negativeValue : true
				})
			} else {
				this.setState({
					negativeValue : false
				})
				f.target.value = parseFloat(floorDetails.floor.carpetArea) - parseFloat(e.target.value);
				handleChangeNextOne(f, "floor","assessableArea", false, "");
			}
		
			
		} else if(type == 'carpet'){
			
			if(floorDetails.floor.hasOwnProperty('exemptedArea')) {
				
				if(parseFloat(e.target.value) < parseFloat(floorDetails.floor.exemptedArea) ) {
					this.setState({
						negativeValue : true
					})
				} else {
					this.setState({
						negativeValue : false
					})
					f.target.value = parseFloat(e.target.value) - parseFloat(floorDetails.floor.exemptedArea);
					handleChangeNextOne(f, "floor","assessableArea", false, "");
				}
		
			} else {
				f.target.value = parseFloat(e.target.value);
			
				handleChangeNextOne(f, "floor","assessableArea", false, "");
			}
		
			
			
		} 			
	}	
}

checkFloors =(val) => {
	
	let {floorDetails, noOfFloors} = this.props;
	
	totalFloors = totalFloors.filter(function(elem, index, self) {
		return index == self.indexOf(elem);
	})
	
	if(noOfFloors === undefined) {
			this.setState({
				newFloorError : true
			});
			return false;
	} else {
		this.setState({
				newFloorError : false
			});
	}
		
	if(noOfFloors == totalFloors.length){
		var index = totalFloors.indexOf(val);
		console.log(index);
		if(index == -1) {
			this.setState({
				newFloorError : true
			});
		} else {
			this.setState({
				newFloorError : false
			});
		}
	}
}

getFloors = () => {
	totalFloors = [];
	var alldate = [];
	let {floorDetails, noOfFloors} = this.props;
	var allRooms = floorDetails.floors;
	
	allRooms.map((item, index)=>{
		totalFloors.push(item.floorNo)
		
		var d = item.occupancyDate.split('/');
		d = d[2]+'-'+d[1]+'-'+d[0]
		alldate.push(d);
	})
	
	this.getSmallestDate(alldate);
}

handleDialogOpen = () => {
	this.setState({dialogOpen: true});
};

handleDialogClose = () => {
	this.setState({dialogOpen: false});
};

setOccupantName = (item) => {
	
	var a = item.split(',');
	
	for(var i = 0; i<a.length;i++){
		if(temp.indexOf(a[i]) == -1) {
			temp.push(a[i])
		}
	}
	
	this.setState({
		occupantNames: temp
	})
	
	var e = {
		target: {
			value: temp.toString()
		}
	}
	
	this.props.handleChangeNextOne(e,'floor',"occupierName", false,  /^[a-zA-Z,\s]+$/g)
}

deleteOccupantName = (index) =>{
	
	temp.splice(index, 1)
	
	this.setState({
		occupantNames: temp
	})
	
	var e = {
		target: {
			value: temp.toString()
		}
	}
	
	this.props.handleChangeNextOne(e,'floor',"occupierName", false,  /^[a-zA-Z,\s]+$/g)
	
}
 
  
   render(){
	  
		var _this = this;   
		   
		const renderOption = function(list,listName="") {
			if(list)
			{	list.unshift({code:-1, name:'None'})
				return list.map((item)=>
				{
					return (<MenuItem key={item.code} value={item.code} primaryText={item.name}/>)
				})
			}
		}
	   
	     let {
		  floorDetails,
		  fieldErrors,
		  isFormValid,
		  handleChange,
		  handleChangeNextOne,
		  handleChangeNextTwo,
		  deleteObject,
		  deleteNestedObject,
		  editObject,
		  editIndex,
		  isEditIndex,
		  toggleDailogAndSetText,
		  setLoadingStatus,
		  toggleSnackbarAndSetText,
		  handleChangeFloor,
		  isFloorValid,
		  noOfFloors
				} = this.props;

		let {calcAssessableArea, handleAge, checkFloors} = this;
		let cThis = this;
		
		const occupantNames = () => {
								
				return(
					<div>
						<Col xs={12} md={12}>
							<Row>
								<Col xs={12} md={6}>
									<TextField  className="fullWidth"
									  hintText="Mano, Ranjan"
									  floatingLabelText={translate('pt.create.groups.floorDetails.fields.occupantName')}
									  errorText={fieldErrors.occupantName ? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.flatNo}</span> : ""}
									  value={floorDetails.occupantName ? floorDetails.occupantName : ""}
									  onChange={(e) => {handleChange(e,"occupantName", false,  /^[a-zA-Z,\s]+$/g)}}
									  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
									  underlineStyle={styles.underlineStyle}
									  underlineFocusStyle={styles.underlineFocusStyle}
									  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}/>
								  </Col>
								  <Col xs={12} md={6}>
									<RaisedButton type="button" label="Add Name" disabled={(floorDetails.occupantName && floorDetails.occupantName!='') ? false : true} primary={true} onClick={()=>{
										this.setOccupantName(floorDetails.occupantName)
										var e = {
											target: {
												value:''
											}
										}
										handleChange(e,"occupantName", false,  /^[a-zA-Z,\s]+$/g)
										}
									}/>
								  </Col>
							</Row>		  
						</Col>
						<Table>
							<thead>
								<tr>
									<th>#</th>
									<th>Name</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								{this.state.occupantNames.length !=0 && this.state.occupantNames.map((item, index)=>{
									return(
									<tr key={index}>
										<td>{index+1}</td>
										<td>{item}</td>
										<td><div className="material-icons" onClick={()=>{
											this.deleteOccupantName(index)
										}}>delete</div></td>
									</tr>
									)
								})}
							</tbody>
						</Table>
					</div>
				)
		}
		
		const showDialog = () => {
			return(
				<Dialog
				  title="Occupant Names"
				  modal={false}
				  open={this.state.dialogOpen}
				  onRequestClose={this.handleDialogClose}
				  style={{height:'auto'}}
				  autoScrollBodyContent={true}
				>
					{occupantNames()}
				</Dialog>
			)
		}
		
	   return(
			
			<Card className="uiCard">
				{showDialog()}
                <CardHeader style={styles.reducePadding}  title={<div style={{color:"#354f57", fontSize:18,margin:'8px 0'}}>{translate('pt.create.groups.floorDetails')}</div>} />
				<CardText>
								<Grid fluid>
									<Row>
										 <Col xs={12} md={12}>
											<Row>
												<Row>
													<Col xs={12} md={3} sm={6}>
														<SelectField  className="fullWidth selectOption"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.floorNumber')+ '*'}
														  errorText={fieldErrors.floor ? (fieldErrors.floor.floorNo ? <span style={{position:"absolute", bottom:-41}}>{fieldErrors.floor.floorNo}</span>:"") : ""}
														  value={floorDetails.floor ? floorDetails.floor.floorNo : ""}
														  onChange={(event, index, value) => {
															 (value == -1) ?  value = '' : '';
															  var e = {
																target: {
																  value: value
																}
															  };
															  checkFloors(value)
															  handleChangeFloor(e, "floor","floorNo", true, "")}
														  }
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														>
														{renderOption(_this.state.floorNumber)}
														</SelectField>
													</Col>
													<Col xs={12} md={3} sm={6}>
														 <SelectField  className="fullWidth selectOption"
															floatingLabelText={translate('pt.create.groups.floorDetails.fields.unitType')+ '*'}
															errorText={fieldErrors.floor ? (fieldErrors.floor.unitType ? <span style={{position:"absolute", bottom:-41}}>{fieldErrors.floor.unitType}</span>:"" ): ""}
															value={floorDetails.floor ? floorDetails.floor.unitType : ""}
															onChange={(event, index, value) => {
																(value == -1) ?  value = '' : '';
							
																var e = {
																  target: {
																	value: value
																  }
																};
																
																if(floorDetails.hasOwnProperty('floor')) {
																	floorDetails.floor.electricMeterNo = null;
																	floorDetails.floor.waterMeterNo = null;
																	floorDetails.floor.exemptionReason = null;
																	floorDetails.floor.rentCollected = null;
																	floorDetails.floor.age = '0TO20';
																}
																
																handleChangeFloor(e,"floor" ,"unitType", true, "");
																
															  }
															}
															floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
															underlineStyle={styles.underlineStyle}
															underlineFocusStyle={styles.underlineFocusStyle}
															floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														  >
														  {renderOption(_this.state.unitType)}
														</SelectField>
													</Col>
													{(floorDetails.floor ? (floorDetails.floor.unitType == 'FLAT' ? true: false ): false) &&
															<Col xs={12} md={3} sm={6}>
																 <SelectField  className="fullWidth selectOption"
																	floatingLabelText={translate('pt.create.groups.floorDetails.fields.isRoomFlat')}
																	errorText={fieldErrors.roomInFlat ? (fieldErrors.floor.roomInFlat ? <span style={{position:"absolute", bottom:-41}}>{fieldErrors.floor.roomInFlat}</span>:"" ): ""}
																	value={floorDetails.floor ? floorDetails.floor.roomInFlat : ""}
																	onChange={(event, index, value) => {
																		(value == -1) ?  value = '' : '';
																		
																	    if(value == 1){
																			if(floorDetails.floor.hasOwnProperty('flatNo')){
																				floorDetails.floor.flatNo = '';
																			}
																			this.props.addFloorDepandencyFields('flatNo');
																		} else {
																			this.props.removeFloorDepandencyFields('flatNo');
																			if(floorDetails.floor.hasOwnProperty('flatNo')){
																				delete floorDetails.floor.flatNo;
																			}
																		}
																		
																		var e = {
																		  target: {
																			value: value
																		  }
																		};
																		handleChangeFloor(e,"floor" ,"roomInFlat", false, "");
																		if(value == 2) {
																		  this.setState({addRoom:false});
																		}
																	  }
																	}
																	floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
																	underlineStyle={styles.underlineStyle}
																	underlineFocusStyle={styles.underlineFocusStyle}
																	floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
																  >
																  {renderOption(_this.state.roomInFlat)}																								
																</SelectField>
															</Col>	
													}
													{(floorDetails.floor ? (floorDetails.floor.roomInFlat == '1' ? true: false ): false) && 
																<Col xs={12} md={3} sm={6}>			
																	<TextField  className="fullWidth"
																	  hintText="201"
																	  floatingLabelText={translate('pt.create.groups.floorDetails.fields.flatNo')+' *'}
																	  errorText={fieldErrors.floor ? (fieldErrors.floor.flatNo ? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.flatNo}</span> :""): ""}
																	  value={floorDetails.floor ? floorDetails.floor.flatNo : ""}
																	  onChange={(e) => {handleChangeFloor(e,"floor" ,"flatNo", true, /^\d+$/g)}}
																	  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
																	  underlineStyle={styles.underlineStyle}
																	  underlineFocusStyle={styles.underlineFocusStyle}
																	  maxLength={3}
																	  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}/>
																</Col>
															}
													<Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.unitNumber')+' *'}
														  hintText="102"
														  errorText={fieldErrors.floor ? (fieldErrors.floor.unitNo ? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.unitNo}</span> :""): ""}
														  value={floorDetails.floor ? floorDetails.floor.unitNo : ""}
														  onChange={(e) => {handleChangeFloor(e,"floor" ,"unitNo", true, /^\d{0,3}$/g)}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={3}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>		
													<Col xs={12} md={3} sm={6}>
														<SelectField  className="fullWidth selectOption"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.constructionClass')+' *'}
														  errorText={fieldErrors.floor ?(fieldErrors.floor.structure? <span style={{position:"absolute", bottom:-41}}>{fieldErrors.floor.structure}</span>:"" ): ""}
														  value={floorDetails.floor ? floorDetails.floor.structure : ""}
														  onChange={(event, index, value) => {
															  (value == -1) ?  value = '' : '';
															  var e = {
																target: {
																  value: value
																}
															  };
															  handleChangeFloor(e,"floor" ,"structure", true, "")}
														  }
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														>
															  {renderOption(this.state.structureclasses)}
														</SelectField>
													</Col>
													<Col xs={12} md={3} sm={6}>
														<SelectField  className="fullWidth selectOption"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.usageType')+' *'}
														  errorText={fieldErrors.floor ?(fieldErrors.floor.usage? <span style={{position:"absolute", bottom:-41}}>{fieldErrors.floor.usage}</span>:"" ): ""}
														  value={floorDetails.floor ? floorDetails.floor.usage : ""}
														  onChange={(event, index, value) => {
															  (value == -1) ?  value = '' : '';
															  var e = {
																target: {
																  value: value
																}
															  };
															  handleChangeFloor(e,"floor" ,"usage", true, "")}
														  }
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														>
															  {renderOption(this.state.usages)}

														</SelectField>
													</Col>
													<Col xs={12} md={3} sm={6}>
														<SelectField  className="fullWidth selectOption"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.usageSubType')}
														  errorText={fieldErrors.floor ?(fieldErrors.floor.usageSubType ? <span style={{position:"absolute", bottom:-41}}>{fieldErrors.floor.usageSubType}</span> :""): ""}
														  value={floorDetails.floor ? floorDetails.floor.usageSubType : ""}
														  onChange={(event, index, value) => {
															  (value == -1) ?  value = '' : '';
															  var e = {
																target: {
																  value: value
																}
															  };
															  handleChangeFloor(e,"floor" ,"usageSubType", false, "")}
														  }
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														>
															{renderOption(this.state.usages)}
														</SelectField>
													</Col>
													<Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.firmName')}
														  errorText={fieldErrors.floor ?(fieldErrors.floor.firmName? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.firmName}</span>:"") : ""}
														  value={floorDetails.floor ? floorDetails.floor.firmName : ""}
														  onChange={(e) => {handleChangeFloor(e,"floor" ,"firmName", false, "")}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={20}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>
													<Col xs={12} md={3} sm={6}>
														<SelectField  className="fullWidth selectOption"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.occupancy')+' *'}
														  errorText={fieldErrors.floor ? (fieldErrors.floor.occupancyType?<span style={{position:"absolute", bottom:-41}}>{fieldErrors.floor.occupancyType}</span>:"") : ""}
														  value={floorDetails.floor ? floorDetails.floor.occupancyType : ""}
														  onChange={(event, index, value) => {
															  (value == -1) ?  value = '' : '';
															  var e = {
																target: {
																  value: value
																}
															  };
															  handleChangeFloor(e,"floor" ,"occupancyType", true, "")}
														  }
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														>
															  {renderOption(this.state.occupancies)}
														</SelectField>
													</Col>
													<Col xs={12} md={3} sm={6}>
														<Row>
															<Col xs={12} md={9}>
																<TextField  className="fullWidth"
																  floatingLabelText={translate('pt.create.groups.floorDetails.fields.occupantName')}
																  errorText={fieldErrors.floor ? (fieldErrors.floor.occupierName? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.occupierName}</span> :""): ""}
																  value={floorDetails.floor ? floorDetails.floor.occupierName : ""}
																  onChange={(e) => {handleChangeFloor(e,"floor" , "occupierName", false, /^[a-zA-Z,\s]+$/g)}}
																  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
																  underlineStyle={styles.underlineStyle}
																  underlineFocusStyle={styles.underlineFocusStyle}
																  maxLength={4000}
																  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
																/>
															</Col>
															<Col xs={12} md={3}>
													
																<FloatingActionButton mini={true} disabled= {floorDetails.floor ? (floorDetails.floor.occupierName  && floorDetails.floor.occupierName!='' ? false : true) : true} onClick={()=>{
																	if(floorDetails.hasOwnProperty('floor') && floorDetails.floor.hasOwnProperty('occupierName')) {
																		this.handleDialogOpen();
																		this.setOccupantName(floorDetails.floor.occupierName)
																	}
																	
																}} style={{marginTop:15}}>
																	<i className="material-icons">add</i>
																</FloatingActionButton>
															</Col>
														</Row>
													</Col>
													
												
													
													{(floorDetails.floor ? !getNameById(this.state.occupancies,floorDetails.floor.occupancyType).match('Owner') : true) 
													
													&& <Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.annualRent')}
														  hintText="15000"
														  errorText={fieldErrors.floor ? (fieldErrors.floor.annualRent ? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.annualRent}</span>:""): ""}
														  value={floorDetails.floor ? floorDetails.floor.annualRent : ""}
														  onChange={(e) => {handleChangeFloor(e,"floor" , "annualRent", false, /^\d{3,64}$/g)}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={9}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>}
													{window.location.href.match('dataEntry') && <Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.Arv')}
														  errorText={fieldErrors.floor ? (fieldErrors.floor.arv?<span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.arv}</span>:"") : ""}
														  value={floorDetails.floor ? floorDetails.floor.arv : ""}
														  onChange={(e) => {handleChangeFloor(e,"floor" , "arv", false,'')}}
														  type="number"
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={9}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>}
													<Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.manualArv')}
														  errorText={fieldErrors.floor ? (fieldErrors.floor.manualArv?<span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.manualArv}</span>:"") : ""}
														  value={floorDetails.floor ? floorDetails.floor.manualArv : ""}
														  onChange={(e) => {handleChangeFloor(e,"floor" , "manualArv", false, '')}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={9}
														  type="number"
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>
													<Col xs={12} md={3} sm={6}>
														<DatePicker  className="fullWidth datepicker"
														  formatDate={(date)=> this.formatDate(date)}
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.constructionStartDate')}
														  errorText={fieldErrors.floor ? (fieldErrors.floor.constStartDate ? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.constStartDate}</span> :""): ""}
														  onChange={(event,date) => {
																var day = (date.getDate() < 10) ? ('0'+date.getDate()) : date.getDate();
																var month = ((date.getMonth() + 1)<10) ? ('0'+(date.getMonth() + 1)) : (date.getMonth() + 1)
	

															  var e = {
																target:{
																	value: day + "/" + month + "/" + date.getFullYear()
																}
															  }
															handleAge(date.getFullYear());
															handleChangeFloor(e,"floor" ,"constStartDate", false, "")}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  textFieldStyle={{width: '100%'}}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>
													<Col xs={12} md={3} sm={6}>
														<DatePicker  className="fullWidth datepicker"
														  formatDate={(date)=> this.formatDate(date)}
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.constructionEndDate')+' *'}
														  errorText={fieldErrors.floor ? (fieldErrors.floor.constCompletionDate ? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.constCompletionDate}</span> :""): ""}
														  onChange={(event,date) => {
															  	var day = (date.getDate() < 10) ? ('0'+date.getDate()) : date.getDate();
																var month = ((date.getMonth() + 1)<10) ? ('0'+(date.getMonth() + 1)) : (date.getMonth() + 1)
	

															  var e = {
																target:{
																	value: day + "/" + month + "/" + date.getFullYear()
																}
															  }
															handleAge(date.getFullYear());
															handleChangeFloor(e,"floor" ,"constCompletionDate", true, "")}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  textFieldStyle={{width: '100%'}}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>
													<Col xs={12} md={3} sm={6}>
														<DatePicker  className="fullWidth datepicker"
														formatDate={(date)=> this.formatDate(date)}
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.effectiveFromDate')+' *'}
														  errorText={fieldErrors.floor ? (fieldErrors.floor.occupancyDate ? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.occupancyDate}</span> : "") : ""}
														  onChange={(event,date) => {
															  	var day = (date.getDate() < 10) ? ('0'+date.getDate()) : date.getDate();
																var month = ((date.getMonth() + 1)<10) ? ('0'+(date.getMonth() + 1)) : (date.getMonth() + 1)
	

															  var e = {
																target:{
																	value: day + "/" + month + "/" + date.getFullYear()
																}
															  }
															  handleChangeFloor(e,"floor" ,"occupancyDate", true, "")}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  textFieldStyle={{width: '100%'}}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>
													<Col xs={12} md={3} sm={6}>
														<SelectField  className="fullWidth selectOption"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.unstructuredLand')+' *'}
														  errorText={fieldErrors.floor ? ( fieldErrors.floor.isStructured?<span style={{position:"absolute", bottom:-41}}>{fieldErrors.floor.isStructured}</span>:"") : ""}
														  value={floorDetails.floor ? floorDetails.floor.isStructured : ""}
														  onChange={(event, index, value) => {
															  (value == -1) ?  value = '' : '';
															  
															  floorDetails.floor.length = '';
															  floorDetails.floor.width = '';
															  floorDetails.floor.builtupArea = '';
															  
															  var e = {
																target: {
																  value: value
																}
															  };
															  handleChangeFloor(e, "floor" ,"isStructured", true, "")}
														  }
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														>
																<MenuItem value={-1} primaryText="None" />
															  <MenuItem value='YES' primaryText="Yes" />
															  <MenuItem value='NO' primaryText="No" />
														</SelectField>
													</Col>
													<Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.length')}
														  hintText="12.50"
														  errorText={fieldErrors.floor ? (fieldErrors.floor.length ? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.length}</span>:"") : ""}
														  value={floorDetails.floor ? floorDetails.floor.length : ""}
														  onChange={(e) => {
															  handleChangeFloor(e,"floor" ,"length", false, /^[0-9.]+$/);
															  cThis.calcArea(e, 'length');
														  }}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={6}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														  disabled={(floorDetails.hasOwnProperty('floor') && floorDetails.floor!=null )? (floorDetails.floor.isStructured == 'YES' ? true : false) : false}
														/>
													</Col>
													<Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  hintText="15.25"
														  floatingLabelText={translate('pt.create.groups.floorDetails.fields.breadth')}
														  errorText={fieldErrors.floor ?(fieldErrors.floor.width ? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.width}</span> :""): ""}
														  value={floorDetails.floor ? floorDetails.floor.width : ""}
														  onChange={(e) => {
															  handleChangeFloor(e,"floor" ,"width", false, /^[0-9.]+$/);
															  cThis.calcArea(e, 'width');
															}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={6}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														  disabled={(floorDetails.hasOwnProperty('floor') && floorDetails.floor!=null )? (floorDetails.floor.isStructured == 'YES' ? true : false) : false}
														/>
													</Col>
													
													<Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  floatingLabelText="Builtup Area *"
														  hintText="27.75"
														  errorText={fieldErrors.floor ?(fieldErrors.floor.builtupArea? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.builtupArea}</span>:"" ): ""}
														  value={floorDetails.floor ? floorDetails.floor.builtupArea : ""}
														  onChange={(e) => {
															  handleChangeFloor(e, "floor","builtupArea", true, /^[0-9.]+$/)
															}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={6}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														  disabled={(floorDetails.hasOwnProperty('floor') && floorDetails.floor!=null )? (floorDetails.floor.isStructured == 'NO' ? true : false) : false}
														/>
													</Col>
													
													<Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  floatingLabelText="Carpet Area *"
														  errorText={fieldErrors.floor ? (fieldErrors.floor.carpetArea? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.carpetArea}</span> :""): ""}
														  value={floorDetails.floor ? floorDetails.floor.carpetArea : ""}
														  onChange={(e) => {
															  calcAssessableArea(e,'carpet');
															  handleChangeFloor(e,"floor" , "carpetArea", true, "")}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={5}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>
													
													<Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  floatingLabelText="Exempted Area "
														  errorText={fieldErrors.floor ? (fieldErrors.floor.exemptedArea ? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.exemptedArea}</span> :(this.state.negativeValue ? <span style={{position:"absolute", bottom:-13}}>Invalid value</span>: "" )): (this.state.negativeValue ? <span style={{position:"absolute", bottom:-13}}>Invalid value</span>: "" )}
														  value={floorDetails.floor ? floorDetails.floor.exemptedArea : ""}
														  onChange={(e) => {	  
															  calcAssessableArea(e,'exempted');
															  handleChangeFloor(e,"floor" , "exemptedArea", false, /^[0-9]+$/i)}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={5}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>

													<Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  floatingLabelText="Occupancy Certificate Number"
														  errorText={fieldErrors.floor ?(fieldErrors.floor.occupancyCertiNumber? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.occupancyCertiNumber}</span>:"" ): ""}
														  value={floorDetails.floor ? floorDetails.floor.occupancyCertiNumber : ""}
														  onChange={(e) => {handleChangeFloor(e,"floor" ,"occupancyCertiNumber", false, /^[a-z0-9]+$/i)}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={10}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>
															
													<Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  floatingLabelText="Building cost"
														  errorText={fieldErrors.floor ?(fieldErrors.floor.buildingCost? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.buildingCost}</span>: "" ): "" }
														  value={floorDetails.floor ? floorDetails.floor.buildingCost : ""}
														  onChange={(e) => {handleChangeFloor(e,"floor" ,"buildingCost", false, /^[0-9]+$/i)}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={10}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>
													
													<Col xs={12} md={3} sm={6}>
														<TextField  className="fullWidth"
														  floatingLabelText="Land cost"
														  errorText={fieldErrors.floor ?(fieldErrors.floor.landCost? <span style={{position:"absolute", bottom:-13}}>{fieldErrors.floor.landCost}</span>:"" ): ""}
														  value={floorDetails.floor ? floorDetails.floor.landCost : ""}
														  onChange={(e) => {handleChangeFloor(e,"floor" ,"landCost", false, /^[0-9]+$/i)}}
														  floatingLabelFocusStyle={styles.floatingLabelFocusStyle}
														  underlineStyle={styles.underlineStyle}
														  underlineFocusStyle={styles.underlineFocusStyle}
														  maxLength={10}
														  floatingLabelStyle={{color:"rgba(0,0,0,0.5)"}}
														/>
													</Col>
												
													<Col xs={12} md={3} sm={6}>
														  <Checkbox
															label={translate('pt.create.groups.assessmentDetails.fields.isLegal')+' *'}
															style={styles.checkbox}
															defaultChecked ={true}
															onCheck = {(e, i, v) => {
															  var e = {
																target: {
																  value:i
																}
															  }
															  handleChangeFloor(e, "floor","isAuthorised", false, '')
															}}

														  />
													  </Col>
													<Col xs={12} md={12} >
														{this.state.negativeValue && <p>Exempted area should not be greater than Carpet area</p>}
														{this.state.newFloorError && <p>You cannot select more than {this.props.noOfFloors} Floors</p>}

													</Col>
												
													
													<Col xs={12} md={12} style={{textAlign:"right", float:'right'}}>
														<br/>
														{(editIndex == -1 || editIndex == undefined) && <RaisedButton type="button" label="Add Room" disabled={!isFloorValid || this.state.newFloorError || this.state.negativeValue}  primary={true} onClick={()=>{
															 this.props.addNestedFormData("floors","floor");
															
															 this.props.resetObject("floor", false);
															  this.props.resetObject("owner", false);
															  
															 setTimeout(()=>{
																	_this.createFloorObject();
																	_this.getFloors();
																}, 300);
															}	
														}/>}
														{ (editIndex > -1) &&
															<RaisedButton type="button" label="Update Room" disabled={!isFloorValid || this.state.newFloorError || this.state.negativeValue} primary={true}  onClick={()=> {
																  this.props.updateObject("floors","floor",  editIndex);
																  this.props.resetObject("floor", false);
																  this.props.resetObject("owner", false);
																  
																  isEditIndex(-1);
																  
																  setTimeout(()=>{
																	_this.createFloorObject();
																	}, 300);
																}
															}/>
														}
													</Col>
													
												</Row>
												{ floorDetails.floors &&
                                            <div className="col-md-12 col-xs-12"> <br/>
                                          <Table id="floorDetailsTable" style={{color:"black",fontWeight: "normal", marginBottom:0}} bordered responsive>
                                          <thead style={{backgroundColor:"#607b84",color:"white"}}>
                                            <tr>
                                              <th>#</th>
											  <th>Floor No.</th>
                                              <th>Unit Type</th>
											  <th>Flat No.</th>
                                              <th>Unit No.</th>
                                              <th>Construction Class</th>
                                              <th>Usage Type</th>
                                              <th>Usage Sub Type</th>
                                              <th>Firm Name</th>
                                              <th>Occupancy</th>
                                              <th>Occupant Name</th>
                                              <th>Annual Rent</th>
                                              <th>Manual ARV</th>
                                              <th>Construction End Date</th>
                                              <th>Effective From Date</th>
                                              <th>Unstructured land</th>
                                              <th>Length</th>
                                              <th>Breadth</th>
											  <th>Carpet Area</th>
											  <th>Exempted Area</th>
											  <th>Building Cost</th>
											  <th>Land Cost</th>
                                              <th>Builtup Area</th>
                                              <th>Occupancy Certificate Number</th>
                                              <th>Building Permission Number</th>
                                              <th style={{minWidth:70}}></th>
                                            </tr>
                                          </thead>
                                          <tbody>
                                            {floorDetails.floors && floorDetails.floors.map(function(i, index){
                                              if(i){
                                                return (<tr key={index}>
                                                    <td>{index+1}</td>
                                                    <td>{getNameById(_this.state.floorNumber ,i.floorNo) || 'NA'}</td>
													<td>{getNameById(_this.state.unitType ,i.unitType)  || 'NA'}</td>
													<td>{i.flatNo ? i.flatNo : 'NA'}</td>
                                                    <td>{i.unitNo || 'NA'}</td>
                                                    <td>{i.structure || 'NA'}</td>
                                                    <td>{i.usage || 'NA'}</td>
                                                    <td>{i.usageSubType || 'NA'}</td>
                                                    <td>{i.firmName || 'NA'}</td>
                                                    <td>{i.occupancyType || 'NA'}</td>
                                                    <td>{i.occupierName || 'NA'}</td>
                                                    <td>{i.annualRent || 'NA'}</td>
                                                    <td>{i.manualArv || 'NA'}</td>
                                                    <td>{i.constCompletionDate || 'NA'}</td>
                                                    <td>{i.occupancyDate || 'NA'}</td>
                                                    <td>{i.isStructured || 'NA'}</td>
                                                    <td>{i.length || 'NA'}</td>
                                                    <td>{i.width || 'NA'}</td>
													<td>{i.carpetArea || 'NA'}</td>
													<td>{i.exemptedArea || 'NA'}</td>
													<td>{i.buildingCost || 'NA'}</td>
													<td>{i.landCost || 'NA'}</td>
                                                    <td>{i.builtupArea || 'NA'}</td>
                                                    <td>{i.occupancyCertiNumber || 'NA'}</td>
                                                    <td>{i.bpaNo || 'NA'}</td>
                                                    <td>
														<i className="material-icons" style={styles.iconFont} onClick={ () => {
															if(i.isStructured){
																i.isStructured = 'YES'
															} else {
																i.isStructured = 'NO'
															}
															editObject("floor",i, true);
															cThis.props.setForm();
															toggleSnackbarAndSetText(true, 'Edit room details and update.')
															isEditIndex(index);
                                                         }}>mode_edit</i>
                                                         <i className="material-icons" style={styles.iconFont} onClick={ () => {
															  deleteObject("floors", index);
																isEditIndex(-1);
																setTimeout(()=>{
																	_this.createFloorObject();
																}, 300);
                                                         }}>delete</i>
                                                    </td>
                                                  </tr>)
                                              }

                                            })}
                                          </tbody>
                                          </Table>


                                                </div>}

											</Row>
										</Col>
									</Row>
								</Grid>
		
				</CardText>
			</Card>
	   )
   } 

}

const mapStateToProps = state => ({
  floorDetails:state.form.form,
  fieldErrors: state.form.fieldErrors,
  editIndex: state.form.editIndex,
  addRoom : state.form.addRoom,
  isFloorValid: state.form.isFloorValid,
  noOfFloors: state.form.noOfFloors
});

const mapDispatchToProps = dispatch => ({
	
initForm : () => {
	dispatch({
		type: "SET_FLOOR_NUMBER",
		noOfFloors: 0
	})
},

 setForm: () => {
    dispatch({
      type: "SET_FLOOR_STATE",
	   validatePropertyFloor: {
        required: {
          current: ['floorNo', 'unitType','unitNo', 'structure', 'usage', 'occupancyType', 'constCompletionDate', 'occupancyDate', 'isStructured', 'builtupArea','carpetArea'],
          required: ['floorNo', 'unitType','unitNo', 'structure', 'usage', 'occupancyType', 'constCompletionDate', 'occupancyDate', 'isStructured', 'builtupArea','carpetArea']
        },
        pattern: {
          current: [],
          required: []
        }
      }
	
    });
  },
  handleChange: (e, property, isRequired, pattern) => {
    dispatch({type: "HANDLE_CHANGE", property, value: e.target.value, isRequired, pattern});
  },

  handleChangeNextOne: (e, property, propertyOne, isRequired, pattern) => {
    dispatch({
      type: "HANDLE_CHANGE_NEXT_ONE",
      property,
      propertyOne,
      value: e.target.value,
      isRequired,
      pattern
    })
  },
  
  handleChangeFloor: (e, property, propertyOne, isRequired, pattern) => {
    dispatch({
      type: "HANDLE_CHANGE_FLOOR",
      property,
      propertyOne,
      value: e.target.value,
      isRequired,
      pattern
    })
  },
  
  addNestedFormData: (formArray, formData) => {
    dispatch({
      type: "PUSH_ONE",
      formArray,
      formData
    })
  },

  addNestedFormDataTwo: (formObject, formArray, formData) => {
    dispatch({
      type: "PUSH_ONE_ARRAY",
      formObject,
      formArray,
      formData
    })
  },

  deleteObject: (property, index) => {
    dispatch({
      type: "DELETE_OBJECT",
      property,
      index
    })
  },

  deleteNestedObject: (property,position, propertyOne, subPosition) => {
    dispatch({
      type: "DELETE_NESTED_OBJECT",
      property,
	  position,
      propertyOne,
      subPosition
    })
  },

  editObject: (objectName, object, isSectionValid) => {
    dispatch({
      type: "EDIT_OBJECT",
      objectName,
      object,
	  isSectionValid
    })
  },

  resetObject: (object, isSectionValid) => {
    dispatch({
      type: "RESET_OBJECT",
      object,
	  isSectionValid,
	    validatePropertyOwner: {
        required: {
          current: [],
          required: ['mobileNumber', 'name', 'gaurdianRelation', 'gaurdian', 'gender' ]
        },
        pattern: {
          current: [],
          required: []
        }
      },
	   validatePropertyFloor: {
        required: {
          current: [],
          required: ['floorNo', 'unitType','unitNo', 'structure', 'usage', 'occupancyType', 'constCompletionDate', 'occupancyDate', 'isStructured', 'builtupArea','carpetArea']
        },
        pattern: {
          current: [],
          required: []
        }
      }
    })
  },

  updateObject: (objectName, object) => {
    dispatch({
      type: "UPDATE_OBJECT",
      objectName,
      object
    })
  },

  updateNestedObject:  (property,position, propertyOne, subPosition) => {
    dispatch({
      type: "UPDATE_NESTED_OBJECT",
      property,
	  position,
      propertyOne,
	  subPosition
    })
  },

  isEditIndex: (index) => {
    dispatch({
      type: "EDIT_INDEX",
      index
    })
  },
  
	addFloorDepandencyFields: (property) => {
		dispatch({
			type: 'ADD_FLOOR_REQUIRED',
			property
		})
	},

	removeFloorDepandencyFields: (property) => {
		dispatch({
			type: 'REMOVE_FLOOR_REQUIRED',
			property
		})
	},
  
	setLoadingStatus: (loadingStatus) => {
	  dispatch({type: "SET_LOADING_STATUS", loadingStatus});
	},
	toggleDailogAndSetText: (dailogState,msg) => {
	  dispatch({type: "TOGGLE_DAILOG_AND_SET_TEXT", dailogState, msg});
	},
	toggleSnackbarAndSetText: (snackbarState, toastMsg) => {
	  dispatch({type: "TOGGLE_SNACKBAR_AND_SET_TEXT", snackbarState, toastMsg});
	},

});

export default connect(mapStateToProps, mapDispatchToProps)(FloorDetails);