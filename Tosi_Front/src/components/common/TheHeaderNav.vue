<template>
    <header>
        <div></div>
        <div>
            <RouterLink to="/membermain">
                <img src="@/assets/logo.png" alt="Logo"/>
            </RouterLink>
            <div>로그아웃</div>
        </div>
        <div>
        <ul>
            <RouterLink to="/membermain">홈</RouterLink>
            <!--TODO 로그아웃 버튼 추가-->
            <div>검색</div>
        </ul>
        </div>
    </header>
</template>

<script setup>
</script>

<style scoped>
header {
    display: flex; 
    flex-direction: row;
    align-items: center;
    justify-content: space-around;
}

img {
    width: 200px;
}

</style>
