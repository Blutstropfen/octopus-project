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
                $scope.recipe = recipe;
            })
            .error(function (html) {
                $scope.raise({
                    type: "danger",
                    message: html
                })
            })
        } else {
            $scope.recipe = {};
        }
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