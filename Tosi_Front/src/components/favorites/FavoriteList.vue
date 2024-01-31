<template>
    <div>
        <h4>나의 책장</h4>
        <hr>
        <table>
            <tr>
                <th>썸네일</th>
                <th>제목</th>
                <th>재생 시간</th>
            </tr>
            <tr v-for="favorite in favoriteStore.favoriteList" :key="favorite.favoriteId">
                <!--TODO 즐겨찾기 부분 코드 확인 후 수정-->
                <td>
                <!--TODO 이미지 추가-->
                </td>
                <td>
                    <RouterLink :to="`/tales/${favorite.taleId}`">{{ favorite.title }}</RouterLink>
                </td>
                <td>{{ favorite.time }}</td>
                <!--TODO title, time 부분 수정
                    favorite의 taleId와 일치하는 tale 가져와서 tale.tiltle, time 출력?-->
            </tr>
        </table>
    </div>
</template>

<script setup>
import { useFavoriteStore } from "@/stores/favoriteStore";
import { onMounted } from "vue";

const favoriteStore = useFavoriteStore()

onMounted(() => {
    store.getFavoriteList()
})

</script>

<style scoped></style>