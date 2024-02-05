<template>
    <header>
        <div></div>
        <div>
            <RouterLink to="/membermain">
                <img src="@/assets/logo.png" class="logo" alt="Logo"/>
            </RouterLink>   
        </div>
        <div>
            <div>
                <RouterLink to="/membermain">홈</RouterLink>
                <br>
                <a>로그아웃</a>
            </div>
            <div>검색</div>
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

.logo {
    width: 200px;
    margin-top: 40px;
}


a {
  text-decoration: none;
  color: black;
}

</style>
