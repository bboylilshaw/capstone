import {app} from "../../app";

angular
  .module(app)
  .service('AuthService', AuthService);

function AuthService($http, TokenUtils) {
  this.auth = function () {
    return $http.post('/api/auth');
  };

  this.login = function (username, password) {
    var requestBody = {
      username: username,
      password: password
    };
    return $http.post('/api/auth', requestBody);
  };

  this.getCurrentUser = function () {
    return $http.get('/api/current');
  };

  this.logout = function () {
    return TokenUtils.remove();
  }
}