(function () {
	'use strict';
	angular.module('Pagination')
	.service('PaginationSrv', [function() {
		var _this = this;
		_this.loading = false;
		this.create = function(service, results, cantPerPage, maxPages, searchFunction) {
			_this.search = {};
			_this.config(service, results, cantPerPage || 20, maxPages || 10, searchFunction || 'searchByPage');
			return _this;
		};
		this.config = function(service, results, cantPerPage, maxPages, searchFunction) {
			_this.service = service;
			_this.results = results;
			_this.cantPerPage = cantPerPage; // Resultados por página.
			_this.maxPages = maxPages; // Máximo de páginas por resultado.
			_this.searchFunction = searchFunction; // Función de búsqueda
			this.getTimes(1, _this.maxPages); // Inicialmente muestra los botones del 1 al 10.
		};
		this.getTimes = function(start, end) {
			_this.min = start >= 1 ? start : 1;
			_this.max = end - start < _this.maxPages ? end : _this.maxPages;
			var range = [];
			for (var i = _this.min; i <= _this.max; i++) {
				range.push(i);
			}
			_this.range = range;
		};
		this.showPage = function(pageId, $event, callback) {
			_this.loading = true;
			if(!_this.currentPage){
				_this.currentPage = 1;
			}
			var page = pageId;
			if (pageId == '-2') { // First
				page = 1;
			}
			if (pageId == '-1') { // Previous
				page = _this.prev;
			}
			if (pageId == '-3') { // Next
				page = _this.next;
			}
			if (pageId == '-4') { // Last
				page = _this.last;
			}
			if (pageId > 1 && _this.range.length >= pageId) { // Number
				page = _this.range[pageId - 1];
			}
			if (!page || parseInt(page) < 0) {
				page = parseInt(_this.currentPage);
			}
			_this.service[_this.searchFunction](_this.search, page, _this.cantPerPage, function(err, result) {
				var length = _this.results.length;
				_this.results.splice(0, length);
				if (err) { return (callback ? callback() : null); }
				if (result && result.results) {
					var results = result.results;
					if (results) {
						for (var i = 0; i < results.length; i++)
							_this.results.push(results[i]);
					}
					_this.currentPage = result.current;
					_this.count = result.count;
					_this.prev = result.prev;
					_this.next = result.next;
					_this.last = result.last;
					_this.start = (result.current - 1) * _this.cantPerPage + 1;
					_this.end = result.current * _this.cantPerPage <= result.count ? result.current * _this.cantPerPage : result.count;
					var min = Math.floor((_this.currentPage - 1 )/ _this.maxPages) * _this.maxPages + 1;
					var max = Math.ceil(_this.currentPage / _this.maxPages) * _this.maxPages;
					_this.getTimes(min, max < _this.last ? max : _this.last);
				} else { // Si no hay resultados...
					_this.currentPage = 1;
					_this.count = 0;
					_this.prev = null;
					_this.next = null;
					_this.last = 1;
					_this.start = 0;
					_this.end = 0;
				}
				if ($event) {
					angular.element($event.target).parent().parent.removeClass('active');
					angular.element($event.target).parent().addClass('active');
				}
				_this.loading = false;
				//$(function() {$('html, body').animate({scrollTop: '0px'}, 800);});
				if (callback){
					return callback();
				}
			});
		};

		this.currentPage = 1;
		this.showCurrent = function() {
			_this.showPage(_this.currentPage);
		};
		this.showCurrentDelay = function (callback){
			_this.showPage(_this.currentPage, null, callback);
		};
		this.getStart = function() {
			return _this.start;
		};
		this.getEnd = function() {
			return _this.end;
		};
		this.getCount = function() {
			return _this.count;
		};
		this.cleanSearch = function() {
			_this.search = {};
			_this.getTimes(1, _this.maxPages);
			_this.showPage(1);
		};
	}]);
})()