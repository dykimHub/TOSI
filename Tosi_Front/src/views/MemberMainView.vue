<template>
    <div class="mainContainer">
        <div>
            <RouterLink to="/membermain">
                <img class="logo" src="@/assets/logo.png" alt="Logo"/>
            </RouterLink>
        </div>
        <article>
            <div class="slotMachine">슬롯머신</div>
            <div class="toMenus">
                <RouterLink to="/tales" class="toMenu">
                    <img class="icon" src="@/assets/talelist.png" />
                    전체 책 보기
                </RouterLink>
                <RouterLink :to="`/`" class="toMenu">
                    <img class="icon" src="@/assets/favorite.png" />
                    나의 책장
                </RouterLink>
                <RouterLink to="/customtale/create" class="toMenu">
                    <img class="icon" src="@/assets/custom.png" />
                    나만의 동화 만들기
                </RouterLink>
            </div>
            <p>지금 인기있어요!</p>
            <div class="top3">
                <ul v-for="tale in Talestore.taleList.slice(0, 3)" :key="tale.taleId">
                <div class="oneTale">
                <RouterLink :to="`/tales/${tale.taleId}`"><img class="thumbnail" :src="tale.thumbnail" /></RouterLink>
                <br>
                <RouterLink :to="`/tales/${tale.taleId}`">{{ tale.title }}</RouterLink>
                <br>
            </div>
        </ul>
            </div>
        </article>
        <TheFooter/>
      </div>
  </template>
<script setup>
import TheFooter from '@/components/common/TheFooter.vue';
import { useTaleStore } from '@/stores/taleStore';
import { onMounted, ref, watch } from 'vue';

const Talestore = useTaleStore();

//TODO 필요없는 코드 삭제
const sortOption = ref('likeCnt');
const sortedTaleList = ref([]);

const sortTaleList = () => {
  switch (sortOption.value) {
    case 'likeCnt':
      sortedTaleList.value = Talestore.taleList.sort((a, b) => b.likeCnt - a.likeCnt);
      break;
  }
};

onMounted(() => {
  Talestore.getTaleList();
  watch(() => Talestore.taleList, sortTaleList, { immediate: true });
  watch(sortOption, sortTaleList);
});

</script>

<style scoped>

.mainContainer {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.logo {
    width: 200px;
    margin-top: 40px;
}

.toMenus {
    display: flex;
    flex-direction: row;
    width: auto;
}

.toMenu {
    padding: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 18em;
    text-align: center;
    margin: 40px;
    flex-direction: column;
}

.slotMachine {
    display: flex;
    justify-content: center;
    width: auto;
}

.top3 {
    display: flex;
    justify-content: center;
    height: auto;
    justify-content: center;
    flex-direction: row;
    flex-wrap: wrap;
    align-items: center;
    justify-content: space-around;
    margin: 40px;
}

.slotMachine, .toMenu, .top3 {
    background-color: white;
    border-radius: 10px;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.slotMachine{
    margin: 50px;
    height: 100px;
}

p {
    text-align: center;
    display: inline;
    text-shadow: -1px -1px 2px white, 1px -1px 2px white, -1px 1px 2px white, 1px 1px 2px white;
}

.oneTale {
    width: 15em;
    text-align: center;
    margin: 2em;
}

.thumbnail {
    border-radius: 5px;
    width: 175px;
}

.icon {
    width: 100px;
}

a {
    text-decoration: none;
    color: black;
}
</style>