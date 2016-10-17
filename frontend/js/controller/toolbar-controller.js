import { app } from '../../app';

angular
  .module(app)
  .controller('ToolbarController', ToolbarController);

ToolbarController.$inject = ['$mdSidenav'];

function ToolbarController($mdSidenav) {
  var vm = this;
  vm.toggleSidenav = () => {
    $mdSidenav('left').toggle();
  };
}