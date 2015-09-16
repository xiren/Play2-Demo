angular.module('DemoApp',['DemoApp.Controllers', 'DemoApp.Services', 'ngRoute']).
	config(['$routeProvider', function($routeProvider){
		$routeProvider.
			when('/question', {templateUrl:'/question', controller:'questionController'}).
			when('/questions', {templateUrl:'/question', controller:'questionsController'}).
			otherwise({redirectTo:'/question'});
	}]);