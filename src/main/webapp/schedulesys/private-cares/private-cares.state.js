(function() {
	'use strict';
	angular
		.module('scheduleSys')
		.config(stateConfig);
	
	stateConfig.$Inject = ['$stateProvider'];
	
	function stateConfig($stateProvider) {
		$stateProvider
		.state('home.privatecares', {
			url: '/private-cares',
			templateUrl: 'schedulesys/private-cares/private-cares.html',
			controller: 'PrivateCareController'
		})
		.state('home.privatecares.new', {
			url: '/new',
			onEnter: ['$rootScope', '$state', '$mdDialog', function($rootScope, $state, $mdDialog) {
				$mdDialog.show({
					title: 'New private care',
					templateUrl: 'schedulesys/private-cares/private-care.dialog.html',
					parent: angular.element(document.body),
					controller: 'PrivateCareDialogController',
					clickOutsideToClose:true,
					onRemoving: function (){
						$state.go($rootScope.previousState.name, {id: $rootScope.previousStateParams.id}, {reload: true});
					}
				});
			}]
		})
		.state('home.privatecares.edit', {
			url:'/{id}/edit',
			onEnter: ['$rootScope', '$state', '$mdDialog', function($rootScope, $state, $mdDialog) {
				$mdDialog.show({
					templateUrl: 'schedulesys/private-cares/private-care.dialog.html',
					parent: angular.element(document.body),
					controller: 'PrivateCareDialogController',
					clickOutsideToClose:true,
					onRemoving: function (){
						$state.go($rootScope.previousState.name, {id: $rootScope.previousStateParams.id}, {reload: true});
					}
				});
			}]
		})
		.state('home.privatecares-details', {
			url: '/private-cares/{id}/details',
			templateUrl: 'schedulesys/private-cares/private-care.details.html',
			controller: 'PrivateCareDetailsController'
		})
//		.state('home.facilities-scheduling', {
//			url: '/facilities/{id}/schedules',
//			 data: {
//	                schedulingType: 'facility'
//	            },
//			templateUrl: 'schedulesys/schedules/facilities.scheduling-page.html',
//			controller: 'FacilitySchedulingController'
//		})
//		.state('home.facilities-scheduling.add', {
//			url: '/add',
//			onEnter: ['$rootScope', '$state', '$mdDialog', function($rootScope, $state, $mdDialog) {
//				$mdDialog.show({
//					templateUrl: 'schedulesys/schedules/schedule-dialog.html',
//					parent: angular.element(document.body),
//					controller: 'FacilitySchedulingDialogController',
//					clickOutsideToClose:true,
//					onRemoving: function (){
//						$state.go($rootScope.previousState.name, {id: $rootScope.previousStateParams.id}, {reload: true});
//					}
//				});
//			}]
//		})
//		.state('home.facilities-scheduling.edit', {
//			url: '/{scheduleId}/edit',
//			onEnter: ['$rootScope', '$state', '$mdDialog', function($rootScope, $state, $mdDialog) {
//				$mdDialog.show({
//					templateUrl: 'schedulesys/schedules/schedule-dialog.html',
//					parent: angular.element(document.body),
//					controller: 'FacilitySchedulingDialogController',
//					clickOutsideToClose:true,
//					onRemoving: function (){
//						$state.go($rootScope.previousState.name, {id: $rootScope.previousStateParams.id}, {reload: true});
//					}
//				});
//			}]
//		})
		.state('home.privatecares-details.add-contacts', {
			url: '/contacts/new',
			onEnter: ['$rootScope', '$state', '$mdDialog', function($rootScope, $state, $mdDialog) {
				$mdDialog.show({
					title: 'New Contact',
					templateUrl: 'schedulesys/contacts/contacts.dialog.html',
					parent: angular.element(document.body),
					controller: 'ContactDialogController',
					clickOutsideToClose:true,
					onRemoving: function (){
						$state.go($rootScope.previousState.name, {id: $rootScope.previousStateParams.id}, {reload: true});
					}
				});
			}]
		}).state('home.privatecares-details.edit-contacts', {
			url:'/contacts/{contactId}/edit',
			onEnter: ['$rootScope', '$state', '$mdDialog', function($rootScope, $state, $mdDialog) {
				$mdDialog.show({
					templateUrl: 'schedulesys/contacts/contacts.dialog.html',
					parent: angular.element(document.body),
					controller: 'ContactDialogController',
					clickOutsideToClose:true,
					onRemoving: function (){
						$state.go($rootScope.previousState.name, {id: $rootScope.previousStateParams.id}, {reload: true});
					}
				});
			}]
		})
	}
})();
