let movieapp = angular.module('movieapp', ['ngRoute']);


movieapp.config(function($routeProvider) {
    $routeProvider
        .when("/stack", {
            templateUrl : "html/stack.html"
        })
        .when("/search", {
            templateUrl : "html/search.html"
            })
        .when("/create", {
            templateUrl : "html/create.html"
        })
        .when("/resume", {
            templateUrl : "html/resume.html"
        })
        .otherwise( {
            templateUrl : "html/main.html"
        });
});