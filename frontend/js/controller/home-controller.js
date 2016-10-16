import { app } from '../../app';

angular
  .module(app)
  .controller('HomeController', HomeController);

function HomeController($scope) {
  var vm = this;
  vm.name = 'Jason';
  $scope.test = 'Hello, World!!!';
  console.log('bla');
}