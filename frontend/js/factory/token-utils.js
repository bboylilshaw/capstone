import { app } from '../../app';

angular
  .module(app)
  .factory('TokenUtils', TokenUtils);

function TokenUtils() {
  const TOKEN_KEY = 'auth_token';
  return {
    save: save,
    get: get,
    remove: remove
  };

  function save(token) {
    return localStorage.setItem(TOKEN_KEY, token);
  }

  function get() {
    return localStorage.getItem(TOKEN_KEY);
  }

  function remove() {
    return localStorage.removeItem(TOKEN_KEY)
  }
}