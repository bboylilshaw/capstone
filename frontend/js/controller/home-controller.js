import { app } from '../../app';

angular
  .module(app)
  .controller('HomeController', HomeController);

HomeController.$inject = ['$scope'];

function HomeController($scope) {
  var vm = this;
  vm.name = 'Jason';
  $scope.test = 'Hello, World!!!';
}