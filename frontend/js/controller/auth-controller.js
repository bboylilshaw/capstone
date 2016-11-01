import { app } from '../../app';

angular
  .module(app)
  .controller('AuthController', AuthController);

AuthController.$inject = ['TokenUtils', 'AuthService', '$state'];

function AuthController(TokenUtils, AuthService, $state) {
  var vm = this;

  vm.init = function () {
    console.log('init....');
    if (TokenUtils.get() !== null) {
      AuthService.auth()
        .then((response) => {
          console.log(response);
          const headers = response.headers();
          const token = headers['x-auth-token'];
          TokenUtils.save(token);
          $state.go('home');
        }, (err) => {
          console.log(err);
        });
    }
  };

  vm.login = function () {
    console.log('login....');
    console.log(vm.username);
    console.log(vm.password);
    AuthService.login(vm.username, vm.password)
      .then((response) => {
        console.log(response);
        const headers = response.headers();
        const token = headers['x-auth-token'];
        TokenUtils.save(token);
        $state.go('home');
      }, (err) => {
        console.log(err);
      });
  };
}