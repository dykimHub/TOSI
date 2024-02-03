<template>
  <div>save</div>
  <img
        :src="customTaleStore.customTaleImage"
        class="img-fluid"
        style="height: 300px"
      />
      <p>{{ customTaleStore.customTaleText.gptMessage }}</p>
      <div>
            <label for="title" class="form-label">동화의 제목</label>
            <input v-model="customTale.title" type="text" class="form-control" id="title" required
            maxlength="30">
      </div>
      <div>
        <label for="isPublic">
        <input
          type="radio" v-model="customTale.public" :value=true id="isPublic"/>
        친구들과 함께 보기
      </label>
        <label for="isNotPublic">
        <input
          type="radio" v-model="customTale.public" :value=false id="isNotPublic"/>
        나만 보기
      </label>
      </div>
      <button @click="saveCustomTale" class="btn btn-primary">
        동화 저장
      </button>

</template>

<script setup>
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { useS3Store } from "@/stores/S3Store";
import { onMounted, ref } from "vue";
import axios from 'axios';
import router from "@/router";
const customTaleStore = useCustomTaleStore();
const S3Store = useS3Store();


const customTale = ref({
  // userId: userStore.loginUser.user_id,
  userId: "1",
  title: "",
  content: customTaleStore.customTaleText.gptMessage,
  // content: "한적한 시골 마을에 성주라는 아이가 살고 있었어.",
  public: true,
  thumbnail: customTaleStore.customTaleImage,
  // thumbnail: "https://oaidalleapiprodscus.blob.core.windows.net/private/org-gCaNZNKVUzVGW51QLiJ3CzrP/user-FL8u4CRfTrB3bjRaam2snOPc/img-d2AAiI3I3F6jeUxAIop5i4J2.png?st=2024-02-03T14%3A45%3A09Z&se=2024-02-03T16%3A45%3A09Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2024-02-03T02%3A03%3A04Z&ske=2024-02-04T02%3A03%3A04Z&sks=b&skv=2021-08-06&sig=8VTCA4KQ3BFDxFLCjhyVaxs%2BQ34xgYfoyKFoJ1M0kf8%3D",
  //time: tts로 나온 시간 
  time: "3",
})

const saveCustomTale = function(){
 customTaleStore.insertCustomTale(customTale.value);
}

</script>

<style></style>
