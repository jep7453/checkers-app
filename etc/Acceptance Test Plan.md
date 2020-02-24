| User Story | Acceptance Criterion | Sprint 1 | Tester initials; date; comments (required if test failed) | Sprint 2 | Tester initials; date; comments (required if test failed) | Sprint 3 | Tester initials; date; comments (required if test failed) |
| ---------- | -------------------- | -------- | --------------------------------------------------------- | -------- | --------------------------------------------------------- | -------- | --------------------------------------------------------- |
| As a Player I want to sign-in so that I can play a game of checkers. | Given that I have not yet signed in when I see the Home page then I must see a means to sign-in. (such as a link or button) |
|| "Given that I am not signed-in when I do click on the sign-in link then I expect to be taken to the Sign-in page, with a means to enter a player name." ||||||
|| Given that no one else is using my name when I enter my name containing at least one alphanumeric character and optionally spaces in the sign-in form and click the Sign-in button then I expect the system to reserve my name and navigate back to the Home page. ||||||
|| Given that I am on the Sign-in page when I enter a name that does not contain at least one alphanumeric character or contains one or more characters that are not alphanumeric or spaces and click the Sign-in button then I expect the system to reject this name and return the Sign-in page ||||||
|| Given that someone else with my name has signed-in when I enter my name in the Sign-in form  and click the Sign-in button then I expect the system to reject my sign-in and return the Sign-in page for me to try a different name. ||||||
|| Given that I am signed-in when I navigate to the Home page then I expect to see a list of all other signed-in players.  (NOTE: in the next story you will use this list to pick opponents for checkers games.) ||||||
|| Given that I am not signed-in when I navigate to the Home page then I expect to see a message of how many players are signed-in but not a list of them (for privacy reasons). ||||||
| As a Player I want to start a game so that I can play checkers with an opponent. | Given that I'm signed in when I view the Home page then I can start a game by selecting a player listed on the Home page. ||||||
|| Given that the player I selected isn't yet in a game when I select that player then the system will begin a checkers game and assign me as the starting (Red) player and my opponent as the White player. ||||||
|| Given that the player I selected is already in a game when I select that player then the system will return me to the Home page with an error message. ||||||
|| Given that I'm waiting for a game when another player selects a game with me then the system will automatically send me to the Game View as the White player.  NOTE: the `home.ftl` HTML includes a `<meta>` tag that tells the browser to refresh the game every 5 seconds; thus you need to update the `GetHomeRoute` controller to handle the situation when a player is assigned a game. ||||||
|| Given a valid, initial game board when I drag a piece to a white space then the piece should not be droppable. ||||||
|| Given a valid, initial game board when I drag a piece to an occupied space then the piece should not be droppable. ||||||
|| Given a valid, initial game board when I drag a piece to an open space then the piece should be droppable.  NOTE: In this Story the drop action should not do anything; piece movement will be the focus of future stories. ||||||
|| Given a valid, initial game board when I view the board in the browser then my pieces are oriented on the bottom of the board grid just like I would see the board if I were playing in the physical world. ||||||
| As a player, I want to be able to move checker pieces diagonally so that I can take a turn. |||||||
| As a player I want to jump an opposing piece so that I can win the game. |||||||
| As a player I want to eliminate multiple pieces so that I can win the game. |||||||
| As a player, I want my piece promoted to a king if it reaches the other side of the board so that I can move it backwards. |||||||
| As a player, I want to sign-out so that I can stop playing checkers. |||||||
| As a player, I want the game to end if I cannot make a move so that my opponent can be declared the winner. |||||||
| As a player, I want the game to end if all of one player's pieces have been captured so that the game can have a winner. |||||||
| As a player I want to leave the game so that the game can end early. |||||||
| As a Player I want to spectate a game so that I can see the moves being made in real time. |||||||
| As a player, I want to be able to replay the game so that I can see what moves I used in the previous game. |||||||
