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
            .otherwise({
                redirectTo: "/"
            });
    });