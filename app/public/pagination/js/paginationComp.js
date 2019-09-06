(function () {
	'use strict';
	angular.module('Pagination')
	.component('pagination', {
		templateUrl: 'pagination/partials/pagination.html',
		controller: ['$scope', '$location', 'PaginationSrv', function($scope, $location, service) {
			var $ctrl = $scope.$parent;
        	$ctrl.pagination = this.pagination = service.create($ctrl.service, $ctrl.listElement, $ctrl.cantPerPage, $ctrl.maxPages, $ctrl.searchFunction || null);
        	this.pagination.showPage(1);
        	this.isCurrent = function(index){
        		return this.pagination.currentPage == index + 1;
        	}
		}],
		bindings: {
		    service: '<',
		    searchFunction: '<',
		    listElement: '=',
		    cantPerPage: '<',
		    maxPages: '<'
		  }
	})
})()