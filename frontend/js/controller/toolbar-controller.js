import { app } from '../../app';

angular
  .module(app)
  .controller('ToolbarController', ToolbarController);

ToolbarController.$inject = ['$mdSidenav', 'notifier', 'AuthService', '$state'];

function ToolbarController($mdSidenav, notifier, AuthService, $state) {
  var vm = this;
  vm.toggleSidenav = () => {
    $mdSidenav('left').toggle();
  };

  vm.showMsg = () => {
    notifier.notify('hello');
  };

  vm.getCurrentUser = () => {
    AuthService.getCurrentUser()
      .then((response) => {
        console.log(response);
        vm.username = response.data.username;
      }, (err) => {
        console.log(err);
        $state.go('login');
      });
  };

  vm.logout = () => {
    AuthService.logout();
    $state.go('login');
  }
}