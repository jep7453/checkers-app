<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

      <#if currentUser??>
        <p>
            Users Playing:
        </p>
        <#list playerList as player>
            <p>
                <a href="/game?player=${player}">${player}</a>
            </p>
        </#list>
      <#else>
        <p>
            Users Playing: ${totalPlayers}
        </p>
      </#if>
      <#if gameList??>
        <p>
            Games Being Played:
        </p>

        <#list gameList as game>
            <p>
                <#if game.redPlayer.name == currentUser || game.whitePlayer.name == currentUser>
                    <a href="/game">${game.title}: ${game.numSpectators} Spectators (click to resume your game)</a>
                    <#else >
                    <a href="/spectator/game?gameID=${game.gameID}">${game.title}: ${game.numSpectators} Spectators</a>
                </#if>

            </p>
        </#list>
      </#if>

      <#if replayList??>
        <p>
            Replays:
        </p>
           <#list replayList as game>
              <p>
                   <a href="/replay/game?gameID=${game.gameID}">${game.title}</a>
              </p>
           </#list>
      </#if>



    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

  </div>

</div>
</body>

</html>
