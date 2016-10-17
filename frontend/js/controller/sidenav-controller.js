import { app } from '../../app';

angular
  .module(app)
  .controller('SidenavController', SidenavController);

SidenavController.$inject = ['$mdSidenav'];

function SidenavController($mdSidenav) {
  var vm = this;
  vm.toggleSidenav = () => {
    $mdSidenav('left').toggle();
    console.log('clicked sidenav button');
  };
}