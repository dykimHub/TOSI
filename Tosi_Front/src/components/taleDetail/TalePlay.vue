<template>
  <div v-if="taleDetailStore.pages">
    <div class="book">
      <div class="flip-book">
        <div
          class="flip"
          v-for="(page, index) in pages"
          :key="`page-${index}`"
          :class="{ flipped: page.flipped }"
          :style="{ zIndex: zIndexes[index] }"
        >
          <div class="back">
            <img :src="page.left" alt="" />
            <button @click="flipPage(index, false)" class="back-btn">Before</button>
          </div>
          <div class="front">
            <h2 v-if="index + 1 < pages.length">
              {{ pages[index + 1].right }}
            </h2>
            <h2 v-else>
              {{ taleDetailStore.tale.title }}
            </h2>
            <div v-if="index === 1">
              <button @click="goToEnd" class="next-btn">End</button>
            </div>
            <div v-else>
              <button @click="flipPage(index, true)" class="next-btn">NEXT</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-else>is Loading...</div>
</template>

<script setup>
import { ref, computed, reactive } from "vue";
import { useTaleDetailStore } from "@/stores/taleDetailStore";
import { useRouter } from "vue-router";

const taleDetailStore = useTaleDetailStore();
const router = useRouter();
const pages = taleDetailStore.pages.reverse();

const props = defineProps({
  speaker: String,
});

const goToEnd = () => {
  router.push({ name: "taleEnd", params: { taleId: taleDetailStore.taleId } });
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
img {
  width: 400px;
  height: 600px;
}
.book {
  display: flex;
}
#cover {
  width: 400px;
  height: 600px;
}
.flip-book {
  width: 400px;
  height: 600px;
  position: relative;
  perspective: 1500px;
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
}
.back {
  background-color: #000;
  transform: rotateY(180deg);
}
.next-btn,
.back-btn {
  position: absolute;
  bottom: 13px;
  right: 13px;
  cursor: pointer;
}
.next-btn {
  color: #000;
}
.back-btn {
  color: #fff;
}
.flip.flipped {
  transform: rotateY(-180deg);
}
</style>
