app.service('searchService',function ($http) {

    this.searchList = function(searchMap){
        return $http.post('itemsearch/search',searchMap);
    }
});