<template>
    <div class="mainContainer">
        <div>
            <RouterLink to="/membermain">
                <img src="@/assets/logo.png" alt="Logo"/>
            </RouterLink>
        </div>
        <article>
            <div class="slotMachine">슬롯머신</div>
            <div class="toMenus">
                <div class="toMenu">
                    <RouterLink to="/tales">전체 책 보기</RouterLink>
                </div>
                <div class="toMenu">
                    <RouterLink :to="`/`">나의 책장</RouterLink>
                </div>
                <div class="toMenu">
                    <RouterLink to="/customtale/create">나만의 동화 만들기</RouterLink>
                </div>
            </div>
            <div class="top3">
                인기동화
            </div>
        </article>
      </div>
  </template>
<script setup>

</script>

<style scoped>

.mainContainer {
    display: flex;
    flex-direction: column;
    align-items: center;
}

img {
    width: 200px;
}

.toMenus {
    display: flex;
    flex-direction: row;
}

.toMenu {
    padding: 50px;
    display: flex;
}

.slotMachine {
    display: flex;
    justify-content: center;
    margin: 100px;
}

.top3 {
    display: flex;
    justify-content: center;
    margin: 100px;
}
</style>
