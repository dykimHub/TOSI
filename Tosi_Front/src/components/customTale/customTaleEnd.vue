<template>
  <div class="play">
    <div class="container">
      <div class="topOfTaleList">
        <div class="bigtitle">{{ customTaleStore.customTale.title }}</div>
        <div class="taleinfo">
          <div class="leftImg">
            <div class="bookbtn">
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

          <div class="chat">
            <div class="startbox">
              <div class="infobox">
                <div class="voicetitle">친구들의 동화 보기</div>
                <div class="inputgroup">
                  <div class="othercustom">
                    <div class="twoColumns">
                      <div
                        v-for="customTale in getRandomCustomTales"
                        :key="customTale.customTaleId"
                        class="onetale"
                      >
                        <router-link
                          :to="`/customtale/${customTale.customTaleId}`"
                        >
                          <img
                            :src="customTale.thumbnail"
                            class="img-fluid"
                            style="height: 150px"
                            alt="커스텀이미지"
                          />
                          <div class="othertitle">
                            {{ customTale.title }}
                          </div>
                        </router-link>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
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


onMounted(async () => {
  await customTaleStore.getCustomTalesList(true);

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
.infobox {
  border: 5px solid #cee8e8;
  border-radius: 30px;
  background-color: rgb(255, 255, 255);
  font-size: 20px;
  /* margin-right: 70px; */
}
.startbox {
  display: flex;
  justify-content: center;
}
.inputgroup {
  padding: 10px;
}
.play {
  background-color: white;
  border-radius: 50px;
  margin-top: 35px;
  padding: 40px 30px;
  border: 5px solid #cee8e8;
  margin-bottom: 20px;
}
.container {
  display: flex;
  /* width: 70vw; */
  margin: 40px 0 0 60px;
}
.bigtitle {
  text-decoration: none;
  display: inline-block;
  box-shadow: inset 0 -20px 0 #d3e4ff;
  font-size: 40px;
  /* margin: 30px 0px 30px 50px; */
  margin-bottom: 40px;
  line-height: 1;
  text-align: left;
}
.taleinfo {
  display: flex;
}
.title {
  margin: 30px 0px 25px 0px;
  font-size: 35px;
  width: 300px;
  text-align: center;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.leftImg {
  width: 400px;
  height: 450px;
  background-image: url(@/assets/book_end.png);
  background-size: cover;
  padding: 10px 10px 0px 57px;
  margin-right: 25px;
  box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.3);
}
.thumbnail {
  width: 300px;
  height: 300px;
  border: 5px solid white;
  margin-top: 50px;
}

.align-items-center {
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
}
.form-wrapper {
  width: 100%;
  max-width: 28rem;
}
.chat {
  width: 520px;
  height: 500px;
  align-items: center;
}
.titleimg {
  width: 40px;
  height: 40px;
  margin: 25px 10px 0 0;
}
.selecttitle {
  font-size: 20px;
  margin: 30px 10px 0 0;
}
.form-select {
  font-size: 18px;
}

.speaker-image {
  height: 25px;
  width: 25px;
  cursor: pointer;
}

.mic {
  width: 30px;
  height: 30px;
  margin-right: 5px;
}
.voicetitle {
  display: flex;
  align-items: center;
  justify-content: center;
  border: 5px solid #cee8e8;
  border-radius: 30px;
  width: 260px;
  height: 60px;
  text-align: center;
  margin: -40px 0px 0px 70px;
  background-color: #ebffdf;
  position: relative;
  z-index: 5;
  font-size: 23px;
}
.voicebox {
  border: 5px solid #cee8e8;
  border-radius: 30px;
  background-color: rgb(255, 255, 255);
  font-size: 20px;
  width: 370px;
  height: 150px;
}
.button {
  margin-top: 20px;
  width: 130px;
  height: 40px;
  /* padding: 10px 25px; */
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: white;
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
}
.publicbox {
  margin-top: 20px;
}
.bookbtn {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 120px;
  margin-right: 40px;
}
.twoColumns {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* 2개의 열로 설정 */
  gap: 20px; /* 열 사이의 간격 설정 */
}
.onetale {
  margin: 10px;
}
.othertitle {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  width: 150px;
}
</style>
