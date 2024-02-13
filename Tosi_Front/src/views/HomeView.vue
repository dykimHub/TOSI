<template>
    <TheHeaderNav />
    <div class="mainContainer">
        <article v-if="isMain">
            <!-- <div class="slotMachine">슬롯머신</div> -->
            <div class="toMenus">
                <RouterLink to="/tales" class="toMenu">
                    <img class="icon" src="@/assets/Play.png" />
                    전체 책 보기
                </RouterLink>
                <RouterLink to="/customtale/create" class="toMenu">
                    <img class="icon" src="@/assets/Maker.png" />
                    나만의 동화 만들기
                </RouterLink>
                <RouterLink :to="`/bookshelf`" class="toMenu">
                    <img class="icon" src="@/assets/Mine.png" />
                    나의 책장
                </RouterLink>
            </div>
            <h5>최근에 친구들이 만든 이야기에요!</h5>
            <div class="top3">
                <ul v-for="tale in  CTalestore.customTalesList.slice(-3).reverse()" :key="tale.taleId">
                    <div class="oneTale">
                        <RouterLink :to="`/tales/${tale.taleId}`"
                            ><img class="thumbnail" :src="tale.thumbnail" />
                        </RouterLink>
                        <br />
                        <RouterLink :to="`/tales/${tale.taleId}`">{{ tale.title }}</RouterLink>
                        <br />
                    </div>
                </ul>
            </div>
        </article>
        <div v-else class="splitView">
            <div class="sideBar">
                <TheSideBar />
            </div>
            <div class="content">
                <RouterView />
            </div>
        </div>
        <TheFooter />
    </div>
</template>
<script setup>
import TheHeaderNav from "@/components/common/TheHeaderNav.vue";
import TheFooter from "@/components/common/TheFooter.vue";
import TheSideBar from "@/components/common/TheSideBar.vue";

import { useCustomTaleStore } from "@/stores/customTaleStore";
import { onMounted, ref, watch } from "vue";
import { useRouter } from "vue-router";
const CTalestore = useCustomTaleStore();
const router = useRouter();

const isMain = ref(true);
const sortOption = ref("likeCnt");
const sortedTaleList = ref([]);

const sortTaleList = () => {
    switch (sortOption.value) {
        case "likeCnt":
            sortedTaleList.value = CTalestore.customTalesList.sort((a, b) => b.likeCnt - a.likeCnt);
            break;
    }
};

onMounted(async () => {
    CTalestore.getCustomTalesList();
    watch(() => CTalestore.getCustomTalesList, sortTaleList, { immediate: true });
    watch(sortOption, sortTaleList);

    await router.afterEach((to) => {
        isMain.value = to.path === "/tosi";
    });
});
</script>

<style scoped>
TheHeaderNav {
    width: 100%; /* Header의 너비를 화면 전체로 설정 */
}

.mainContainer {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center; /* 수평 가운데 정렬을 위해 추가 */
    width: 100%; /* 화면 전체 너비를 차지하도록 설정 */
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

.slotMachine,
.toMenu,
.top3 {
    background-color: white;
    border-radius: 10px;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.slotMachine {
    margin: 50px;
    height: 100px;
}

h5 {
    text-align: center;
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

.splitView {
    display: flex;
}

.sideBar {
    position: fixed; /* 왼쪽에 고정 */
    left: 0; /* 왼쪽에 정렬 */
    top: 220px; /* 위쪽에 정렬 */
    height: 100%; /* 화면 전체 높이로 설정 */
    width: 15em;
    z-index: 1; /* 사이드바가 제일 앞쪽에 렌더링되도록 */
}

.content {
    margin-left: 15em; /* 사이드바 너비만큼 */
    flex-grow: 1; /* 남은 공간 차지 */
}

a {
    text-decoration: none;
    color: black;
}

.content {
    margin-left: 13em;
}

a {
    text-decoration: none;
    color: black;
}
</style>
