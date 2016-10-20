import { app } from '../../app';

angular
  .module(app)
  .controller('ToolbarController', ToolbarController);

ToolbarController.$inject = ['$mdSidenav', 'notifier'];

function ToolbarController($mdSidenav, notifier) {
  var vm = this;
  vm.toggleSidenav = () => {
    $mdSidenav('left').toggle();
  };

  vm.showMsg = () => {
    notifier.notify('hello');
  }
}