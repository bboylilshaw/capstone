import { app } from '../../app';

angular
  .module(app)
  .factory('notifier', notifier);

notifier.$inject = ['$mdToast', '$websocket'];

function notifier($mdToast, $websocket) {
  var ws = $websocket('ws://localhost:8080/notification');
  ws.onMessage(function (msg) {
    // console.log('onMessage');
    // console.log(msg);
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