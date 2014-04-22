angular.module("OctopusApp")
    .controller("SearchController", function ($scope, $http) {
        $scope.recipes = [];
        $scope.ingredients = [];
        $scope.addIngredient = function () {
            $scope.ingredients.push({
                editable: true,
                invalid: true,
                type: "danger"
            });
        };
        $scope.confirmIngredient = function (ingredient) {
            ingredient.editable = false;
            $http.get("/ingredient/name", {
                params: {
                    name: ingredient.name
                }
            }).success(function (id) {
                if (id) {
                    ingredient.id = JSON.parse(id);
                    ingredient.type = "primary";
                    ingredient.invalid = false;
                }
            }).error(function (html) {
                $scope.raise({
                    type: "danger",
                    message: html
                })
            })
        };
        $scope.removeIngredient = function (ingredient) {
            $scope.ingredients = _.without($scope.ingredients, ingredient);
        };
        $scope.submit = function () {
            var ingredients = _.filter($scope.ingredients, function (ingredient) {
                return !ingredient.editable;
            });
            var valid = _.every(ingredients, function (ingredient) {
                return !ingredient.invalid;
            });
            var ids = _.map(ingredients, function (ingredient) {
                return ingredient.id;
            });
            if (valid) {
                console.log(ids);
                $http.post("/recipe/ingredient-search", ids)
                    .success(function (data) {
                        $scope.recipes = data;
                        if (!data.length) {
                            $scope.showAlert = true;
                        }
                    })
                    .error(function (html) {
                        $scope.raise({
                            type: "danger",
                            message: html
                        });
                    })
            } else {
                $scope.recipes = []
            }
        }
    });