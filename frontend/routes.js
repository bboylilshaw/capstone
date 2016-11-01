export default routes;

function routes($httpProvider, $stateProvider, $urlRouterProvider) {
  $httpProvider.interceptors.push('TokenAuthInterceptor');

  $urlRouterProvider.otherwise('/');

  $stateProvider
    .state('home', {
      url: '/',
      template: require('./template/home.pug')
    })
    .state('login', {
      url: '/login',
      template: require('./template/login.pug')
    });
}