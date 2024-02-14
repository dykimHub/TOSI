import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "@/util/http-common";
import router from "@/router";
import { useS3Store } from "@/stores/S3Store";

export const useCustomTaleStore = defineStore("customtale", () => {
  //공개 중인 모든 커스텀동화
  const customTalesList = ref([]);
  const getCustomTalesList = function (opened) {
    axios
      .get(`/customtale?isPublic=${opened}`,opened, { withCredentials: true })
      .then((response) => {
        // console.log(response.data);
        customTalesList.value = response.data;
        customTalesList.value.sort((a, b) => b.customTaleId - a.customTaleId);
      })
      .catch((error) => {
        console.error("Error fetching custom tales:", error);
      });
  };

  //나의책장 - 커스텀동화
  const myCustomTalesList = ref([]);
  const getMyCustomTalesList = async function () {
    await axios.get(`/customtale/user`, { withCredentials: true }).then((response) => {
      myCustomTalesList.value = response.data;
      console.log(myCustomTalesList.value);
    });
  };

//나의책장 - 커스텀동화 공개 여부 수정
const updatedCustomTale = ref([]);
const updateCustomTale = async function (customTaleId, opened) {
  await axios({
    method: "PUT",
    url: `/customtale/${customTaleId}`,
    params: {
      opened: opened,
    },
    withCredentials: true 
  }).then((response) => {
    alert("공개 여부를 수정했어요~");
  });
};


  //나의책장 - 커스텀동화 삭제
  const deleteCustomTale = async function (customTaleId) {
    await axios.delete(`/customtale/${customTaleId}`, { withCredentials: true }).then((response) => {
    });
  };

  //상세조회
  const customTale = ref({});
  const getCustomTale = function (customTaleId) {
    axios.get(`/customtale/${customTaleId}`,customTaleId, { withCredentials: true }).then((response) => {
      customTale.value = response.data;
    });
  };


  const S3Store = useS3Store();
  //등록
  const insertCustomTale = async function (customTale) {
   await axios.post(`/customtale`, customTale, { withCredentials: true }).then(() => {
      // console.log(customTale.value);
      router.push({name:'customTaleList'})
      alert("동화가 저장되었어요.")
    });
  };

  //gptapi 에 동화생성 요청
  const customTaleText = ref("");
  
const getCustomTaleText = async function (userInputMessage) {
  try {
    const response = await axios.post(`/customtale/input`, {
      userMessage: userInputMessage,
    }, { withCredentials: true });

    // console.log(userInputMessage);
    // console.log(response.data);

    customTaleText.value = response.data;
  } catch (error) {
    console.error("커스텀 이야기를 가져오는 중 오류 발생:", error);
  }
};

  const customTaleImage = ref("");
  const getCustomTaleImage = async (imagePrompt) => {
    
  
    const API_KEY = "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";
    const API_URL = "https://api.openai.com/v1/images/generations";
  
    try {
      const response = await fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${API_KEY}`,
        },
        body: JSON.stringify({
          model: "dall-e-3",
          prompt: imagePrompt,
          n: 1,
          size: "1024x1024",
        }),
      });
  
      if (response.ok) {
        const data = await response.json();
        console.log(data);
        if (data && data.data.length > 0) {
          customTaleImage.value = data.data[0].url;
          console.log("Image URL:", customTaleImage.value);
        } else {
          alert("이미지 생성에 실패했습니다. 다시 시도해주세요.");
        }
      } else {
        alert("이미지 생성에 실패했습니다. 다시 시도해주세요.");
      }
    } catch (error) {
      console.log("Error generating image:", error);
      alert("이미지 생성에 실패했습니다. 다시 시도해주세요.");
    }

  }
  
  const resetCustomTale = function(){
    customTaleImage.value = "";
    customTaleText.value = "";
  }

  const pages = ref([]);
  const readCustomTale = async function (text) {
    // console.log(text);
   await axios.post(`/customtale/read`, text, { withCredentials: true }).then((response) => {
    // console.log(response.data);
    pages.value = response.data;
    // console.log(pages.value);  
    });
  };

  return {
    customTalesList,
    getCustomTalesList,
    myCustomTalesList,
    getMyCustomTalesList,
    updatedCustomTale,
    updateCustomTale,
    deleteCustomTale,
    customTale,
    getCustomTale,
    insertCustomTale,
    customTaleText,
    getCustomTaleText,
    customTaleImage,
    getCustomTaleImage,
    resetCustomTale,
    pages,
    readCustomTale,
  };
});