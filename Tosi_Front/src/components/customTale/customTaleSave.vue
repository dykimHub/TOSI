<template>
  <div id="app" class="talelistContainer">
    <div>
      <div class="title">나만의 동화 만들기</div>
    </div>

    <loading-modal :is-loading="loading"></loading-modal>

    <div class="twoContainer">

      <div class="book-column">
         <div class="btncolumn">
            <button class="button" @click="retry">다시 만들래요!</button>
          <RouterLink :to="`/customtale`">
            <button class="button">나가기</button>
          </RouterLink>
        </div>
      </div>

      <div class="info-column">
        <p class="minititle">내가 만든 동화 저장하기</p>
        <div class="savecustom">
          <div>
            <label for="title" class="form-label">동화의 제목</label>
            <input
              v-model="customTale.title"
              type="text"
              class="form-control"
              id="title"
              required
              maxlength="30"
            />
          </div>
          <div>
            <label for="isPublic">
              <input
                type="radio"
                v-model="customTale.public"
                :value="true"
                id="isPublic"
              />
              친구들과 함께 보기
            </label>
            <label for="isNotPublic">
              <input
                type="radio"
                v-model="customTale.public"
                :value="false"
                id="isNotPublic"
              />
              나만 보기
            </label>
          </div>
          <div style="display: flex; text-align: center;justify-content: center;">
            <button @click="saveCustomTale" class="button" style="text-align: center;">
              동화 저장
            </button>
          </div>
          </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { useS3Store } from "@/stores/S3Store";
import { onMounted, ref } from "vue";
const customTaleStore = useCustomTaleStore();
const S3Store = useS3Store();
import LoadingModal from "@/components/customTale/loadingModal.vue";
import router from "@/router";

const loading = ref(false);
const customTale = ref({
  userId: "3",
  title: "",
  content: customTaleStore.customTaleText.gptMessage,
  public: true,
  thumbnail: customTaleStore.customTaleImage,
  time: "1",
});

const saveCustomTale = async function () {
  if (customTale.value.title == "") {
    alert("제목을 입력해주세요.");
    return;
  }
  try {
    loading.value = true;
    await customTaleStore.insertCustomTale(customTale.value);
  } finally {
    loading.value = false;
  }
};

const retry = function(){
  customTaleStore.resetCustomTale();
  router.push({name:'customTaleCreate'})
}
</script>

<style scoped>
.talelistContainer {
  background-color: white;
  border-radius: 20px;
  margin: 35px;
  /* padding-top: 40px;
  padding-bottom: 40px; */
  opacity: 0.95;
  padding: 40px 0px 0px 50px;
  border: 5px solid #cee8e8;
  width: 800px;
}

.twoContainer {
  display: flex;
  justify-content: space-between;
  margin-left: 10%;
  margin-right: 10%;
  height: 250px;
  margin-bottom: 50px;
}
.title {
  text-decoration: none;
  display: inline-block;
  box-shadow: inset 0 -20px 0 #c4ecb0;
  font-size: 40px;
  margin: 30px 0px 30px 50px;
  margin-bottom: 40px;
  line-height: 1;
  text-align: left;
}
.minititle{
  text-decoration: none;
  display: inline-block;
  box-shadow: inset 0 -10px 0 #c4ecb0;
  font-size: 20px;
  /* margin: 30px 0px 0px 0px; */
  margin-top: 30px;
  margin-bottom: 40px;
  line-height: 1;
  text-align: left;
}
.info-column {
  width: 60%;
  display: flex;
  align-items: center;
  flex-direction: column;
  background-color: #ffffff;
  border: 3px solid #cee8e8;
  border-radius: 20px;
}

.inputgroup {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.book-column {
  width: 50%;
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

.book {
  width: 100%;
  display: flex;
  text-align: center;
  justify-content: center;
  align-items: center;
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
  margin-bottom: 50px;
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
.btncolumn {
  width: 100%;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  align-content: center;
  flex-direction: column;
}
.infobtn {
  text-align: center;
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
.savecustom{
display: flex;
flex-direction: column;
}
</style>
