<template>
  <div id="app" class="talelistContainer">
    <div>
      <div class="title">{{ customTaleStore.customTale.title }}</div>
    </div>
    <div class="twoContainer">
      <div class="book-column">
        <div class="btncolumn">
          <RouterLink
            :to="`/customtale/${customTaleStore.customTale.customTaleId}`"
          >
            <button class="button">다시 볼래요!</button>
          </RouterLink>
          <RouterLink :to="`/customtale`">
            <button class="button">나가기</button>
          </RouterLink>
        </div>
      </div>

      <div class="info-column">
        <p class="minititle">친구들의 동화 보기</p>
        <div class="othercustom">
          <div
            v-for="customTale in getRandomCustomTales"
            :key="customTale.customTaleId"
          >
            <div class="onetale">
              <router-link :to="`/customtale/${customTale.customTaleId}`">
                <img
                  :src="customTale.thumbnail"
                  class="img-fluid"
                  style="height: 150px"
                  alt="커스텀이미지"
                />
                <div>{{ customTale.title }}</div>
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { onMounted, computed, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import LoadingModal from "@/components/customTale/loadingModal.vue";

const route = useRoute();
const router = useRouter();

const customTaleStore = useCustomTaleStore();

const getRandomCustomTales = ref([]);
function shuffleArray(array) {
  const shuffledArray = array.slice();
  for (let i = shuffledArray.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [shuffledArray[i], shuffledArray[j]] = [shuffledArray[j], shuffledArray[i]];
  }
  return shuffledArray;
}

const loading = ref(false);

onMounted(async () => {
  // 비동기로 데이터를 먼저 로드
  await customTaleStore.getCustomTalesList(true);
  // await  customTaleStore.getCustomTale(route.params.customTaleId);

  // console.log(customTaleStore.customTalesList);
  // 로드된 데이터를 기반으로 랜덤 아이템 선택
  const randomCustomTales = shuffleArray(customTaleStore.customTalesList).slice(
    0,
    4
  );
  getRandomCustomTales.value = randomCustomTales;
});
</script>

<style scoped>
a {
  color: black;
  text-decoration: none;
}
.talelistContainer {
  background-color: white;
  border-radius: 20px;
  margin: 35px;
  /* padding-top: 40px;
    padding-bottom: 40px; */
  opacity: 0.95;
  padding: 40px 0px 0px 50px;
  border: 5px solid #cee8e8;
  min-height: 90vh;
}

.twoContainer {
  display: flex;
  justify-content: space-between;
  margin-left: 10%;
  margin-right: 10%;
  height: 350px;
}
.title {
  text-decoration: none;
  display: inline-block;
  box-shadow: inset 0 -20px 0 #D3E4FF;
  font-size: 40px;
  margin: 30px 0px 30px 50px;
  line-height: 1;
  text-align: left;
}
.minititle{
  text-decoration: none;
  display: inline-block;
  box-shadow: inset 0 -10px 0 #D3E4FF;
  font-size: 20px;
  /* margin: 30px 0px 0px 0px; */
  margin-bottom: 40px;
  line-height: 1;
  text-align: left;
}
.info-column {
  display: flex;
  align-items: center;
  flex-direction: column;
}

.inputgroup {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.book-column {
  width: 80%;
  overflow: hidden;
  display: flex;
  justify-content: center;
}

.info {
  background-color: #ffffff;
  border: 3px solid #cee8e8;
  border-radius: 20px;
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}

.input {
  margin-bottom: 10px;
}

.btncolumn {
  width: 100%;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  align-content: center;
  flex-direction: column;
}

.book img {
  width: 80%;
  height: 80%;
}

.loading-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.loading-content {
  text-align: center;
  background: white;
  padding: 20px;
  border-radius: 8px;
}

.button {
  margin-top: 20px;
  width: 130px;
  height: 40px;
  /* padding: 10px 25px; */
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow: 3px 3px 5px 0px #0002;
}
.button:hover {
  box-shadow: 7px 7px 5px 0px #0002, 4px 4px 5px 0px #0001;
}

.infobtn {
  text-align: center;
  flex-direction: column;
}

.itemform-container {
  display: flex;
  flex-wrap: wrap;
}
.itemform {
  flex: 0 0 33.33%;
  box-sizing: border-box;
  padding: 0 10px;
  margin-top: 20px;
}
.othercustom {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  justify-content: center;
}
.onetale {
  margin: 20px;
}
</style>
