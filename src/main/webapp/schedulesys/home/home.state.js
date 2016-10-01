(function() {
    'use strict';

    angular
        .module('scheduleSys')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {
    	
    	$urlRouterProvider.otherwise('home.facilities');
    	
        $stateProvider.state('home', {
            url: '/home',
            templateUrl: 'schedulesys/home/home.html',
            controller: 'HomeController'
        });
    }
})();
