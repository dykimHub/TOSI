<template>
  <div>
    <loading-modal :is-loading="loading"></loading-modal>
    <div>
      <img
        :src="customTaleStore.customTaleImage"
        class="img-fluid"
        style="height: 300px"
      />
      <p>{{ customTaleStore.customTaleText.gptMessage }}</p>
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
      <button @click="saveCustomTale" class="btn btn-primary">동화 저장</button>
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

const loading = ref(false);
const customTale = ref({
  // userId: userStore.loginUser.user_id,
  userId: "1",
  title: "",
  content: customTaleStore.customTaleText.gptMessage,
  // content: "한적한 시골 마을에 성주라는 아이가 살고 있었어.",
  public: true,
  thumbnail: customTaleStore.customTaleImage,
  // thumbnail:
  //   "https://talebucket.s3.ap-northeast-2.amazonaws.com/png_skoid_6aaadede-4fb3-4698-a8f6-684d7786b067_sktid_a48cca56-e6da-484e-a814-9c849652bcb3_skt_2024-02-03T13_3A46_3A14Z_ske_2024-02-04T13_3A46_3A14Z_sks_b_skv_2021-08-06_sig_REj_2BfoVSBAbB4jW8XNZAXIcE5vyvDtn2q2w_2B6w0KIOY_3D",
  //time: tts로 나온 시간
  time: "3",
});

const saveCustomTale =  async function () {
<<<<<<< HEAD
=======
  if(customTale.value.title == ""){
    alert("제목을 입력해주세요.");
    return;
  }
>>>>>>> 4d40147d770f53e2189b668c4be10f034a658ea0
  try{
  loading.value = true;
  await customTaleStore.insertCustomTale(customTale.value);
  }finally{
    loading.value = false;
  }
};
</script>

<style></style>
