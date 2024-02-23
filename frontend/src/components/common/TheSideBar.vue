<template>
  <div class="wholeContainer">
    <ul>
      <div class="side" :class="{ active: $route.path.startsWith('/tales') }">
        <li><RouterLink to="/tales">전체 책 보기</RouterLink></li>
      </div>
      <div class="side" :class="{ active: $route.path.startsWith('/bookshelf') }">
        <li><RouterLink to="/bookshelf">나의 책장</RouterLink></li>
      </div>
      <div class="side" :class="{ active:$route.path==='/customTale/save' ||  $route.path==='/customTale/create' || $route.path==='/customTale/createplay' || $route.path.startsWith('/customTale/createplay') || $route.path.startsWith('/customTale/loading')}">
        <li><RouterLink to="/customTale/create">나만의 동화 만들기</RouterLink></li>
      </div>
      <div class="side" :class="{ active: $route.path==='/customTale' ||  $route.path.startsWith('/customtale/') || $route.path.startsWith('/customTale/play')  || $route.path.startsWith('/customTale/play') || $route.path==='/customTale/end' }">
        <li><RouterLink to="/customTale">친구들의 동화 보기</RouterLink></li>
      </div>
      <div class="side" :class="{ active: $route.path.startsWith('/userInfo') }">
        <li><RouterLink to="/userInfo">마이페이지</RouterLink></li>
      </div>
    </ul>
  </div>
</template>
<script setup></script>
<style scoped>
.wholeContainer {
    width: 13rem;
    position: fixed;
    margin-top: 40px;
    background-color: #cee8e8;
    border-top-left-radius: 50px;
    border-bottom-left-radius: 50px;
    padding-top: 10px;
    padding-bottom: 10px;
    border: 5px solid #cee8e8;
    position: relative;
    left: 43px;
    top: 40px;
    font-size:large;
}

.active {
  width: 13rem;
  background-color: white;
  border-top-left-radius: 50px;
  border-bottom-left-radius: 50px;
  padding: 4px;
  margin-left: -10px;
}

.side:not(.active) :hover {
  color: white;
}

.active li {
  margin-left: 8px;
}

li {
  list-style-type: none;
  margin-top: 14px;
  margin-bottom: 14px;
}

a {
  text-decoration: none;
  color: black;
}
</style>
