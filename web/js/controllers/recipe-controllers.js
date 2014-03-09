angular.module("OctopusApp")
    .controller("RecipeBrowser", function ($scope, $http) {
        $http.get("/recipe/recent")
            .success(function (data) {
                $scope.recipes = data
            })
    });

angular.module("OctopusApp")
    .controller("RecipeEditor", function ($scope, $location, $routeParams, $http) {
        $http.get("/recipe", {
                params: {
                    id: $routeParams.id
                }
            }).success(function (recipe) {
                $scope.recipe = recipe
            }).error(function (status) {
                $("#alert-panel").html(status);
            });
        $scope.commit = function () {
            $http.post("/recipe", $scope.recipe)
                .success(function () {
                    $location.path("/")
                })
        }
    });