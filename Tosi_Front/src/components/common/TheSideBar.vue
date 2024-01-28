<template>
    <div id="container">
        <ul>
            <li>
                <RouterLink to="/tales">전체 책 보기</RouterLink> |
                <!-- <RouterLink :to="`/favorite/${userId}`">나의 책장</RouterLink> | -->
                <!--TODO 유저아이디 바인딩 확인-->
                <!-- <RouterLink to="">나만의 동화 만들기</RouterLink> | -->
                <!-- TODO 링크 추가 -->
                <!-- <RouterLink to="/customtale">친구들의 동화 보기</RouterLink> -->
                <!-- <RouterLink to="/">마이페이지</RouterLink>  -->
                <!--TODO 링크 추가-->
            </li>
        </ul>
    </div>
</template>

<script setup>

</script>

<style  scoped>
#container {
    text-align: center;
}
</style>