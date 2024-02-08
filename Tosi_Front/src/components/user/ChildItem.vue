<template>
  <li>
    {{ child.childName }} - 성별: {{ child.gender }} - 내 아이 여부: {{ child.myBaby }}
    <!-- <button @click="deleteChild(child)">삭제</button> -->
  </li>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const { child } = defineProps(['child']);
const { emit } = defineEmits();

// const deleteChild = (child) => {
//   emit('deleteChild', child);
// };
</script>
