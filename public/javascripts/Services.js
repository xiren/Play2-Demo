angular.module('DemoApp.Services',[]).
	factory('questionService', function($http){
		var questionServiceAPI = {};
		
		questionServiceAPI.getQuestion = function(){
			return $http.get('/normal/question').success(function(data){
				return data.data;
			}).error(function(error){
				return error;
			});
		}
		
		questionServiceAPI.getQuestions = function(){
			return $http.get('/normal/questions').success(function(data){
				return data.data;
			}).error(function(error){
				return error;
			});
		}
		
		return questionServiceAPI;
	})