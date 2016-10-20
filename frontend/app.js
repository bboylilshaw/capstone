import angular from 'angular';
import 'angular-ui-router';
import 'angular-animate';
import 'angular-aria';
import 'angular-material';
import 'angular-websocket';

import './style/index.scss';
import routes from './routes';

export const app = 'Capstone';
angular
  .module(app, [
    'ui.router',
    'ngMaterial',
    'ngWebSocket'
  ])
  .run(function () {
    console.log('App is running.');
  })
  .config(routes);