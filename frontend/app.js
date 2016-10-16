import angular from 'angular';
import 'angular-ui-router';

import routes from './routes';

export const app = 'Capstone';
angular
  .module(app, ['ui.router'])
  .run(function () {
    console.log('App is running.');
  })
  .config(routes);