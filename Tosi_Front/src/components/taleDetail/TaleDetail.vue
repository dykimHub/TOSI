<template>
  <div v-if="taleDetailStore.tale">
    <h1>{{ taleDetailStore.tale.title }}</h1>
    <img :src="taleDetailStore.tale.thumbnail" />

    <h1>오늘의 주인공</h1>
    <div v-for="(character, index) in taleDetailStore.tale.characters" :key="character.id">
      <h3>{{ character }}</h3>
      <div>선택됨: {{ selected[index] }}</div>
      <select class="form-select" aria-label="Default select example" v-model="selected[index]">
        <option v-for="option in options" :value="option.value">
          {{ option.text }}
        </option>
      </select>
    </div>

    <h1>목소리 선택</h1>
    <div>
      <div v-for="item in items" :key="item.speaker">
        <label
          ><input type="radio" :value="item.speaker" v-model="speaker" :name="item.name" />
          {{ item.name }}</label
        >
      </div>
      <div>선택한 목소리 : {{ speaker }}</div>
    </div>

    <button class="btn btn-primary" type="submit" @click="readBook">동화 시작하기</button>
    <div v-for="page in pages" :key="page.id">
      <img :src="page.left" />
      <h1>{{ page.right }}</h1>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useTaleDetailStore } from '@/stores/taleDetailStore';
import axios from 'axios';

const taleDetailStore = useTaleDetailStore();
const speaker = ref('vdain');
const items = ref([
  { name: '다인', speaker: 'vdain', emotion: 3, 'emotion-strength': 1 },
  { name: '고은', speaker: 'vgoeun', emotion: 3, 'emotion-strength': 1 },
  { name: '미경', speaker: 'vmikyung', emotion: 3, 'emotion-strength': 1 },
  { name: '이안', speaker: 'vian', emotion: 0, 'emotion-strength': 0 },
  { name: '대성', speaker: 'vdaeseong', emotion: 3, 'emotion-strength': 1 },
  { name: '원탁', speaker: 'nwontak', emotion: 0, 'emotion-strength': 0 },
]);

const props = defineProps({
  taleId: Number,
});

taleDetailStore.taleId = props.taleId;
taleDetailStore.getTaleDetail();

const selected = reactive({});

const options = ref([
  { text: '하나', value: '1' },
  { text: '둘', value: '2' },
  { text: '셋', value: '3' },
]);

const pages = ref(null);
const readBook = () => {
  axios
    .post('http://localhost:8080/tales/read', taleDetailStore.tale)
    .then((response) => {
      pages.value = response.data;
    })
    .catch((error) => {
      console.error('Error fetching:', error);
    });
};

console.log(props.taleId);
</script>

<style scoped>
button {
  border: 1px solid burlywood;
}
</style>
