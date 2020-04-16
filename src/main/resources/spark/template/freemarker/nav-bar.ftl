 <div class="navigation">
  <#if currentUser??>
      <#if isPlaying!false>
        <a href="/?spectate=yes">Go Spectate</a> |
      </#if>
    <a href="/">my home</a> |
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser}]</a>
    </form>
  <#else>
    <a href="/signin">sign in</a>
  </#if>
 </div>
