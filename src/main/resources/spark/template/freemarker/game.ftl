<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
   <#if play??>
      <#if play == true>
          <meta http-equiv="refresh" content="3">
      </#if>
    </#if>
  <title>${title} | Web Checkers</title>
  <link rel="stylesheet" href="/css/style.css">
  <link rel="stylesheet" href="/css/game.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script>
  window.gameData = {
    "gameID" : "${gameID!}",
    "currentUser" : "${currentUser}",
    "viewMode" : "${viewMode}",
    "modeOptions" : ${modeOptionsAsJSON!'{}'},
    "redPlayer" : "${redPlayer}",
    "whitePlayer" : "${whitePlayer}",
    "activeColor" : "${activeColor}"
  };
  </script>
</head>
<body>
  <div class="page">
    <h1>Web Checkers | Game View</h1>
    
    <#include "nav-bar.ftl" />

    <div class="body">

      <div id="help_text" class="INFO"></div>

      <div>
        <div id="game-controls">
        
          <fieldset id="game-info">
            <legend>Info</legend>

            <#include "message.ftl" />

            <div>
              <table data-color='RED'>
                <tr>
                  <td><img src="../img/single-piece-red.svg" /></td>
                  <td class="name">Red</td>
                </tr>
              </table>
              <table data-color='WHITE'>
                <tr>
                  <td><img src="../img/single-piece-white.svg" /></td>
                  <td class="name">White</td>
                </tr>
              </table>
            </div>
          </fieldset>
          
          <fieldset id="game-toolbar">
            <legend>Controls</legend>
            <div class="toolbar"></div>
              <#if viewMode =="SPECTATOR">
              <button onclick="window.location.href =
                      '/spectator/game?gameID=${gameID}&orientation=${otherOrientation}';">Rotate Board</button>
                  </#if>
               <#if viewMode == "REPLAY">
               		 <button onclick="window.location.href =
               			 '/replay/game?gameID=${gameID}&orientation=${otherOrientation}';">Rotate Board</a>
                                <button onclick="window.location.href = '/replay/game?gameID=${gameID}&play=${true?c}&&orientation=${otherOrientation}';
                                ">Play</button>
               		 <br>
                               Move #${movesMade}/${moveCount}
               </#if>
          </fieldset>
          
        </div>
  
        <div class="game-board">
          <table id="game-board">
          <#if currentUser == whitePlayer>
            <tbody>
                <#list board.iterator() as row>
                <tr data-row="${row.index}">
                <#list row.iterator() as space>
                    <td data-cell="${space.cellIdx}"
                        <#if space.isValid() >
                        class="Space"
                        </#if>
                        >
                    <#if space.piece??>
                    <div class="Piece"
                           id="piece-${row.index}-${space.cellIdx}"
                        data-type="${space.piece.type}"
                        data-color="${space.piece.color}">
                    </div>
                    </#if>
                    </td>
                </#list>
                </tr>
                </#list>
                </tbody>
          <#else>
            <tbody>
                 <#list board.iterator()?sequence?reverse as row>
                 <tr data-row="${row.index}">
                 <#list row.iterator()?sequence?reverse as space>
                    <td data-cell="${space.cellIdx}"
                        <#if space.isValid() >
                        class="Space"
                        </#if>
                        >
                    <#if space.piece??>
                    <div class="Piece"
                           id="piece-${row.index}-${space.cellIdx}"
                        data-type="${space.piece.type}"
                        data-color="${space.piece.color}">
                    </div>
                    </#if>
                    </td>
                    </#list>
                    </tr>
                    </#list>
                    </tbody>
             </#if>
          </table>
        </div>
      </div>

    </div>
  </div>

  <audio id="audio" src="http://www.soundjay.com/button/beep-07.mp3" autostart="false" ></audio>
  
  <script data-main="/js/game/index" src="/js/require.js"></script>
  
</body>
</html>
