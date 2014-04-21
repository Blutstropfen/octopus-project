angular.module("OctopusApp", ["ngRoute"])
    .config(function ($routeProvider) {
        $routeProvider
            .when("/", {
                controller: "RecipeBrowser",
                templateUrl: "views/recipe-browser.html"
            })
            .when("/recipe/:id", {
                controller: "RecipeEditor",
                templateUrl: "views/recipe-editor.html"
            })
            .when("/recipe", {
                controller: "RecipeEditor",
                templateUrl: "views/recipe-editor.html"
            })
            .otherwise({
                redirectTo: "/"
            });
    })
    .controller("ScopeInitializer", function ($scope, $sce) {
        $scope.board = [];
        $scope.hide = function (message) {
            $scope.board = _.without($scope.board, message)
        };
        $scope.raise = function (message) {
            $scope.board.push({
                header: message.header || "Message",
                message: $sce.trustAs("html", message.message),
                type: message.type || "default"
            })
        }
    })
    .controller("SearchController", function ($scope, $location) {
        $scope.search = function (text) {
            if (text) {
                $location.search("search", text).path("/");
            }
        }
    });