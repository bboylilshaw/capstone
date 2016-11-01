import { app } from '../../app';

angular
  .module(app)
  .factory('TokenAuthInterceptor', TokenAuthInterceptor);

function TokenAuthInterceptor($q, TokenUtils) {
  return {
    request: request,
    responseError: responseError,
  };

  function request(config) {
    const AUTH_TOKEN = TokenUtils.get();
    if (AUTH_TOKEN) {
      config.headers['X-Auth-Token'] = AUTH_TOKEN;
    }
    return config;
  }

  function responseError(error) {
    if (error.status === 401 || error.status === 403) {
      TokenUtils.remove();
    }
    return $q.reject(error);
  }
}