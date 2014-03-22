angular.module("OctopusApp")
    .controller("RecipeBrowser", function ($scope, $http, $sce) {
        $http.get("/recipe/recent")
            .success(function (data) {
                $scope.recipes = data
            })
            .error(function (html) {
                $scope.raise({
                    type: "danger",
                    message: html
                })
            });
    });

angular.module("OctopusApp")
    .controller("RecipeEditor", function ($scope, $location, $routeParams, $http) {
        if ($routeParams.id) {
            $http.get("/recipe", {
                params: {
                    id: $routeParams.id
                }
            })
            .success(function (recipe) {
                $scope.recipe = recipe
            })
            .error(function (html) {
                $scope.raise({
                    type: "danger",
                    message: html
                })
            })
        } else {
            $scope.recipe = {}
        }
        $scope.commit = function () {
            $http.post("/recipe", $scope.recipe)
                .success(function () {
                    $location.path("/")
                })
                .error(function (html) {
                    $scope.raise({
                        type: "danger",
                        message: html
                    })
                })
        }
    });