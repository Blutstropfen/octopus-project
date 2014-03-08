angular.module("OctopusApp", [])
    .controller("RecipeCommitController", function($scope, $http) {
        $scope.recipe = {
            name: "Название",
            contents: "Содержание"
        };
        $scope.commitRecipe = function () {
            $http.post("/recipe", $scope.recipe)
        }
    });