<template>
    <header>
        <RouterLink to="/tosi">
            <img src="@/assets/logo.png" alt="Logo"/>
        </RouterLink>   
        <div class="container">
            <ul>
                <RouterLink to="/tosi">홈</RouterLink>
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
