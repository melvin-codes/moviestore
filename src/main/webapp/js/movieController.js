let mymovieapp = angular.module('movieapp');

mymovieapp.controller('movieController', function($scope) {
    $scope.title = "My Movie Application";
    $scope.author = "by Melvin Vizueth";
})