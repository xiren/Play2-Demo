angular.module('DemoApp.Controllers',[]).
	controller('questionController',function($scope, questionService){
		
		$scope.questions = null;
		
		questionService.getQuestion().success(function(response){
			$scope.questions = response.data;
		});
	}).
	controller('questionsController',function($scope, questionService){
		
		$scope.questions = null;
		
		questionService.getQuestions().success(function(response){
			$scope.questions = response.data;
		});
	});