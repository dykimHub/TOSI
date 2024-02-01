<template>
    <div>
        <ul>
            <li><RouterLink to="/tales">전체 책 보기</RouterLink></li>
            <li><RouterLink :to="`/favorite/${userId}`">나의 책장</RouterLink></li>
            <li><RouterLink to="/customTale/create">나만의 동화 만들기</RouterLink></li>
            <!-- TODO 링크 추가 -->
            <li><RouterLink to="/customtale/customTaleList">친구들의 동화 보기</RouterLink></li>
            <li><RouterLink to="/userInfo/Update">마이페이지</RouterLink></li>
            <!--TODO 링크 추가-->
        </ul>
    </div>
</template>

<script setup>

</script>

<style scoped>

div {
    width:15rem;
    position: fixed;
} 
a{
    text-decoration: none;
    color: black;
}

li {
    display: inline-block;
    margin: 15px;
    list-style-type: none;
}
</style>