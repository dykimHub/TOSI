<template>
    <div>
        <ul>
            <li><RouterLink to="/tales">전체 책 보기</RouterLink></li>
            <li><RouterLink :to="`/favorite/${userId}`">나의 책장</RouterLink></li>
            <li><RouterLink to="/customTale/create">나만의 동화 만들기</RouterLink></li>
            <li><RouterLink to="/customtale/customTaleList">친구들의 동화 보기</RouterLink></li>
            <li><RouterLink to="/userInfo/Update">마이페이지</RouterLink></li>
        </ul>
    </div>
</template>

<script setup>

</script>

<style scoped>

div {
    width:13rem;
    position: fixed;
    margin-top: 40px;
} 

li {
    display: inline-block;
    margin: 10px;
    list-style-type: none;
}

a {
  text-decoration: none;
  color: black;
}
</style>