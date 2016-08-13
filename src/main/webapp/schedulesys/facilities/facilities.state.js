(function() {
	'use strict';
	angular
		.module('scheduleSys')
		.config(stateConfig);
	
	stateConfig.$Inject = ['$stateProvider'];
	
	function stateConfig($stateProvider) {
		$stateProvider
		.state('home.facilities', {
			url: '/facilities',
			templateUrl: 'schedulesys/facilities/facilities.html',
			controller: 'FacilitiesController'
		})
		.state('home.facilities.new', {
			url: '/new',
			onEnter: ['$stateParams', '$state', '$mdDialog', function($stateParams, $state, $mdDialog) {
				$mdDialog.show({
					title: 'New facility',
					templateUrl: 'schedulesys/facilities/facility-dialog.html',
					parent: angular.element(document.body),
					controller: 'FacilityDialogController',
					clickOutsideToClose:true,
					onRemoving: function (){
						$state.go($rootScope.previousState.name, {id: $rootScope.previousStateParams.id}, {reload: true});
					}
				});
			}]
		})
		.state('home.facilities.edit', {
			url:'/{id}/facility/edit',
			onEnter: ['$stateParams', '$state', '$mdDialog', function($stateParams, $state, $mdDialog) {
				$mdDialog.show({
					templateUrl: 'schedulesys/facilities/facility-dialog.html',
					parent: angular.element(document.body),
					controller: 'FacilityDialogController',
					clickOutsideToClose:true,
					onRemoving: function (){
						$state.go($rootScope.previousState.name, {id: $rootScope.previousStateParams.id}, {reload: true});
					}
				});
			}]
		})
		.state('home.facilities-details', {
			url: '/{id}/facility/details',
			templateUrl: 'schedulesys/facilities/facilities-details.html',
			controller: 'FacilityDetailsController'
		})
		.state('home.facilities-scheduling', {
			url: '/{id}/schedules',
			templateUrl: 'schedulesys/schedules/facilities.scheduling-page.html',
			controller: 'FacilitySchedulingController'
		})
		.state('home.facilities-scheduling.add', {
			url: '/{id}/schedules/add',
			onEnter: ['$rootScope', '$state', '$mdDialog', function($rootScope, $state, $mdDialog) {
				$mdDialog.show({
					templateUrl: 'schedulesys/schedules/schedule-dialog.html',
					parent: angular.element(document.body),
					controller: 'FacilitySchedulingDialogController',
					clickOutsideToClose:true,
					onRemoving: function (){
						$state.go($rootScope.previousState.name, {id: $rootScope.previousStateParams.id}, {reload: true});
					}
				});
			}]
		})
	}
})();
