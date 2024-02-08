<template>
    <div v-if="customTaleStore.pages">
      <div class="play">
        <div class="title">{{ customTaleStore.customTale.title }}</div>
        <div class="book">
          <div class="cover"><img :src="customTaleStore.customTale.thumbnail" class="coverImg" /></div>
          <div class="flip-book">
            <div
              class="flip"
              v-for="(page, index) in pages"
              :key="`page-${index}`"
              :class="{ flipped: page.flipped }"
              :style="{ zIndex: zIndexes[index] }"
            >
              <div class="back" v-if="index > 1">
                <img :src="customTaleStore.customTale.thumbnail" class="leftImg" />
                <img src="@/assets/leftarrow.gif" class="left" />
                <img
                  src="@/assets/leftarrowstatic.png"
                  class="leftstatic"
                  @click="flipPage(index, false)"
                />
              </div>
              <div class="front pre-wrap">
                {{ pages[index].right }}
                <div v-if="index === 0">
                  <img src="@/assets/end.gif" class="end" @click="goToEnd" />
                </div>
                <div v-else>
                  <img src="@/assets/rightarrow.gif" class="right" />
                  <img
                    src="@/assets/rightarrowstatic.png"
                    class="rightstatic"
                    @click="flipPage(index, true)"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else>is Loading...</div>
  </template>
  
  <script setup>
  import { ref, computed, reactive, onMounted } from "vue";
  import { useCustomTaleStore } from "@/stores/customTaleStore";
  import { useRouter } from "vue-router";
  
  const customTaleStore = useCustomTaleStore();
  const router = useRouter();
  const pages = customTaleStore.pages.reverse();
  
  const props = defineProps({
    speaker: String,
  });
  
  const goToEnd = () => {
    router.push({ name: "customTaleEnd", params: { taleId: customTaleStore.customTaleId } });
  };
  
  // 페이지 배열의 인덱스를 저장해서 zindex 배열로 만듦
  // zindex는 클수록 위쪽에 위치함
  // map함수로 pages를 돌면서 index + 1을 저장하고 있음
  const zIndexes = reactive(pages.map((_, index) => index + 1));
  
  // index: 뒤집을 페이지의 인덱스
  // flip: true; 뒤집은 상태, false; 이전 페이지 펼친 상태
  function flipPage(index, flip) {
    pages[index].flipped = flip;
  
    // 페이지를 뒤집을 때
    if (flip) {
      zIndexes.forEach((_, i) => {
        if (i === index) {
          // 페이지 인덱스가 클수록 z-index가 낮아야 함
          // 제일 낮은 인덱스(첫페이지)가 맨 위에 있어야 해서
          zIndexes[i] = pages.length - index;
        }
      });
    } else {
      // 이전 상태로 되돌리면 해당 페이지가 제일 위에 와야함
      // zindex의 max값에 1을 더한 값을 저장
      const maxZIndex = Math.max(...zIndexes) + 1;
      zIndexes[index] = maxZIndex;
  
      // 다른 페이지들의 z-index 업데이트
      zIndexes.forEach((z, i) => {
        if (i !== index && pages[i].flipped) {
          zIndexes[i] = z - 1;
        }
      });
    }
  }
  </script>
  
  <style scoped>
  .play {
    width: 1100px;
    height: 800px;
    border: 5px solid #cee8e8;
    margin: 20px 0px 20px 40px;
    border-radius: 50px;
  }
  .title {
    position: relative;
    text-decoration: none;
    display: inline;
    box-shadow: inset 0 -6px 0 #ffd3d3;
  }
  
  .like {
    width: 50px;
    height: 50px;
  }
  .cover {
    background-color: #fff;
    box-sizing: border-box;
    width: 500px;
    height: 500px;
    border-radius: 0px 40px 40px 0px;
  }
  .coverImg,
  .leftImg {
    width: 450px;
    height: 450px;
    margin-top: 25px;
    margin-left: 5px;
  }
  .book {
    margin: 90px 0px 20px 50px;
    display: flex;
  }
  .flip-book {
    width: 500px;
    height: 500px;
    position: relative;
    perspective: 1500px;
    border-radius: 100px;
  }
  .flip {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    transform-origin: left;
    transform-style: preserve-3d;
    transition: 0.5s;
    color: #000;
  }
  .front,
  .back {
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    backface-visibility: hidden;
  }
  .front {
    background-color: #fff;
    box-sizing: border-box;
    padding: 0 13px;
    border-radius: 40px 0px 0px 40px;
  }
  .back {
    background-color: #fff;
    transform: rotateY(180deg);
    border-radius: 0px 40px 40px 0px;
  }
  .flip.flipped {
    transform: rotateY(-180deg);
  }
  .pre-wrap {
    white-space: pre-wrap;
  }
  .left,
  .leftstatic,
  .right,
  .rightstatic {
    width: 40px;
    height: 40px;
    position: absolute;
    background: white;
    cursor: pointer;
    bottom: 13px;
    right: 13px;
  }
  .rightstatic:hover,
  .leftstatic:hover {
    width: 40px;
    height: 40px;
    opacity: 0;
    cursor: pointer;
  }
  .end {
    width: 40px;
    height: 40px;
    position: absolute;
    background: white;
    cursor: pointer;
    bottom: 13px;
    right: 13px;
  }
  </style>