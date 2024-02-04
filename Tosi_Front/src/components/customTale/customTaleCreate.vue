<template>
  <div id="app" class="container">
    <h1 class="text-center my-4">나만의 동화 만들기</h1>

    <loading-modal :is-loading="loading"></loading-modal>
    <div>
      <img
        v-if="customTaleStore.customTaleImage"
        :src="customTaleStore.customTaleImage"
        class="img-fluid"
        style="height: 300px"
      />
      <img v-else :src="randomImageUrl" alt="" style="height: 300px" />
    </div>

    <!-- First input field -->
    <div class="input">
      <label for="prompt1" class="form-label">주인공</label>
      <div class="input-group">
        <input
          type="text"
          id="prompt1"
          v-model="prompt[0]"
          class="form-control"
        />
      </div>
    </div>

    <!-- Second input field -->
    <div class="input">
      <label for="prompt2" class="form-label">배경</label>
      <div class="input-group">
        <input
          type="text"
          id="prompt2"
          v-model="prompt[1]"
          class="form-control"
        />
      </div>
    </div>

    <!-- Third input field -->
    <div class="input">
      <label for="prompt3" class="form-label">키워드</label>
      <div class="input-group">
        <input
          type="text"
          id="prompt3"
          v-model="prompt[2]"
          class="form-control"
        />
      </div>
    </div>

    <div class="mb-3">
      <button @click="generateCustomTale" class="btn btn-primary">
        동화 생성
      </button>
    </div>

    <!-- <div v-if="imageUrl !== ''"> -->
    <h2>Generated Image:</h2>
    <img :src="customTaleStore.customTaleImage" class="img-fluid" style="height: 300px" />
    <!-- </div> -->

    <div>
      <h2>Generated text:</h2>
      <p>{{ customTaleStore.customTaleText.gptMessage }}</p>
      <router-link to="/customtale/save" class="btn btn-primary">
      재생
    </router-link>
    </div>
  </div>
</template>

<script setup>
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { onMounted, ref } from "vue";
import LoadingModal from "@/components/customTale/loadingModal.vue";
const customTaleStore = useCustomTaleStore();

const generateRandomImageUrl = () => {
  const randomNumber = Math.floor(Math.random() * 9) + 1; // 1부터 9까지의 랜덤 숫자 생성
  return `https://customtalebucket.s3.ap-northeast-2.amazonaws.com/customthum${randomNumber}.png`;
};
const randomImageUrl = ref(generateRandomImageUrl());

const prompt = ref(["", "", ""]);

const loading = ref(false);
const generateCustomTale = async function () {
  try {
    loading.value = true;
    if (prompt.value.some((prompt) => prompt === "")) {
      alert("키워드를 모두 입력해주세요.");
      return;
    }
  const imagePrompt = "3D animation illustrations for children's books with " +
      prompt.value[0] +
      " child, " +
      prompt.value[1] +
      " is the background and " +
      prompt.value[2] +
      // + ","+ prompt.value[3] + ","+ prompt.value[4]
      "are the main keywords, bright and lively background, simply express it as a modern character. Don't include any text in the image. only image."
  //  await customTaleStore.getCustomTaleImage(imagePrompt);

    const gptPrompt =
      prompt.value[0] +
      "라는 아이를 주인공, " +
      prompt.value[1] +
      "을 배경, " +
      prompt.value[2] +
      "를 이용해 500자 내외의 교훈있는 동화를 만들어줘. 줄바꿈은 하지 말아줘. 성별언급은 하지말아줘. 자연스럽고 매끄러운 문맥.";

    await customTaleStore.getCustomTaleText(gptPrompt);
  } catch (error) {
    console.error("커스텀 동화 생성 오류:", error);
  } finally {
    // await Vue.nextTick();
    loading.value = false;
  }
};

onMounted(() => {
  console.log("Image URL:", customTaleStore.customTaleImage);
  console.log("tale", customTaleStore.customTaleText);
});
</script>

<style>
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
</style>