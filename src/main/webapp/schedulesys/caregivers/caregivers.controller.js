(function() {
	
	'use strict';
	
	angular
		.module('scheduleSys')
		.controller('careGiversController', careGiversController);
	
	careGiversController.$Inject = ['$scope', '$state', '$mdToast', '$mdDialog' ,'careGiversService'];
	
	function careGiversController($scope, $state, $mdToast, $mdDialog, careGiversService){
		var vm = this;
		
		vm.allcareGivers = null;
		vm.careGiversOnCurrentPage = null;
		
		vm.loadAll = loadAll;
		vm.showConfirm = showConfirm;
		vm.selected = [];
		vm.editOrDelete = true;
		vm.showToast = showToast;
		vm.onPaginate = onPaginate;
		vm.slicecareGiversArray = slicecareGiversArray;
		vm.showcareGiversDialog = showcareGiversDialog;
		vm.lastDayDateCheck = lastDayDateCheck;
		
		vm.query = {
				order: 'name',
				limit: 5,
				page: 1
		};
		
		loadAll();
		
		$scope.$watchCollection('vm.selected', function(oldValue, newValue) {
			vm.editOrDelete = (vm.selected.length === 0) ? true : false;
			console.log('Selected items : ' + angular.toJson(vm.selected));
			console.log('Edit or delete : ' + vm.editOrDelete);
		});
		
		function showConfirm(ev) {
			var confirm = $mdDialog.confirm()
					.title('Delete a Care-Giver')
					.textContent('Are you sure you want to delete this Care-Giver?')
					.ariaLabel('Care-Giver deletion')
					.targetEvent(ev)
					.ok('Delete')
					.cancel('Cancel');
			$mdDialog.show(confirm).then(function() {
				careGiversService.remove(
						{id:vm.selected[0].id},
						onDeleteSuccess,
						onDeleteFailure
						);
			}, function() {
				console.log('Keep this one ...');
			});
		};
		
		function loadAll(){
			careGiversService.query({}, onLoadAllSuccess, onLoadAllError);
		}
		
		function onLoadAllSuccess(data){
			vm.allcareGivers = data;
			vm.careGiversOnCurrentPage = vm.slicecareGiversArray();
		}
		
		function onLoadAllError(status){
			console.log('Error status : ' + status);
		}
		
		function onDeleteSuccess (){
			vm.careGiversOnCurrentPage.splice(vm.careGiversOnCurrentPage.indexOf(vm.selected[0]), 1);
			vm.allcareGivers.splice(vm.allcareGivers.indexOf(vm.selected[0]), 1);
			vm.editOrDelete = true;
			vm.showToast('Care-Giver ' + vm.selected[0].firstName + ' successfully deleted', 5000);
		}	
		
		function onDeleteFailure (){
			vm.showToast('Care-Giver ' + vm.selected[0].firstName 
					+ ' could not be deleted ', 5000);
		}
		
		function showToast(textContent, delay){
			$mdToast.show(
					$mdToast.simple()
					.textContent(textContent)
					.position('top right')
					.hideDelay(delay)
					);
		}
		
		function onPaginate(){
			vm.careGiversOnCurrentPage = vm.slicecareGiversArray();
		}
		
		function slicecareGiversArray(){
			var slicedArray = vm.allcareGivers.slice(5 * (vm.query.page - 1), (vm.query.limit * vm.query.page));
			console.log('Sliced array : ' + angular.toJson(slicedArray));
			return slicedArray;
		}
		
		function showcareGiversDialog(ev) {
			$mdDialog.show({
				templateUrl: 'schedulesys/caregivers/caregiver-dialog.html',
				parent: angular.element(document.body),
				targetEvent: ev,
				clickOutsideToClose:true
			})
			.then(function() {
				//$scope.status = 'You said the information was "' + answer + '".';
			}, function() {
				//$scope.status = 'You cancelled the dialog.';
			});
		};
	
		
		   function lastDayDateCheck(caregiver){
				
				vm.checkDate1 = new Date();
				vm.checkDate2 = new Date(caregiver.lastDayOfWork);
				
				if(vm.checkDate1 > vm.checkDate2){
					return true;
				}
				else{
					return false;
				}
				
			}
		   
	}
	
})();