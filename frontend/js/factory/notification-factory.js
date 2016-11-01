import { app } from '../../app';

angular
  .module(app)
  .factory('notifier', notifier);

notifier.$inject = ['$mdToast', '$websocket', '$location'];

function notifier($mdToast, $websocket, $location) {
  const uri = 'ws://' + $location.host() + ':' + $location.port() + '/notification';
  const ws = $websocket(uri);
  ws.onMessage(function (msg) {
    console.log(msg);
    $mdToast.show(
      $mdToast.simple()
        .textContent(msg.data)
        .position('top right')
        .hideDelay(3000)
        .parent(angular.element(document.querySelector('#main-content')))
    );
  });

  return {
    notify: notify
  };

  function notify(msg, type) {
    $mdToast.show(
      $mdToast.simple()
        .textContent(msg)
        .position('top right')
        .hideDelay(3000)
        .parent(angular.element(document.querySelector('#main-content')))
    );
  }

}