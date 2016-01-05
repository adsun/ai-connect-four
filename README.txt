
"java -jar OnlineGame.jar"
Registriert zwei Agents und lässt sie auf http://134.93.143.155:6008/game.html
gegeneinander Spielen.
Die main ist in OnlinePlayer.java.

Tests.java enthält methoden zum lokalen testen.

Zum compilieren wird das library json-20140107.jar benötigt.
.project ist die eclipse project datei.

OnlineGameMain Arguments:

register and store player:
http://134.93.175.116:6008 -c g08 testName Adrian

load player , create and join game
http://134.93.175.116:6008 -l g08 -g -j

