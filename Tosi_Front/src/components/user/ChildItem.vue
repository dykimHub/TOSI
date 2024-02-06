<template>
    <li>
      {{ child.childName }} - 성별: {{ child.gender }} - 내 아이 여부: {{ child.isMyBaby }}
      <button @click="deleteChild">삭제</button>
    </li>
  </template>
  
  <script setup>
  import { defineProps, defineEmits } from 'vue';
  
  const { child } = defineProps(['child']);
  const { emit } = defineEmits();
  
  const deleteChild = () => {
    emit('deleteChild', child);
  };
  
  </script>
  