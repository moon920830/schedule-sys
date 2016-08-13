(function(){
	'use strict';
	angular
		.module('scheduleSys')
		.controller('careGiverDialogController', careGiverDialogController);
	
	careGiverDialogController.$Inject = ['$state','$stateParams', '$mdDialog', '$mdToast', 'careGiversService','careGiversPositionTypeService','PhoneLabelService','PhoneTypeService'];
	
	function careGiverDialogController($state,$stateParams, $mdDialog, $mdToast, careGiversService,careGiversPositionTypeService,PhoneLabelService,PhoneTypeService){
		var vm = this;
		
		vm.cancel = cancel;
		vm.createOrUpdatecareGiver = createOrUpdatecareGiver;
		vm.showToast = showToast;
		vm.careGiverpositionType = careGiversPositionTypeService.query();
		vm.options2 = vm.careGiverpositionType;
		vm.careGiverphoneType = PhoneTypeService.query();
		vm.options3 = vm.careGiverphoneType;
		vm.careGiverphoneLabel = PhoneLabelService.query();
		vm.options4 = vm.careGiverphoneLabel;
		console.log(vm.options2);
		console.log(vm.options3);
		console.log(vm.options4);
		vm.getSelectedcareGiver = getSelectedcareGiver;
		vm.showPhone = function(){
			
			vm.displayPhone = ! vm.displayPhone;
		}
		vm.myModel = {};
		vm.careGiver = {
				id: null,
				firstName: null,
				lastName: null,
				positionName: null,
				ebc: null,
				dateOfHire: null,
				rehireDate: null,
				lastDayOfWork: null,
				comment: null,
				phoneNumbers: [
			      {
			        
			        number: null ,
			        numberLabel: null,
			        numberType: null
			      }
			    ]
			
		};
		
		if(angular.isDefined($stateParams.id)){
			vm.getSelectedcareGiver();
		}
		
		console.log(vm.careGiver.dateOfHire);
		
		function getSelectedcareGiver(){
			careGiversService.get({id : $stateParams.id},function(result){
				vm.careGiver = result;
				
				var x = vm.careGiver.dateOfHire.split("-");
				var y = (x[1] + '/' + x[2] + '/' + x[0]);
				vm.careGiver.dateOfHire = new Date(y);
				
					
					var a = vm.careGiver.rehireDate.split("-");
					var b = (a[1] + '/' + a[2] + '/' + a[0]);
					vm.careGiver.rehireDate = new Date(b);
					
				
      
					
					var c = vm.careGiver.lastDayOfWork.split("-");
					var d = (c[1] + '/' + c[2] + '/' + c[0]);
					vm.careGiver.lastDayOfWork = new Date(d);
					
				
				
			});
		}
		
		function cancel() {
			$mdDialog.cancel();
		}
		
		function createOrUpdatecareGiver(){
			console.log('Care-Giver to be created : ' + angular.toJson(vm.careGiver));
			if(vm.careGiver.id === null){
				careGiversService.save(vm.careGiver, onCreateOrUpdateSucess, onCreateOrUpdateFailure);
			}else{
				careGiversService.update(vm.careGiver, onCreateOrUpdateSucess, onCreateOrUpdateFailure);
			}
		}
		
		function onCreateOrUpdateSucess(result){
			$state.go('home.caregivers',{}, {reload: true});
			$mdDialog.cancel();
			vm.showToast('Care-Giver ' + vm.careGiver.firstName + ' successfully created', 5000);
		}
		
		function onCreateOrUpdateFailure(result){
			vm.showToast(result.data, 5000);
		}
		
		function showToast(textContent, delay){
			$mdToast.show(
					$mdToast.simple()
					.textContent(textContent)
					.position('top right')
					.hideDelay(delay));
		}
	}
	
})();