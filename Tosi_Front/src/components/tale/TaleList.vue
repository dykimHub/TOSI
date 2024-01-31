<template>
    <div>
        <h4>동화 목록</h4>
        <hr>
        <table>
            <!-- <tr>
                <th>썸네일</th>
                <th>제목</th>
                <th>재생 시간</th>
            </tr>
            <tr v-for="tale in store.taleList" :key="tale.taleId">
                <td>이미지</td>
                <td>
                    <RouterLink :to="`/tale/${tale.taleId}`">{{ tale.title }}</RouterLink>
                </td>
                <td>{{ tale.time }}</td>
            </tr> -->
            <ul>
                <li v-for="tale in Talestore.taleList" :key="tale.taleId">
                    제목: {{ tale.title }}
                    <hr/>
                    재생 시간: {{ tale.time }}
                </li>
            </ul>
        </table>
    </div>
</template>

<script setup>
import { useTaleStore } from "@/stores/tale";
import { onMounted } from "vue";

const Talestore = useTaleStore()

onMounted(() => {
    Talestore.getTaleList()
})

</script>

<style scoped></style>