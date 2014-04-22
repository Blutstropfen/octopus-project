angular.module("OctopusApp")
    .controller("RecipeBrowser", function ($scope, $http, $routeParams) {
        var search = $routeParams.search;
        $scope.showAlert = false;
        if (search) {
            $http.get("/recipe/search", {
                params: {
                    text: search
                }
            }).success(function (data) {
                $scope.recipes = data;
                if (!data.length) {
                    $scope.showAlert = true;
                }
            }).error(function (html) {
                $scope.raise({
                    type: "danger",
                    message: html
                })
            })
        } else {
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
        }
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
                $scope.recipe = recipe;
            })
            .error(function (html) {
                $scope.raise({
                    type: "danger",
                    message: html
                })
            })
        } else {
            $scope.recipe = {
                ingredients: [],
                notes: []
            }
        }
        $scope.addIngredient = function () {
            $scope.recipe.ingredients.push({
                editable: true
            });
        };
        $scope.removeIngredient = function (ingredient) {
            $scope.recipe.ingredients = _.without($scope.recipe.ingredients, ingredient);
        };
        $scope.addNote = function () {
            $scope.recipe.notes.push({
                created: true,
                editable: true
            });
        };
        $scope.removeNote = function (note) {
            $scope.recipe.notes = _.without($scope.recipe.notes, note);
        };
        $scope.commit = function () {
            $http.post("/recipe", $scope.recipe)
                .success(function (response) {
                    if (response.status == "ok") {
                        $location.path("/")
                    } else {
                        $scope.raise({
                            type: "danger",
                            message: response.message
                        })
                    }
                })
                .error(function (html) {
                    $scope.raise({
                        type: "danger",
                        message: html
                    })
                })
        };
        $scope.remove = function () {
            $http.delete("/recipe", {params: {id: $scope.recipe.id}})
                .success(function () {
                    $location.path("/")
                })
                .error(function (html) {
                    $scope.raise({
                        type: "danger",
                        message: html
                    })
                })
        };
        $scope.back = function () {
            $location.path("/")
        }
    });

angular.module("OctopusApp")
    .controller("IngredientEditor", function ($scope) {
        $scope.disableEditor = function () {
            $scope.ingredient.editable = false;
        }
    });

angular.module("OctopusApp")
    .controller("NoteEditor", function ($scope) {
        $scope.enableEditor = function () {
            $scope.note.editable = true;
            $scope.editable = {
                contents: $scope.note.contents
            };
        };

        $scope.discardEditor = function () {
            $scope.note.editable = false;
            if ($scope.note.created) {
                $scope.removeNote($scope.note)
            }
        };

        $scope.saveEditor = function () {
            $scope.note.editable = false;
            $scope.note.created = false;
            $scope.note.contents = $scope.editable.contents;
        }
    });