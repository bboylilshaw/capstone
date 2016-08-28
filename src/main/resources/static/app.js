angular.module('app', [])
  .run(function () {
    console.log('App is running.');
  });

angular.module('app')
  .controller('AppController', function ($scope) {
    var ws = new SockJS('/socket/message');
    var stompClient = Stomp.over(ws);
    stompClient.connect({}, function(frame) {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/greetings', function(msg){
        console.log(msg.body);
        $scope.msg = msg.body;
        $scope.$apply();
      });
      stompClient.subscribe('/topic/another', function(msg){
        console.log(msg.body);
        $scope.another = msg.body;
        $scope.$apply();
      });
      stompClient.send("/app/greetings", {}, "Jason");
    });
  });