<template>
  <loading-modal :is-loading="loading"></loading-modal>
  <div class="play">
    <div class="container">
      <div class="topOfTaleList">
        <div class="bigtitle">나만의 동화 만들기</div>
        <div class="taleinfo">
          <div class="leftImg">
            <div class="bookbtn">
              <button class="button" @click="retry">다시 만들래요!</button>
              <button class="button" @click="nosave">나가기</button>
            </div>
          </div>

          <div class="chat">
            <div class="startbox">
              <div class="infobox">
                <div class="voicetitle">내가 만든 동화 저장하기</div>
                <div class="inputgroup">
                  <div>
                    <label for="title" class="form-label">동화의 제목</label>
                    <input
                      v-model="customTale.title"
                      type="text"
                      class="form-control"
                      id="title"
                      required
                      maxlength="20"
                    />
                  </div>
                  <div class="publicbox">
                    <label for="isPublic">
                      <input
                        type="radio"
                        v-model="customTale.public"
                        :value="true"
                        id="isPublic"
                      />
                      친구들과 함께 보기
                    </label>
                    <label for="isNotPublic" style="margin-left: 15px">
                      <input
                        type="radio"
                        v-model="customTale.public"
                        :value="false"
                        id="isNotPublic"
                      />
                      나만 보기
                    </label>
                  </div>
                </div>
                <div class="infobtn">
                  <button
                    @click="saveCustomTale"
                    class="button"
                    style="text-align: center"
                  >
                    동화 저장
                  </button>
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
import { onMounted, ref } from "vue";
const customTaleStore = useCustomTaleStore();
import LoadingModal from "@/components/customTale/loadingModal.vue";
import router from "@/router";

const loading = ref(false);
const customTale = ref({
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

const retry = function () {
  if (confirm("동화를 저장하지 않고 다시 만드시겠습니까?") == true) {
    customTaleStore.resetCustomTale();
    router.push({ name: "customTaleCreate" });
  } else {
    return false;
  }
};

const nosave = function () {
  if (confirm("동화를 저장하지 않고 나가시겠습니까?") == true) {
    customTaleStore.resetCustomTale();
    router.push({ name: "customTaleList" });
  } else {
    return false;
  }
};
</script>
<style scoped>
.infobox {
  border: 5px solid #cee8e8;
  border-radius: 30px;
  background-color: rgb(255, 255, 255);
  font-size: 20px;
  width: 350px;
  height: 270px;
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
  /* width: 80vw; */
}
.container {
  display: flex;
  width: 70vw;
  margin: 40px 0 0 60px;
}
.bigtitle {
  text-decoration: none;
  display: inline-block;
  box-shadow: inset 0 -20px 0 #c4ecb0;
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
  overflow: hidden; /* 글자가 넘칠 경우 숨김 처리 */
  white-space: nowrap; /* 글자가 한 줄에 표시되도록 설정 */
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
.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  margin: 15px 15px 0 15px;
}
.chat {
  width: 520px;
  height: 500px;
  display: flex;
  align-items: center;
  justify-content: center;
  /* padding-right: 60px; */
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
.startbox {
  display: flex;
  flex-direction: column;
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
  margin: -40px 0px 0px 40px;
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
</style>
